package com.example.camera

import android.hardware.camera2.CameraCharacteristics
import android.os.Build
import java.io.File

object HardwareSensorReader {

    data class DetectedDriverInfo(
        val rawDriverLog: String?,
        val detectedSensorModels: List<String>,
        val systemProperties: Map<String, String>,
        val vendorTags: Map<String, String>,
        val detectionSourceRu: String,
        val detectionSourceUa: String,
        val detectionSourceEn: String
    )

    fun inspectHardware(cameraId: String, characteristics: CameraCharacteristics): DetectedDriverInfo {
        val detectedModels = mutableListOf<String>()
        val foundProps = mutableMapOf<String, String>()
        val foundVendorTags = mutableMapOf<String, String>()
        var primarySourceRu = "Аппаратный замер оптики и матрицы"
        var primarySourceUa = "Апаратний вимір оптики та матриці"
        var primarySourceEn = "Hardware optical & sensor measurement"

        // 1. Check Kernel drivers in /proc and /sys
        val kernelLog = readKernelCameraDrivers()
        if (kernelLog.isNotBlank()) {
            val parsedFromKernel = extractSensorNamesFromText(kernelLog)
            if (parsedFromKernel.isNotEmpty()) {
                detectedModels.addAll(parsedFromKernel)
                primarySourceRu = "Драйвер ядра Linux (/proc/driver/camera_info)"
                primarySourceUa = "Драйвер ядра Linux (/proc/driver/camera_info)"
                primarySourceEn = "Linux Kernel Driver (/proc/driver/camera_info)"
            }
        }

        // 2. Query SystemProperties via Android Reflection
        val propKeys = listOf(
            "ro.camera.sensor.$cameraId",
            "vendor.camera.sensor.$cameraId",
            "persist.vendor.camera.sensor.$cameraId",
            "ro.hardware.camera.sensor$cameraId",
            "ro.camera.sensor",
            "vendor.camera.sensor",
            "camera.sensor.vendor",
            "vendor.camera.sensor.name",
            "vendor.camera.aux.packagelist",
            "ro.product.device",
            "ro.product.model",
            "ro.product.board"
        )

        for (key in propKeys) {
            val value = getSystemProperty(key)
            if (!value.isNullOrBlank()) {
                foundProps[key] = value
                val parsed = extractSensorNamesFromText(value)
                if (parsed.isNotEmpty()) {
                    detectedModels.addAll(parsed)
                    if (primarySourceRu.startsWith("Аппаратный")) {
                        primarySourceRu = "Системные свойства Android ($key)"
                        primarySourceUa = "Системні властивості Android ($key)"
                        primarySourceEn = "Android System Properties ($key)"
                    }
                }
            }
        }

        // 3. Inspect CameraCharacteristics vendor keys
        try {
            val keys = characteristics.keys
            for (key in keys) {
                val keyName = key.name
                if (keyName.contains("sensor", ignoreCase = true) ||
                    keyName.contains("vendor", ignoreCase = true) ||
                    keyName.contains("camera.name", ignoreCase = true) ||
                    keyName.contains("hardware", ignoreCase = true)
                ) {
                    try {
                        val value = characteristics.get(key)
                        if (value != null) {
                            val strValue = value.toString()
                            if (strValue.isNotBlank() && strValue.length < 150) {
                                foundVendorTags[keyName] = strValue
                                val parsed = extractSensorNamesFromText(strValue)
                                if (parsed.isNotEmpty()) {
                                    detectedModels.addAll(parsed)
                                    if (primarySourceRu.startsWith("Аппаратный")) {
                                        primarySourceRu = "Вендорный тег Camera HAL ($keyName)"
                                        primarySourceUa = "Вендорний тег Camera HAL ($keyName)"
                                        primarySourceEn = "Camera HAL Vendor Tag ($keyName)"
                                    }
                                }
                            }
                        }
                    } catch (e: Throwable) {
                        // Key not readable
                    }
                }
            }
        } catch (e: Throwable) {
            // Ignore reflection / vendor keys error
        }

        return DetectedDriverInfo(
            rawDriverLog = kernelLog.ifBlank { null },
            detectedSensorModels = detectedModels.distinct(),
            systemProperties = foundProps,
            vendorTags = foundVendorTags,
            detectionSourceRu = primarySourceRu,
            detectionSourceUa = primarySourceUa,
            detectionSourceEn = primarySourceEn
        )
    }

    private fun readKernelCameraDrivers(): String {
        val paths = listOf(
            "/proc/driver/camera_info",
            "/proc/camera_info",
            "/sys/class/camera/camera_info",
            "/sys/android_camera/sensor",
            "/sys/devices/virtual/camera/info"
        )
        val sb = StringBuilder()
        for (path in paths) {
            try {
                val file = File(path)
                if (file.exists() && file.canRead()) {
                    val text = file.readText().trim()
                    if (text.isNotBlank()) {
                        sb.appendLine("[$path]: $text")
                    }
                }
            } catch (e: Throwable) {
                // Ignore read access errors
            }
        }
        return sb.toString().trim()
    }

    private fun getSystemProperty(propName: String): String? {
        return try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val getMethod = systemPropertiesClass.getMethod("get", String::class.java)
            val value = getMethod.invoke(null, propName) as? String
            if (value.isNullOrBlank()) null else value.trim()
        } catch (e: Throwable) {
            null
        }
    }

    private val SENSOR_PATTERNS = listOf(
        // SmartSens
        Regex("""(?i)\b(sc\d{3,4}[a-z]{0,3})\b"""),
        // GalaxyCore
        Regex("""(?i)\b(gc\d{2,4}[a-z]{0,3})\b"""),
        // Sony
        Regex("""(?i)\b(imx\d{3,4}|lyt[-_]?\d{3})\b"""),
        // Samsung
        Regex("""(?i)\b(s5k[a-z0-9]{3,6}|isocell\s*[a-z0-9]{2,5}|hp\d|gn\d|hm\d|jn\d|gm\d|gw\d)\b"""),
        // OmniVision
        Regex("""(?i)\b(ov\d{2,4}[a-z0-9]{0,3})\b"""),
        // SK Hynix
        Regex("""(?i)\b(hi[-_]?\d{3,4})\b""")
    )

    private fun extractSensorNamesFromText(text: String): List<String> {
        val results = mutableListOf<String>()
        for (regex in SENSOR_PATTERNS) {
            val matches = regex.findAll(text)
            for (m in matches) {
                val found = m.value.uppercase()
                // Filter out non-sensors
                if (found.length >= 4 && !found.startsWith("PROC") && !found.startsWith("HTTP")) {
                    results.add(found)
                }
            }
        }
        return results
    }
}
