package com.example.camera

import android.content.Context
import android.graphics.ImageFormat
import android.graphics.Rect
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraMetadata
import android.hardware.camera2.params.StreamConfigurationMap
import android.os.Build
import android.util.SizeF
import com.example.data.SensorDatabase
import com.example.model.CameraFacing
import com.example.model.CameraItem
import com.example.model.CameraRole
import com.example.model.DeviceInfo
import com.example.model.SensorVendorGuess
import kotlin.math.atan
import kotlin.math.roundToInt
import kotlin.math.sqrt

class CameraHardwareScanner(private val context: Context) {

    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    fun getDeviceInfo(totalLogical: Int, totalPhysical: Int): DeviceInfo {
        val soc = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Build.SOC_MODEL.ifBlank { Build.HARDWARE }
        } else {
            Build.HARDWARE
        }

        return DeviceInfo(
            manufacturer = Build.MANUFACTURER.replaceFirstChar { it.uppercase() },
            brand = Build.BRAND.replaceFirstChar { it.uppercase() },
            model = Build.MODEL,
            deviceCode = Build.DEVICE,
            board = Build.BOARD,
            hardware = Build.HARDWARE,
            socModel = soc,
            androidVersion = "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})",
            apiLevel = Build.VERSION.SDK_INT,
            totalLogicalCameras = totalLogical,
            totalPhysicalCameras = totalPhysical
        )
    }

    fun scanAllCameras(): List<CameraItem> {
        val resultList = mutableListOf<CameraItem>()
        val seenPhysicalIds = mutableSetOf<String>()

        try {
            val cameraIds = cameraManager.cameraIdList

            for (cameraId in cameraIds) {
                try {
                    val characteristics = cameraManager.getCameraCharacteristics(cameraId)

                    // Check physical camera IDs (Android 9 / API 28+)
                    val physicalIds = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        try {
                            characteristics.physicalCameraIds.toList()
                        } catch (e: Exception) {
                            emptyList()
                        }
                    } else {
                        emptyList()
                    }

                    val isLogicalMulti = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        physicalIds.isNotEmpty()
                    } else {
                        false
                    }

                    // Parse the primary/logical camera
                    val cameraItem = parseCamera(
                        id = cameraId,
                        characteristics = characteristics,
                        isLogical = isLogicalMulti,
                        parentLogicalId = null,
                        physicalIds = physicalIds
                    )
                    resultList.add(cameraItem)

                    // If it has physical camera IDs, inspect each individual physical camera!
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && physicalIds.isNotEmpty()) {
                        for (physId in physicalIds) {
                            if (!seenPhysicalIds.contains(physId)) {
                                seenPhysicalIds.add(physId)
                                try {
                                    val physCharacteristics = try {
                                        cameraManager.getCameraCharacteristics(physId)
                                    } catch (e: Exception) {
                                        null
                                    }

                                    if (physCharacteristics != null) {
                                        val physicalCameraItem = parseCamera(
                                            id = physId,
                                            characteristics = physCharacteristics,
                                            isLogical = false,
                                            parentLogicalId = cameraId,
                                            physicalIds = emptyList()
                                        )
                                        resultList.add(physicalCameraItem)
                                    }
                                } catch (e: Exception) {
                                    // Ignore per-physical camera inspection errors
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    // Ignore single camera enumeration error
                }
            }
        } catch (e: Exception) {
            // General camera scan error
        }

        return resultList
    }

    private fun parseCamera(
        id: String,
        characteristics: CameraCharacteristics,
        isLogical: Boolean,
        parentLogicalId: String?,
        physicalIds: List<String>
    ): CameraItem {
        // Facing
        val lensFacingInt = characteristics.get(CameraCharacteristics.LENS_FACING)
        val facing = when (lensFacingInt) {
            CameraCharacteristics.LENS_FACING_BACK -> CameraFacing.BACK
            CameraCharacteristics.LENS_FACING_FRONT -> CameraFacing.FRONT
            CameraCharacteristics.LENS_FACING_EXTERNAL -> CameraFacing.EXTERNAL
            else -> CameraFacing.UNKNOWN
        }

        // Active Array & Pixel Array Sizes
        val activeArrayRect: Rect? = characteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE)
        val pixelArrayDimension = characteristics.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE)
        val width = activeArrayRect?.width() ?: pixelArrayDimension?.width ?: 1920
        val height = activeArrayRect?.height() ?: pixelArrayDimension?.height ?: 1080

        // Calculate Megapixels
        val rawMp = (width.toLong() * height.toLong()) / 1_000_000.0
        val megapixels = (rawMp * 10.0).roundToInt() / 10.0

        // Physical sensor size (mm)
        val physicalSize: SizeF = characteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE) ?: SizeF(4.0f, 3.0f)
        val physicalWidthMm = physicalSize.width
        val physicalHeightMm = physicalSize.height
        val diagonalMm = sqrt(physicalWidthMm * physicalWidthMm + physicalHeightMm * physicalHeightMm)

        // Pixel pitch (microns)
        val pixelPitchMicrons = if (width > 0) {
            (physicalWidthMm * 1000f) / width
        } else {
            1.0f
        }

        // Optical Format Calculation (e.g. 1/1.56")
        val opticalFormat = calculateOpticalFormat(diagonalMm)

        // Focal Lengths & Apertures
        val focalLengths = characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS)?.toList() ?: listOf(4.0f)
        val primaryFocalLength = focalLengths.firstOrNull() ?: 4.0f
        val apertures = characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_APERTURES)?.toList() ?: listOf(1.8f)

        // 35mm equivalent focal length: diag35mm = 43.27 mm
        val focalLength35mmEq = if (diagonalMm > 0.1f) {
            (primaryFocalLength * (43.27f / diagonalMm)).roundToInt().toFloat()
        } else {
            null
        }

        // Angles of view (FoV)
        val hFovRad = 2.0 * atan((physicalWidthMm / (2.0 * primaryFocalLength)).toDouble())
        val vFovRad = 2.0 * atan((physicalHeightMm / (2.0 * primaryFocalLength)).toDouble())
        val dFovRad = 2.0 * atan((diagonalMm / (2.0 * primaryFocalLength)).toDouble())
        val horizontalFovDeg = Math.toDegrees(hFovRad).toFloat()
        val verticalFovDeg = Math.toDegrees(vFovRad).toFloat()
        val diagonalFovDeg = Math.toDegrees(dFovRad).toFloat()

        // Hardware Level
        val hwLevelInt = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)
        val hardwareLevel = when (hwLevelInt) {
            CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY -> "LEGACY (Базовый)"
            CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED -> "LIMITED (Ограниченный)"
            CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL -> "FULL (Полный доступ к RAW и ручным настройкам)"
            CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_3 -> "LEVEL_3 (Флагманский уровень с RAW-репроцессингом)"
            CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_EXTERNAL -> "EXTERNAL (Внешняя камера)"
            else -> "UNKNOWN"
        }

        // Capabilities
        val capabilities = characteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES) ?: intArrayOf()
        val rawSupported = capabilities.contains(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_RAW)

        // OIS & EIS
        val oisModes = characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_OPTICAL_STABILIZATION) ?: intArrayOf()
        val oisSupported = oisModes.contains(CameraMetadata.LENS_OPTICAL_STABILIZATION_MODE_ON)
        val eisModes = characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES) ?: intArrayOf()
        val eisSupported = eisModes.contains(CameraMetadata.CONTROL_VIDEO_STABILIZATION_MODE_ON)

        // Flash
        val flashSupported = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) ?: false

        // Focus & Macro
        val minFocusDist = characteristics.get(CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE)
        val minFocusDistanceMeters = if (minFocusDist != null && minFocusDist > 0.001f) {
            1.0f / minFocusDist
        } else {
            null
        }
        val isMacroCapable = (minFocusDist != null && minFocusDist >= 10.0f) // Can focus closer than 10 cm

        val afModesInt = characteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES) ?: intArrayOf()
        val afModes = afModesInt.map { mode ->
            when (mode) {
                CameraMetadata.CONTROL_AF_MODE_AUTO -> "Автофокус (Auto)"
                CameraMetadata.CONTROL_AF_MODE_MACRO -> "Макрофокус"
                CameraMetadata.CONTROL_AF_MODE_CONTINUOUS_PICTURE -> "Фазовый следящий (Continuous Photo)"
                CameraMetadata.CONTROL_AF_MODE_CONTINUOUS_VIDEO -> "Следящий видео (Continuous Video)"
                CameraMetadata.CONTROL_AF_MODE_EDOF -> "Расширенная глубина резкости (EDOF)"
                CameraMetadata.CONTROL_AF_MODE_OFF -> "Ручной фокус (Manual Focus)"
                else -> "Режим $mode"
            }
        }

        // ISO Range
        val isoRangeRange = characteristics.get(CameraCharacteristics.SENSOR_INFO_SENSITIVITY_RANGE)
        val isoRange = if (isoRangeRange != null) Pair(isoRangeRange.lower, isoRangeRange.upper) else null

        // Shutter Range (nanoseconds to milliseconds)
        val expTimeRange = characteristics.get(CameraCharacteristics.SENSOR_INFO_EXPOSURE_TIME_RANGE)
        val exposureTimeRangeMs = if (expTimeRange != null) {
            Pair(expTimeRange.lower / 1_000_000.0, expTimeRange.upper / 1_000_000.0)
        } else {
            null
        }

        // Color Filter
        val cfa = characteristics.get(CameraCharacteristics.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT)
        val colorFilter = when (cfa) {
            CameraCharacteristics.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_RGGB -> "RGGB (Стандартный Bayer)"
            CameraCharacteristics.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_GRBG -> "GRBG (Bayer)"
            CameraCharacteristics.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_GBRG -> "GBRG (Bayer)"
            CameraCharacteristics.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_BGGR -> "BGGR (Bayer)"
            CameraCharacteristics.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_RGB -> "RGB"
            5 -> "MONO (Монохромный сенсор без цветофильтра)"
            6 -> "NIR (Ближний инфракрасный диапазон)"
            else -> "Quad Bayer / Tetracell / RGGB"
        }

        // Video and High Speed Resolutions
        val map: StreamConfigurationMap? = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
        val videoResolutions = mutableListOf<String>()
        var maxFps = 30
        val highSpeedFpsList = mutableListOf<Int>()

        if (map != null) {
            val jpegSizes = map.getOutputSizes(ImageFormat.JPEG) ?: emptyArray()
            val has4k = jpegSizes.any { it.width >= 3840 && it.height >= 2160 }
            val has8k = jpegSizes.any { it.width >= 7680 && it.height >= 4320 }

            if (has8k) videoResolutions.add("8K UHD (7680×4320)")
            if (has4k) videoResolutions.add("4K UHD (3840×2160)")
            videoResolutions.add("Full HD 1080p (1920×1080)")
            videoResolutions.add("HD 720p (1280×720)")

            // Check FPS ranges
            val fpsRanges = characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES) ?: emptyArray()
            for (range in fpsRanges) {
                if (range.upper > maxFps) maxFps = range.upper
            }

            try {
                val hsRanges = map.highSpeedVideoFpsRanges
                for (range in hsRanges) {
                    highSpeedFpsList.add(range.upper)
                    if (range.upper > maxFps) maxFps = range.upper
                }
            } catch (e: Exception) {
                // High speed not supported
            }
        }

        // Camera Role Determination
        val role = determineCameraRole(
            facing = facing,
            megapixels = megapixels,
            focalLength35mmEq = focalLength35mmEq,
            horizontalFovDeg = horizontalFovDeg,
            isMacroCapable = isMacroCapable,
            capabilities = capabilities
        )

        // Sensor Vendor Guessing using our intelligent database
        val sensorVendorGuess = SensorDatabase.matchSensor(
            megapixels = megapixels,
            widthMm = physicalWidthMm,
            heightMm = physicalHeightMm,
            pixelArrayW = width,
            pixelArrayH = height,
            isFront = facing == CameraFacing.FRONT,
            focalLengthMm = primaryFocalLength,
            manufacturer = Build.MANUFACTURER
        )

        val technicalSummary = buildMap {
            put("ID камеры", id)
            put("Тип", if (isLogical) "Логическая мульти-камера" else "Физический сенсор")
            if (parentLogicalId != null) put("Принадлежит к модулю", "Камера ID $parentLogicalId")
            if (physicalIds.isNotEmpty()) put("Связанные физические ID", physicalIds.joinToString(", "))
            put("Разрешение сенсора", "$width × $height пикселей ($megapixels Мп)")
            put("Размер матрицы", "${"%.2f".format(physicalWidthMm)} × ${"%.2f".format(physicalHeightMm)} мм")
            put("Диагональ сенсора", "${"%.2f".format(diagonalMm)} мм ($opticalFormat)")
            put("Размер отдельного пикселя", "${"%.2f".format(pixelPitchMicrons)} мкм (µm)")
            put("Фокусное расстояние", "${focalLengths.joinToString(", ") { "${it} мм" }}${if (focalLength35mmEq != null) " (~${focalLength35mmEq.roundToInt()} мм в экв. 35мм)" else ""}")
            put("Светосила (Диафрагма)", apertures.joinToString(", ") { "f/$it" })
            put("Угол обзора (FoV)", "По диагонали: ${diagonalFovDeg.roundToInt()}°, по горизонтали: ${horizontalFovDeg.roundToInt()}°")
            put("Аппаратный уровень HAL", hardwareLevel)
            put("Оптическая стабилизация (OIS)", if (oisSupported) "Поддерживается (Аппаратный OIS)" else "Отсутствует")
            put("Электронная стабилизация (EIS)", if (eisSupported) "Поддерживается (Gyro-EIS)" else "Отсутствует")
            put("Съемка в RAW (DNG)", if (rawSupported) "Поддерживается (RAW10 / RAW12 / RAW_SENSOR)" else "Не поддерживается")
            put("Вспышка / Фонарик", if (flashSupported) "Присутствует" else "Отсутствует")
            put("Автофокус", afModes.joinToString(", "))
            if (minFocusDistanceMeters != null) {
                put("Мин. дистанция фокусировки", "${"%.1f".format(minFocusDistanceMeters * 100)} см")
            }
            if (isoRange != null) {
                put("Диапазон чувствительности ISO", "ISO ${isoRange.first} — ISO ${isoRange.second}")
            }
            if (exposureTimeRangeMs != null) {
                val minMs = exposureTimeRangeMs.first
                val maxMs = exposureTimeRangeMs.second
                val minText = if (minMs < 1.0) "1/${(1000.0 / minMs).roundToInt()} с" else "${"%.2f".format(minMs)} мс"
                val maxText = if (maxMs >= 1000.0) "${"%.1f".format(maxMs / 1000.0)} с" else "${"%.1f".format(maxMs)} мс"
                put("Диапазон выдержки", "$minText — $maxText")
            }
            put("Цветовой фильтр матрицы", colorFilter)
            put("Максимальная частота кадров", "$maxFps fps")
            if (highSpeedFpsList.isNotEmpty()) {
                put("Замедленная съемка (Slow-mo)", "${highSpeedFpsList.distinct().sorted().joinToString(", ")} fps")
            }
        }

        return CameraItem(
            id = id,
            isLogical = isLogical,
            parentLogicalId = parentLogicalId,
            physicalIds = physicalIds,
            facing = facing,
            role = role,
            sensorVendorGuess = sensorVendorGuess,
            megapixels = megapixels,
            resolutionWidth = width,
            resolutionHeight = height,
            resolutionText = "$width × $height",
            pixelArraySize = pixelArrayDimension?.let { "${it.width} × ${it.height}" } ?: "$width × $height",
            activeArraySize = activeArrayRect?.let { "${it.width()} × ${it.height()}" } ?: "$width × $height",
            physicalWidthMm = physicalWidthMm,
            physicalHeightMm = physicalHeightMm,
            diagonalMm = diagonalMm,
            opticalFormat = opticalFormat,
            pixelPitchMicrons = pixelPitchMicrons,
            focalLengthsMm = focalLengths,
            focalLength35mmEq = focalLength35mmEq,
            apertures = apertures,
            horizontalFovDeg = horizontalFovDeg,
            verticalFovDeg = verticalFovDeg,
            diagonalFovDeg = diagonalFovDeg,
            hardwareLevel = hardwareLevel,
            oisSupported = oisSupported,
            eisSupported = eisSupported,
            rawSupported = rawSupported,
            flashSupported = flashSupported,
            afModes = afModes,
            minFocusDistanceMeters = minFocusDistanceMeters,
            isMacroCapable = isMacroCapable,
            isoRange = isoRange,
            exposureTimeRangeMs = exposureTimeRangeMs,
            colorFilter = colorFilter,
            maxFps = maxFps,
            highSpeedFpsList = highSpeedFpsList.distinct().sorted(),
            videoResolutions = videoResolutions,
            technicalSummary = technicalSummary
        )
    }

    private fun determineCameraRole(
        facing: CameraFacing,
        megapixels: Double,
        focalLength35mmEq: Float?,
        horizontalFovDeg: Float,
        isMacroCapable: Boolean,
        capabilities: IntArray
    ): CameraRole {
        if (facing == CameraFacing.FRONT) {
            return if (horizontalFovDeg > 88.0f || (focalLength35mmEq != null && focalLength35mmEq < 22f)) {
                CameraRole.FRONT_ULTRAWIDE
            } else {
                CameraRole.FRONT_SELFIE
            }
        }

        if (capabilities.contains(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_DEPTH_OUTPUT)) {
            return CameraRole.DEPTH_TOF
        }

        if (horizontalFovDeg > 95.0f || (focalLength35mmEq != null && focalLength35mmEq < 18.0f)) {
            return CameraRole.ULTRA_WIDE
        }

        if (focalLength35mmEq != null && focalLength35mmEq >= 100.0f) {
            return CameraRole.PERISCOPE
        }

        if (focalLength35mmEq != null && focalLength35mmEq >= 45.0f) {
            return CameraRole.TELEPHOTO
        }

        if (isMacroCapable && megapixels <= 5.0) {
            return CameraRole.MACRO
        }

        if (megapixels in 1.8..2.5 && !isMacroCapable) {
            return CameraRole.DEPTH_TOF
        }

        return CameraRole.MAIN_WIDE
    }

    private fun calculateOpticalFormat(diagonalMm: Float): String {
        return when {
            diagonalMm >= 15.5f -> "1.0\" (1/0.98\")"
            diagonalMm in 13.5f..15.4f -> "1/1.12\""
            diagonalMm in 11.5f..13.4f -> "1/1.28\""
            diagonalMm in 10.5f..11.4f -> "1/1.3\""
            diagonalMm in 9.5f..10.4f -> "1/1.4\""
            diagonalMm in 8.5f..9.4f -> "1/1.56\""
            diagonalMm in 7.8f..8.4f -> "1/1.72\""
            diagonalMm in 7.0f..7.7f -> "1/1.95\""
            diagonalMm in 6.0f..6.9f -> "1/2.0\""
            diagonalMm in 5.3f..5.9f -> "1/2.5\""
            diagonalMm in 4.7f..5.2f -> "1/2.76\""
            diagonalMm in 4.0f..4.6f -> "1/3.0\""
            diagonalMm in 3.4f..3.9f -> "1/3.6\""
            diagonalMm in 2.8f..3.3f -> "1/4.0\""
            diagonalMm in 2.2f..2.7f -> "1/5.0\""
            else -> "1/${"%.1f".format(16.0f / diagonalMm)}\""
        }
    }
}
