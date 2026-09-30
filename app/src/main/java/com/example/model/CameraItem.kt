package com.example.model

enum class CameraFacing {
    BACK,
    FRONT,
    EXTERNAL,
    UNKNOWN
}

enum class CameraRole(val titleRu: String, val badge: String) {
    MAIN_WIDE("Основная (Широкоугольная)", "MAIN"),
    ULTRA_WIDE("Сверхширокоугольная", "ULTRA-WIDE"),
    TELEPHOTO("Телеобъектив / Зум", "TELEPHOTO"),
    PERISCOPE("Перископический телеобъектив", "PERISCOPE"),
    MACRO("Макро-камера", "MACRO"),
    DEPTH_TOF("Сенсор глубины / ToF", "DEPTH / ToF"),
    MONOCHROME("Монохромный сенсор", "MONO"),
    FRONT_SELFIE("Фронтальная (Селфи)", "FRONT"),
    FRONT_ULTRAWIDE("Сверхширокоугольная селфи", "FRONT-UW"),
    UNKNOWN("Дополнительная камера", "AUX")
}

data class SensorVendorGuess(
    val vendorName: String, // "Sony", "Samsung", "OmniVision", "GalaxyCore", "SK Hynix", "STMicroelectronics"
    val probableModels: List<String>, // e.g. ["IMX766", "IMX890"] or ["ISOCELL GN5", "ISOCELL GN3"]
    val confidence: String, // "Высокая (по совпадению физических параметров)", "Вероятная", "Определено HAL"
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
    val opticalFormat: String, // e.g. "1/1.56\""
    val pixelPitchMicrons: Float, // e.g. 1.00 µm
    val focalLengthsMm: List<Float>,
    val focalLength35mmEq: Float?,
    val apertures: List<Float>,
    val horizontalFovDeg: Float,
    val verticalFovDeg: Float,
    val diagonalFovDeg: Float,
    val hardwareLevel: String, // LEVEL_3, FULL, LIMITED, LEGACY
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
    val technicalSummary: Map<String, String>
)
