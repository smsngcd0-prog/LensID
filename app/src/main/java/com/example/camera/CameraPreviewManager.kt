package com.example.camera

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.CaptureResult
import android.hardware.camera2.TotalCaptureResult
import android.os.Handler
import android.os.Looper
import android.util.Size
import android.view.Surface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.roundToInt

data class LiveCameraTelemetry(
    val currentIso: Int = 0,
    val exposureTimeMs: Double = 0.0,
    val focalLengthMm: Float = 0.0f,
    val focusDistanceM: Float = 0.0f,
    val isFlashOn: Boolean = false,
    val fps: Int = 0,
    val statusMessage: String = "Готов к запуску"
)

class CameraPreviewManager(private val context: Context) {

    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private val mainHandler = Handler(Looper.getMainLooper())

    private var activeCameraDevice: CameraDevice? = null
    private var activeCaptureSession: CameraCaptureSession? = null
    private var previewRequestBuilder: CaptureRequest.Builder? = null

    private val _telemetry = MutableStateFlow(LiveCameraTelemetry())
    val telemetry: StateFlow<LiveCameraTelemetry> = _telemetry.asStateFlow()

    private var frameCount = 0
    private var lastFpsTimestamp = System.currentTimeMillis()
    private var isTorchOn = false

    @SuppressLint("MissingPermission")
    fun startPreview(cameraId: String, surfaceTexture: SurfaceTexture) {
        stopPreview()

        _telemetry.value = _telemetry.value.copy(statusMessage = "Подключение к камере ID $cameraId...")

        try {
            // Find best preview size
            val characteristics = cameraManager.getCameraCharacteristics(cameraId)
            val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
            val outputSizes = map?.getOutputSizes(SurfaceTexture::class.java) ?: emptyArray()
            
            // Choose suitable resolution around 1280x720 or 1920x1080 for smooth preview
            val previewSize = outputSizes.firstOrNull { it.width in 1200..1920 && it.height in 700..1080 }
                ?: outputSizes.firstOrNull() ?: Size(1280, 720)

            surfaceTexture.setDefaultBufferSize(previewSize.width, previewSize.height)

            cameraManager.openCamera(cameraId, object : CameraDevice.StateCallback() {
                override fun onOpened(camera: CameraDevice) {
                    activeCameraDevice = camera
                    createCameraPreviewSession(camera, Surface(surfaceTexture), cameraId)
                }

                override fun onDisconnected(camera: CameraDevice) {
                    camera.close()
                    activeCameraDevice = null
                    _telemetry.value = _telemetry.value.copy(statusMessage = "Камера отключена")
                }

                override fun onError(camera: CameraDevice, error: Int) {
                    camera.close()
                    activeCameraDevice = null
                    val errorMsg = when (error) {
                        ERROR_CAMERA_IN_USE -> "Камера занята другим приложением"
                        ERROR_MAX_CAMERAS_IN_USE -> "Превышен лимит открытых камер"
                        ERROR_CAMERA_DISABLED -> "Камера отключена политикой безопасности"
                        ERROR_CAMERA_DEVICE -> "Аппаратная ошибка сенсора"
                        ERROR_CAMERA_SERVICE -> "Ошибка системной службы CameraService"
                        else -> "Код ошибки: $error"
                    }
                    _telemetry.value = _telemetry.value.copy(statusMessage = "Ошибка: $errorMsg")
                }
            }, mainHandler)

        } catch (e: Exception) {
            _telemetry.value = _telemetry.value.copy(statusMessage = "Исключение при открытии: ${e.localizedMessage}")
        }
    }

    private fun createCameraPreviewSession(camera: CameraDevice, surface: Surface, cameraId: String) {
        try {
            val builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW).apply {
                addTarget(surface)
                set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_AUTO)
                set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
            }
            previewRequestBuilder = builder

            camera.createCaptureSession(listOf(surface), object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(session: CameraCaptureSession) {
                    if (activeCameraDevice == null) return
                    activeCaptureSession = session
                    try {
                        session.setRepeatingRequest(builder.build(), captureCallback, mainHandler)
                        _telemetry.value = _telemetry.value.copy(statusMessage = "Трансляция активна (ID $cameraId)")
                    } catch (e: Exception) {
                        _telemetry.value = _telemetry.value.copy(statusMessage = "Ошибка сессии: ${e.message}")
                    }
                }

                override fun onConfigureFailed(session: CameraCaptureSession) {
                    _telemetry.value = _telemetry.value.copy(statusMessage = "Не удалось сконфигурировать сессию")
                }
            }, mainHandler)

        } catch (e: Exception) {
            _telemetry.value = _telemetry.value.copy(statusMessage = "Ошибка создания сессии: ${e.message}")
        }
    }

    private val captureCallback = object : CameraCaptureSession.CaptureCallback() {
        override fun onCaptureCompleted(
            session: CameraCaptureSession,
            request: CaptureRequest,
            result: TotalCaptureResult
        ) {
            super.onCaptureCompleted(session, request, result)

            frameCount++
            val now = System.currentTimeMillis()
            var currentFps = _telemetry.value.fps
            if (now - lastFpsTimestamp >= 1000) {
                currentFps = frameCount
                frameCount = 0
                lastFpsTimestamp = now
            }

            val iso = result.get(CaptureResult.SENSOR_SENSITIVITY) ?: 0
            val expNs = result.get(CaptureResult.SENSOR_EXPOSURE_TIME) ?: 0L
            val expMs = expNs / 1_000_000.0
            val focalLen = result.get(CaptureResult.LENS_FOCAL_LENGTH) ?: 0.0f
            val focusDist = result.get(CaptureResult.LENS_FOCUS_DISTANCE) ?: 0.0f
            val focusMeters = if (focusDist > 0.001f) 1.0f / focusDist else 0.0f

            _telemetry.value = _telemetry.value.copy(
                currentIso = iso,
                exposureTimeMs = expMs,
                focalLengthMm = focalLen,
                focusDistanceM = focusMeters,
                fps = currentFps
            )
        }
    }

    fun toggleTorch() {
        val session = activeCaptureSession ?: return
        val builder = previewRequestBuilder ?: return

        isTorchOn = !isTorchOn
        try {
            builder.set(
                CaptureRequest.FLASH_MODE,
                if (isTorchOn) CaptureRequest.FLASH_MODE_TORCH else CaptureRequest.FLASH_MODE_OFF
            )
            session.setRepeatingRequest(builder.build(), captureCallback, mainHandler)
            _telemetry.value = _telemetry.value.copy(isFlashOn = isTorchOn)
        } catch (e: Exception) {
            // Flash not supported on this camera
        }
    }

    fun stopPreview() {
        try {
            activeCaptureSession?.close()
            activeCaptureSession = null
            activeCameraDevice?.close()
            activeCameraDevice = null
            isTorchOn = false
            _telemetry.value = LiveCameraTelemetry(statusMessage = "Превью остановлено")
        } catch (e: Exception) {
            // Safe cleanup
        }
    }
}
