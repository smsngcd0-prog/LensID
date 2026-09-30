package com.example.model

import com.example.localization.AppLanguage
import kotlin.math.roundToInt

enum class CameraFacing {
    BACK,
    FRONT,
    EXTERNAL,
    UNKNOWN
}

enum class CameraRole(val badge: String) {
    MAIN_WIDE("MAIN"),
    ULTRA_WIDE("ULTRA-WIDE"),
    TELEPHOTO("TELEPHOTO"),
    PERISCOPE("PERISCOPE"),
    MACRO("MACRO"),
    DEPTH_TOF("DEPTH / ToF"),
    MONOCHROME("MONO"),
    FRONT_SELFIE("FRONT"),
    FRONT_ULTRAWIDE("FRONT-UW"),
    UNKNOWN("AUX");

    fun getTitle(lang: AppLanguage): String = when (this) {
        MAIN_WIDE -> when (lang) {
            AppLanguage.RU -> "Основная (Широкоугольная)"
            AppLanguage.UA -> "Основна (Ширококутна)"
            AppLanguage.EN -> "Main (Wide Angle)"
        }
        ULTRA_WIDE -> when (lang) {
            AppLanguage.RU -> "Сверхширокоугольная"
            AppLanguage.UA -> "Надширококутна"
            AppLanguage.EN -> "Ultra-Wide"
        }
        TELEPHOTO -> when (lang) {
            AppLanguage.RU -> "Телеобъектив / Зум"
            AppLanguage.UA -> "Телеоб'єктив / Зум"
            AppLanguage.EN -> "Telephoto / Optical Zoom"
        }
        PERISCOPE -> when (lang) {
            AppLanguage.RU -> "Перископический телеобъектив"
            AppLanguage.UA -> "Перископічний телеоб'єктив"
            AppLanguage.EN -> "Periscope Telephoto"
        }
        MACRO -> when (lang) {
            AppLanguage.RU -> "Макро-камера"
            AppLanguage.UA -> "Макро-камера"
            AppLanguage.EN -> "Macro Camera"
        }
        DEPTH_TOF -> when (lang) {
            AppLanguage.RU -> "Сенсор глубины / ToF"
            AppLanguage.UA -> "Сенсор глибини / ToF"
            AppLanguage.EN -> "Depth / ToF Sensor"
        }
        MONOCHROME -> when (lang) {
            AppLanguage.RU -> "Монохромный сенсор"
            AppLanguage.UA -> "Монохромний сенсор"
            AppLanguage.EN -> "Monochrome Sensor"
        }
        FRONT_SELFIE -> when (lang) {
            AppLanguage.RU -> "Фронтальная (Селфи)"
            AppLanguage.UA -> "Фронтальна (Селфі)"
            AppLanguage.EN -> "Front (Selfie)"
        }
        FRONT_ULTRAWIDE -> when (lang) {
            AppLanguage.RU -> "Сверхширокоугольная селфи"
            AppLanguage.UA -> "Надширококутна селфі"
            AppLanguage.EN -> "Ultra-Wide Selfie"
        }
        UNKNOWN -> when (lang) {
            AppLanguage.RU -> "Дополнительная камера"
            AppLanguage.UA -> "Додаткова камера"
            AppLanguage.EN -> "Auxiliary Camera"
        }
    }
}

data class SensorVendorGuess(
    val vendorName: String,
    val probableModels: List<String>,
    val confidence: String,
    val details: String
)

data class CameraItem(
    val id: String,
    val isLogical: Boolean,
    val parentLogicalId: String? = null,
    val physicalIds: List<String> = emptyList(),
    val facing: CameraFacing,
    val role: CameraRole,
    val sensorVendorGuess: SensorVendorGuess,
    val megapixels: Double,
    val resolutionWidth: Int,
    val resolutionHeight: Int,
    val resolutionText: String,
    val pixelArraySize: String,
    val activeArraySize: String,
    val physicalWidthMm: Float,
    val physicalHeightMm: Float,
    val diagonalMm: Float,
    val opticalFormat: String,
    val pixelPitchMicrons: Float,
    val focalLengthsMm: List<Float>,
    val focalLength35mmEq: Float?,
    val apertures: List<Float>,
    val horizontalFovDeg: Float,
    val verticalFovDeg: Float,
    val diagonalFovDeg: Float,
    val hardwareLevel: String,
    val oisSupported: Boolean,
    val eisSupported: Boolean,
    val rawSupported: Boolean,
    val flashSupported: Boolean,
    val afModes: List<String>,
    val minFocusDistanceMeters: Float?,
    val isMacroCapable: Boolean,
    val isoRange: Pair<Int, Int>?,
    val exposureTimeRangeMs: Pair<Double, Double>?,
    val colorFilter: String,
    val maxFps: Int,
    val highSpeedFpsList: List<Int>,
    val videoResolutions: List<String>,
    val detectionSource: String = "Camera HAL"
) {
    fun getTechnicalSummary(lang: AppLanguage): Map<String, String> = buildMap {
        val isRu = lang == AppLanguage.RU
        val isUa = lang == AppLanguage.UA

        val kId = if (isRu) "ID камеры" else if (isUa) "ID камери" else "Camera ID"
        put(kId, id)

        val kType = if (isRu) "Тип модуля" else if (isUa) "Тип модуля" else "Module Type"
        val vType = if (isLogical) {
            if (isRu) "Логическая мульти-камера" else if (isUa) "Логічна мульти-камера" else "Logical Multi-Camera"
        } else {
            if (isRu) "Физический сенсор" else if (isUa) "Фізичний сенсор" else "Physical Sensor"
        }
        put(kType, vType)

        val kDetectionSource = if (isRu) "Источник детекции" else if (isUa) "Джерело детекції" else "Detection Source"
        put(kDetectionSource, detectionSource)

        val kRes = if (isRu) "Разрешение матрицы" else if (isUa) "Роздільність матриці" else "Sensor Resolution"
        val vMpUnit = if (isRu) "Мп" else if (isUa) "Мп" else "MP"
        val vPxUnit = if (isRu) "пикселей" else if (isUa) "пікселів" else "pixels"
        put(kRes, "$resolutionWidth × $resolutionHeight $vPxUnit ($megapixels $vMpUnit)")

        val kSize = if (isRu) "Размер матрицы" else if (isUa) "Розмір матриці" else "Physical Sensor Size"
        put(kSize, "${"%.2f".format(physicalWidthMm)} × ${"%.2f".format(physicalHeightMm)} mm")

        val kDiag = if (isRu) "Диагональ и формат" else if (isUa) "Діагональ і формат" else "Diagonal & Format"
        put(kDiag, "${"%.2f".format(diagonalMm)} mm ($opticalFormat)")

        val kPitch = if (isRu) "Размер отдельного пикселя" else if (isUa) "Розмір окремого пікселя" else "Pixel Pitch"
        put(kPitch, "${"%.2f".format(pixelPitchMicrons)} µm (мкм)")

        val kFocal = if (isRu) "Фокусное расстояние" else if (isUa) "Фокусна відстань" else "Focal Length"
        val eqText = if (focalLength35mmEq != null) {
            if (isRu) " (~${focalLength35mmEq.roundToInt()} мм в экв. 35мм)"
            else if (isUa) " (~${focalLength35mmEq.roundToInt()} мм в екв. 35мм)"
            else " (~${focalLength35mmEq.roundToInt()} mm 35mm eq.)"
        } else ""
        put(kFocal, "${focalLengthsMm.joinToString(", ") { "${it} mm" }}$eqText")

        val kAperture = if (isRu) "Светосила (Диафрагма)" else if (isUa) "Світлосила (Діафрагма)" else "Aperture"
        put(kAperture, apertures.joinToString(", ") { "f/$it" })

        val kFov = if (isRu) "Угол обзора (FoV)" else if (isUa) "Кут огляду (FoV)" else "Field of View (FoV)"
        val vFov = if (isRu) "Диагональ: ${diagonalFovDeg.roundToInt()}°, горизонт: ${horizontalFovDeg.roundToInt()}°"
        else if (isUa) "Діагональ: ${diagonalFovDeg.roundToInt()}°, горизонт: ${horizontalFovDeg.roundToInt()}°"
        else "Diagonal: ${diagonalFovDeg.roundToInt()}°, Horizontal: ${horizontalFovDeg.roundToInt()}°"
        put(kFov, vFov)

        val kHal = if (isRu) "Аппаратный уровень HAL" else if (isUa) "Апаратний рівень HAL" else "Camera2 HAL Level"
        put(kHal, hardwareLevel)

        val kOis = if (isRu) "Оптическая стабилизация (OIS)" else if (isUa) "Оптична стабілізація (OIS)" else "Optical Stabilization (OIS)"
        val vOis = if (oisSupported) {
            if (isRu) "Поддерживается (Аппаратный OIS)" else if (isUa) "Підтримується (Апаратний OIS)" else "Supported (Hardware OIS)"
        } else {
            if (isRu) "Отсутствует" else if (isUa) "Відсутній" else "Not supported"
        }
        put(kOis, vOis)

        val kRaw = if (isRu) "Съемка в RAW (DNG)" else if (isUa) "Зйомка в RAW (DNG)" else "RAW Capture (DNG)"
        val vRaw = if (rawSupported) {
            if (isRu) "Поддерживается (RAW_SENSOR)" else if (isUa) "Підтримується (RAW_SENSOR)" else "Supported (RAW_SENSOR)"
        } else {
            if (isRu) "Не поддерживается" else if (isUa) "Не підтримується" else "Not supported"
        }
        put(kRaw, vRaw)

        val kFlash = if (isRu) "Вспышка / Фонарик" else if (isUa) "Спалах / Ліхтарик" else "Flashlight"
        val vFlash = if (flashSupported) {
            if (isRu) "Присутствует" else if (isUa) "Присутній" else "Available"
        } else {
            if (isRu) "Отсутствует" else if (isUa) "Відсутній" else "None"
        }
        put(kFlash, vFlash)

        if (isoRange != null) {
            val kIso = if (isRu) "Диапазон ISO" else if (isUa) "Діапазон ISO" else "ISO Range"
            put(kIso, "ISO ${isoRange.first} — ISO ${isoRange.second}")
        }

        if (exposureTimeRangeMs != null) {
            val kExp = if (isRu) "Диапазон выдержки" else if (isUa) "Діапазон витримки" else "Exposure Range"
            val minMs = exposureTimeRangeMs.first
            val maxMs = exposureTimeRangeMs.second
            val minText = if (minMs < 1.0) "1/${(1000.0 / minMs).roundToInt()} s" else "${"%.2f".format(minMs)} ms"
            val maxText = if (maxMs >= 1000.0) "${"%.1f".format(maxMs / 1000.0)} s" else "${"%.1f".format(maxMs)} ms"
            put(kExp, "$minText — $maxText")
        }

        val kMaxFps = if (isRu) "Макс. частота кадров" else if (isUa) "Макс. частота кадрів" else "Max Framerate"
        put(kMaxFps, "$maxFps fps")

        if (highSpeedFpsList.isNotEmpty()) {
            val kSlowMo = if (isRu) "Замедленное видео (Slow-mo)" else if (isUa) "Уповільнене відео (Slow-mo)" else "High-speed Video"
            put(kSlowMo, "${highSpeedFpsList.joinToString(", ")} fps")
        }
    }
}
