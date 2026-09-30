package com.example.model

data class SensorCatalogEntry(
    val vendor: String, // "Sony", "Samsung", "OmniVision", "GalaxyCore", "SK Hynix"
    val modelName: String,
    val megapixels: Double,
    val opticalFormat: String, // e.g. "1/1.56\""
    val sensorWidthMm: Float,
    val sensorHeightMm: Float,
    val pixelPitchMicrons: Float, // e.g. 1.00 µm
    val maxResolution: String, // "8192 × 6144"
    val releaseYear: Int,
    val autofocusTech: String, // "All-pixel Omni-directional PDAF", "Dual Pixel Pro", "QPD"
    val keyFeaturesRu: String,
    val typicalPhones: String
)
