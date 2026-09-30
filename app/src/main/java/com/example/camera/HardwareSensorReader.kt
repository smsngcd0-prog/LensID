package com.example.camera

import android.hardware.camera2.CameraCharacteristics
import android.os.Build
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

object HardwareSensorReader {

    data class DetectedDriverInfo(
        val detectedSensorModels: List<String>,
        val systemPropertiesFound: Map<String, String>,
        val kernelDriverFound: String?,
        val vendorFilesFound: List<String>,
        val detectionSourceRu: String,
        val detectionSourceUa: String,
        val detectionSourceEn: String
    )

    private val KNOWN_SENSORS_REGEX = listOf(
        // SmartSens Technology (思特威)
        Regex("""(?i)\b(sc\d{3,4}[a-z0-9_-]{0,4})\b"""),
        // GalaxyCore (格科微)
        Regex("""(?i)\b(gc\d{2,4}[a-z0-9_-]{0,4})\b"""),
        // OmniVision
        Regex("""(?i)\b(ov\d{2,4}[a-z0-9_-]{0,4})\b"""),
        // Samsung ISOCELL
        Regex("""(?i)\b(s5k[a-z0-9_-]{3,6}|isocell[-_]?[a-z0-9]{2,5}|hp[1-9]|gn[1-5]|hm[1-9]|jn[1-5]|gm[1-5]|gw[1-5])\b"""),
        // Sony Semiconductor
        Regex("""(?i)\b(imx\d{3,4}|lyt[-_]?\d{3})\b"""),
        // SK Hynix
        Regex("""(?i)\b(hi[-_]?\d{3,4})\b""")
    )

    fun inspectHardware(cameraId: String, characteristics: CameraCharacteristics): DetectedDriverInfo {
        val detectedModels = mutableListOf<String>()
        val foundProps = mutableMapOf<String, String>()
        val foundFiles = mutableListOf<String>()
        var kernelLogSummary: String? = null
        var primarySourceRu = "Аппаратный анализ оптического формата"
        var primarySourceUa = "Апаратний аналіз оптичного формату"
        var primarySourceEn = "Hardware optical format analysis"

        // 1. Scan /sys/bus/i2c/drivers and /sys/bus/platform/drivers
        // This inspects the ACTUAL loaded Linux kernel camera drivers!
        val driverDirs = listOf(
            "/sys/bus/i2c/drivers",
            "/sys/bus/platform/drivers",
            "/sys/class/camera",
            "/sys/android_camera",
            "/sys/devices/virtual/camera"
        )

        for (dirPath in driverDirs) {
            try {
                val dir = File(dirPath)
                if (dir.exists() && dir.isDirectory) {
                    val list = dir.list()
                    if (list != null) {
                        for (driverName in list) {
                            val lower = driverName.lowercase()
                            for (regex in KNOWN_SENSORS_REGEX) {
                                val match = regex.find(lower)
                                if (match != null) {
                                    val sensorName = match.value.uppercase()
                                    detectedModels.add(sensorName)
                                    foundFiles.add("$dirPath/$driverName")
                                    if (kernelLogSummary == null) {
                                        kernelLogSummary = "Ядро Linux ($dirPath/$driverName)"
                                        primarySourceRu = "Драйвер ядра I2C/Platform ($driverName)"
                                        primarySourceUa = "Драйвер ядра I2C/Platform ($driverName)"
                                        primarySourceEn = "Linux Kernel Driver I2C/Platform ($driverName)"
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (e: Throwable) {
                // Ignore permission denial
            }
        }

        // 2. Scan /proc/driver/camera_info & /proc/camera_info (MediaTek & Qualcomm)
        val procPaths = listOf(
            "/proc/driver/camera_info",
            "/proc/camera_info",
            "/proc/driver/camera_otp",
            "/proc/device-tree/camera",
            "/proc/device-tree/soc"
        )
        for (path in procPaths) {
            try {
                val f = File(path)
                if (f.exists() && f.canRead()) {
                    val content = f.readText()
                    for (regex in KNOWN_SENSORS_REGEX) {
                        for (m in regex.findAll(content)) {
                            val sensor = m.value.uppercase()
                            if (isValidSensorName(sensor)) {
                                detectedModels.add(sensor)
                                primarySourceRu = "Системный интерфейс ядра ($path)"
                                primarySourceUa = "Системний інтерфейс ядра ($path)"
                                primarySourceEn = "Linux Kernel Interface ($path)"
                            }
                        }
                    }
                }
            } catch (e: Throwable) {
                // Ignore
            }
        }

        // 3. Scan /vendor/etc/camera & /vendor/etc/sensors (world-readable camera configurations)
        val vendorDirs = listOf(
            "/vendor/etc/camera",
            "/vendor/etc/sensors",
            "/odm/etc/camera",
            "/system/etc/camera"
        )
        for (vDir in vendorDirs) {
            try {
                val dir = File(vDir)
                if (dir.exists() && dir.isDirectory) {
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

        // 4. Shell `getprop` execution (frequently allows reading vendor.camera.* properties)
        try {
            val process = Runtime.getRuntime().exec(arrayOf("/system/bin/getprop"))
            BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    val l = line ?: break
                    if (l.contains("camera", ignoreCase = true) ||
                        l.contains("sensor", ignoreCase = true) ||
                        l.contains("product", ignoreCase = true)
                    ) {
                        for (regex in KNOWN_SENSORS_REGEX) {
                            for (m in regex.findAll(l)) {
                                val sensor = m.value.uppercase()
                                if (isValidSensorName(sensor)) {
                                    detectedModels.add(sensor)
                                    foundProps[l.substringBefore(":")] = l
                                    if (primarySourceRu.startsWith("Аппаратный")) {
                                        primarySourceRu = "Системное свойство Android (getprop)"
                                        primarySourceUa = "Системна властивість Android (getprop)"
                                        primarySourceEn = "Android System Property (getprop)"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: Throwable) {
            // Ignore shell restriction
        }

        // 5. Vendor tags in CameraCharacteristics
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

        return DetectedDriverInfo(
            detectedSensorModels = detectedModels.distinct(),
            systemPropertiesFound = foundProps,
            kernelDriverFound = kernelLogSummary,
            vendorFilesFound = foundFiles,
            detectionSourceRu = primarySourceRu,
            detectionSourceUa = primarySourceUa,
            detectionSourceEn = primarySourceEn
        )
    }

    private fun isValidSensorName(name: String): Boolean {
        if (name.length < 4) return false
        val upper = name.uppercase()
        val ignoreList = listOf("PROC", "HTTP", "FILE", "DATA", "BOOT", "PATH", "NODE", "HTML", "SYSTEM")
        if (ignoreList.any { upper.startsWith(it) }) return false
        return true
    }
}
