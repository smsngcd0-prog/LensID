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

    fun getTitle(lang: AppLanguage): String =
        com.example.localization.AppStrings.getCameraRoleTitle(this, lang)
}

data class SensorVendorGuess(
    val vendorName: String,
    val probableModels: List<String>,
    val confidenceRu: String,
    val confidenceUa: String,
    val confidenceEn: String,
    val detailsRu: String,
    val detailsUa: String,
    val detailsEn: String
) {
    fun getConfidence(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> confidenceRu
        AppLanguage.UA -> confidenceUa
        else -> confidenceEn
    }

    fun getDetails(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> detailsRu
        AppLanguage.UA -> detailsUa
        else -> detailsEn
    }
}

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
    val detectionSourceRu: String,
    val detectionSourceUa: String,
    val detectionSourceEn: String
) {
    fun getDetectionSource(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> detectionSourceRu
        AppLanguage.UA -> detectionSourceUa
        AppLanguage.ES -> if (detectionSourceEn.contains("Driver")) "Controlador de hardware del kernel de Linux" else "Heurística de hardware Android Camera2"
        AppLanguage.PT, AppLanguage.PT_BR -> if (detectionSourceEn.contains("Driver")) "Driver de hardware do kernel Linux" else "Heurística de hardware Android Camera2"
        AppLanguage.FR -> if (detectionSourceEn.contains("Driver")) "Pilote matériel du noyau Linux" else "Heuristique matérielle Android Camera2"
        AppLanguage.IT -> if (detectionSourceEn.contains("Driver")) "Driver hardware del kernel Linux" else "Euristica hardware Android Camera2"
        AppLanguage.DE -> if (detectionSourceEn.contains("Driver")) "Linux-Kernel Hardware-Treiber" else "Android Camera2 Hardware-Heuristik"
        else -> detectionSourceEn
    }

    fun getTechnicalSummary(lang: AppLanguage): Map<String, String> = buildMap {
        val isRu = lang == AppLanguage.RU
        val isUa = lang == AppLanguage.UA
        val isEs = lang == AppLanguage.ES
        val isPt = lang == AppLanguage.PT || lang == AppLanguage.PT_BR
        val isFr = lang == AppLanguage.FR
        val isIt = lang == AppLanguage.IT
        val isDe = lang == AppLanguage.DE

        val kId = if (isRu) "ID камеры" else if (isUa) "ID камери" else if (isEs || isPt) "ID da câmera" else if (isFr) "ID de la caméra" else if (isIt) "ID fotocamera" else if (isDe) "Kamera-ID" else "Camera ID"
        put(kId, id)

        val kType = if (isRu) "Тип модуля" else if (isUa) "Тип модуля" else if (isEs) "Tipo de módulo" else if (isPt) "Tipo de módulo" else if (isFr) "Type de module" else if (isIt) "Tipo di modulo" else if (isDe) "Modultyp" else "Module Type"
        val vType = if (isLogical) {
            if (isRu) "Логическая мульти-камера" else if (isUa) "Логічна мульти-камера" else if (isEs) "Multi-cámara lógica" else if (isPt) "Multi-câmera lógica" else if (isFr) "Multi-caméra logique" else if (isIt) "Multi-fotocamera logica" else if (isDe) "Logische Multikamera" else "Logical Multi-Camera"
        } else {
            if (isRu) "Физический сенсор" else if (isUa) "Фізичний сенсор" else if (isEs) "Sensor físico" else if (isPt) "Sensor físico" else if (isFr) "Capteur physique" else if (isIt) "Sensore fisico" else if (isDe) "Physischer Sensor" else "Physical Sensor"
        }
        put(kType, vType)

        val kDetectionSource = if (isRu) "Источник детекции" else if (isUa) "Джерело детекції" else if (isEs) "Fuente de detección" else if (isPt) "Fonte de detecção" else if (isFr) "Source de détection" else if (isIt) "Sorgente di rilevamento" else if (isDe) "Erkennungsquelle" else "Detection Source"
        put(kDetectionSource, getDetectionSource(lang))

        val kRes = if (isRu) "Разрешение матрицы" else if (isUa) "Роздільність матриці" else if (isEs) "Resolución del sensor" else if (isPt) "Resolução do sensor" else if (isFr) "Résolution du capteur" else if (isIt) "Risoluzione del sensore" else if (isDe) "Sensorauflösung" else "Sensor Resolution"
        val vMpUnit = if (isRu || isUa) "Мп" else "MP"
        val vPxUnit = if (isRu) "пикселей" else if (isUa) "пікселів" else if (isEs) "píxeles" else if (isPt) "pixels" else if (isFr) "pixels" else if (isIt) "pixel" else if (isDe) "Pixel" else "pixels"
        put(kRes, "$resolutionWidth × $resolutionHeight $vPxUnit ($megapixels $vMpUnit)")

        val kSize = if (isRu) "Размер матрицы" else if (isUa) "Розмір матриці" else if (isEs) "Tamaño del sensor" else if (isPt) "Tamanho do sensor" else if (isFr) "Taille du capteur" else if (isIt) "Dimensione sensore" else if (isDe) "Sensorgröße" else "Physical Sensor Size"
        put(kSize, "${"%.2f".format(physicalWidthMm)} × ${"%.2f".format(physicalHeightMm)} mm")

        val kDiag = if (isRu) "Диагональ и формат" else if (isUa) "Діагональ і формат" else if (isEs) "Diagonal y formato" else if (isPt) "Diagonal e formato" else if (isFr) "Diagonale et format" else if (isIt) "Diagonale e formato" else if (isDe) "Diagonale und Format" else "Diagonal & Format"
        put(kDiag, "${"%.2f".format(diagonalMm)} mm ($opticalFormat)")

        val kPitch = if (isRu) "Размер отдельного пикселя" else if (isUa) "Розмір окремого пікселя" else if (isEs) "Tamaño de píxel" else if (isPt) "Tamanho do pixel" else if (isFr) "Taille du pixel" else if (isIt) "Dimensione pixel" else if (isDe) "Pixelgröße" else "Pixel Pitch"
        put(kPitch, "${"%.2f".format(pixelPitchMicrons)} µm")

        val kFocal = if (isRu) "Фокусное расстояние" else if (isUa) "Фокусна відстань" else if (isEs) "Distancia focal" else if (isPt) "Distância focal" else if (isFr) "Distance focale" else if (isIt) "Lunghezza focale" else if (isDe) "Brennweite" else "Focal Length"
        val eqText = if (focalLength35mmEq != null) {
            if (isRu) " (~${focalLength35mmEq.roundToInt()} мм в экв. 35мм)"
            else if (isUa) " (~${focalLength35mmEq.roundToInt()} мм в екв. 35мм)"
            else " (~${focalLength35mmEq.roundToInt()} mm 35mm eq.)"
        } else ""
        put(kFocal, "${focalLengthsMm.joinToString(", ") { "${it} mm" }}$eqText")

        val kAperture = if (isRu) "Светосила (Диафрагма)" else if (isUa) "Світлосила (Діафрагма)" else if (isEs) "Apertura" else if (isPt) "Abertura" else if (isFr) "Ouverture" else if (isIt) "Apertura" else if (isDe) "Blende" else "Aperture"
        put(kAperture, apertures.joinToString(", ") { "f/$it" })

        val kFov = if (isRu) "Угол обзора (FoV)" else if (isUa) "Кут огляду (FoV)" else if (isEs) "Campo de visión (FoV)" else if (isPt) "Campo de visão (FoV)" else if (isFr) "Champ de vision (FoV)" else if (isIt) "Campo visivo (FoV)" else if (isDe) "Sichtfeld (FoV)" else "Field of View (FoV)"
        val vFov = "Diag: ${diagonalFovDeg.roundToInt()}°, Horiz: ${horizontalFovDeg.roundToInt()}°"
        put(kFov, vFov)

        val kHal = if (isRu) "Аппаратный уровень HAL" else if (isUa) "Апаратний рівень HAL" else "Camera2 HAL Level"
        put(kHal, hardwareLevel)

        val kOis = if (isRu) "Оптическая стабилизация (OIS)" else if (isUa) "Оптична стабілізація (OIS)" else if (isEs) "Estabilización óptica (OIS)" else if (isPt) "Estabilização óptica (OIS)" else if (isFr) "Stabilisation optique (OIS)" else if (isIt) "Stabilizzazione ottica (OIS)" else if (isDe) "Optische Stabilisierung (OIS)" else "Optical Stabilization (OIS)"
        val vOis = if (oisSupported) {
            if (isRu) "Поддерживается (Аппаратный OIS)" else if (isUa) "Підтримується (Апаратний OIS)" else if (isEs) "Compatible (OIS hardware)" else if (isPt) "Suportado (OIS hardware)" else if (isFr) "Pris en charge (OIS matériel)" else if (isIt) "Supportato (OIS hardware)" else if (isDe) "Unterstützt (Hardware OIS)" else "Supported (Hardware OIS)"
        } else {
            if (isRu) "Отсутствует" else if (isUa) "Відсутній" else if (isEs) "No compatible" else if (isPt) "Não suportado" else if (isFr) "Non pris en charge" else if (isIt) "Non supportato" else if (isDe) "Nicht vorhanden" else "Not supported"
        }
        put(kOis, vOis)

        val kRaw = if (isRu) "Съемка в RAW (DNG)" else if (isUa) "Зйомка в RAW (DNG)" else if (isEs) "Captura en RAW (DNG)" else if (isPt) "Captura em RAW (DNG)" else if (isFr) "Prise de vue RAW (DNG)" else if (isIt) "Scatto in RAW (DNG)" else if (isDe) "RAW-Aufnahme (DNG)" else "RAW Capture (DNG)"
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
