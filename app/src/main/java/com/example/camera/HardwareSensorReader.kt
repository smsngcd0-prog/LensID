package com.example.camera

import android.hardware.camera2.CameraCharacteristics
import java.io.File
import java.util.concurrent.ConcurrentHashMap

data class DetectedDriverInfo(
    val detectedSensorModels: List<String>,
    val systemPropertiesFound: Map<String, String>,
    val kernelDriverFound: String?,
    val vendorFilesFound: List<String>,
    val detectionSourceRu: String,
    val detectionSourceUa: String,
    val detectionSourceEn: String
)

object HardwareSensorReader {

    private val cache = ConcurrentHashMap<String, DetectedDriverInfo>()

    private val KNOWN_SENSORS_REGEX = listOf(
        // Samsung ISOCELL
        Regex("""(?i)\b(hp[123]|hm[26]|gw[123]|gm[12]|jn[12]|jn5|jd1|g[nv][12]|gn5|gnd|isocell)\b"""),
        // Sony Semiconductor
        Regex("""(?i)\b(imx\d{3,4}|lyt[-_]?\d{3})\b"""),
        // SK Hynix
        Regex("""(?i)\b(hi[-_]?\d{3,4})\b""")
    )

    private val CAMERA_SYSTEM_PROPS = listOf(
        "vendor.camera.sensor",
        "vendor.camera.sensor.front",
        "vendor.camera.sensor.back",
        "persist.vendor.camera.sensor",
        "ro.hardware.camera",
        "ro.camera.model"
    )

    fun inspectHardware(cameraId: String, characteristics: CameraCharacteristics): DetectedDriverInfo {
        cache[cameraId]?.let { return it }

        val detectedModels = mutableListOf<String>()
        val foundProps = mutableMapOf<String, String>()
        val foundFiles = mutableListOf<String>()
        var kernelLogSummary: String? = null
        var primarySourceRu = "Аппаратный анализ оптического формата"
        var primarySourceUa = "Апаратний аналіз оптичного формату"
        var primarySourceEn = "Hardware optical format analysis"

        // 1. Scan /vendor/etc/camera (world-readable camera configuration files if accessible)
        val vendorDirs = listOf("/vendor/etc/camera", "/odm/etc/camera")
        for (vDir in vendorDirs) {
            try {
                val dir = File(vDir)
                if (dir.exists() && dir.canRead() && dir.isDirectory) {
                    val files = dir.list()
                    if (files != null) {
                        for (fName in files) {
                            for (regex in KNOWN_SENSORS_REGEX) {
                                val match = regex.find(fName.lowercase())
                                if (match != null) {
                                    val sensor = match.value.uppercase()
                                    if (isValidSensorName(sensor)) {
                                        detectedModels.add(sensor)
                                        foundFiles.add("$vDir/$fName")
                                        if (primarySourceRu.startsWith("Аппаратный")) {
                                            primarySourceRu = "Конфигурация модулей вендора ($fName)"
                                            primarySourceUa = "Конфігурація модулів вендора ($fName)"
                                            primarySourceEn = "Vendor Camera Module Config ($fName)"
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (e: Throwable) {
                // Ignore
            }
        }

        // 2. Read camera-related vendor system properties via SystemProperties reflection (zero subprocesses)
        for (prop in CAMERA_SYSTEM_PROPS) {
            val value = getSystemProperty(prop)
            if (!value.isNullOrBlank()) {
                foundProps[prop] = value
                for (regex in KNOWN_SENSORS_REGEX) {
                    for (m in regex.findAll(value)) {
                        val sensor = m.value.uppercase()
                        if (isValidSensorName(sensor)) {
                            detectedModels.add(sensor)
                            if (primarySourceRu.startsWith("Аппаратный")) {
                                primarySourceRu = "Системное свойство Android ($prop)"
                                primarySourceUa = "Системна властивість Android ($prop)"
                                primarySourceEn = "Android System Property ($prop)"
                            }
                        }
                    }
                }
            }
        }

        // 3. Vendor tags in CameraCharacteristics
        try {
            for (key in characteristics.keys) {
                val keyName = key.name
                if (keyName.contains("sensor", ignoreCase = true) ||
                    keyName.contains("name", ignoreCase = true) ||
                    keyName.contains("xiaomi", ignoreCase = true)
                ) {
                    try {
                        val v = characteristics.get(key)?.toString() ?: ""
                        for (regex in KNOWN_SENSORS_REGEX) {
                            for (m in regex.findAll(v)) {
                                val sensor = m.value.uppercase()
                                if (isValidSensorName(sensor)) {
                                    detectedModels.add(sensor)
                                    if (primarySourceRu.startsWith("Аппаратный")) {
                                        primarySourceRu = "Camera2 HAL Vendor Tag ($keyName)"
                                        primarySourceUa = "Camera2 HAL Vendor Tag ($keyName)"
                                        primarySourceEn = "Camera2 HAL Vendor Tag ($keyName)"
                                    }
                                }
                            }
                        }
                    } catch (e: Throwable) {}
                }
            }
        } catch (e: Throwable) {
            // Ignore
        }

        val result = DetectedDriverInfo(
            detectedSensorModels = detectedModels.distinct(),
            systemPropertiesFound = foundProps,
            kernelDriverFound = kernelLogSummary,
            vendorFilesFound = foundFiles,
            detectionSourceRu = primarySourceRu,
            detectionSourceUa = primarySourceUa,
            detectionSourceEn = primarySourceEn
        )

        cache[cameraId] = result
        return result
    }

    private fun getSystemProperty(propName: String): String? {
        return try {
            val clazz = Class.forName("android.os.SystemProperties")
            val getMethod = clazz.getMethod("get", String::class.java)
            val res = getMethod.invoke(null, propName) as? String
            if (!res.isNullOrBlank()) res.trim() else null
        } catch (e: Throwable) {
            null
        }
    }

    private fun isValidSensorName(name: String): Boolean {
        if (name.length < 4) return false
        val upper = name.uppercase()
        val ignoreList = listOf("PROC", "HTTP", "FILE", "DATA", "BOOT", "PATH", "NODE", "HTML", "SYSTEM")
        if (ignoreList.any { upper.startsWith(it) }) return false
        return true
    }
}
