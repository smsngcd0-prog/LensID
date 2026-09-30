package com.example.model

import com.example.localization.AppLanguage

data class SensorCatalogEntry(
    val vendor: String,
    val modelName: String,
    val megapixels: Double,
    val opticalFormat: String,
    val sensorWidthMm: Float,
    val sensorHeightMm: Float,
    val pixelPitchMicrons: Float,
    val maxResolution: String,
    val releaseYear: Int,
    val autofocusTech: String,
    val keyFeaturesRu: String,
    val keyFeaturesUa: String,
    val keyFeaturesEn: String,
    val typicalPhones: String
) {
    fun getKeyFeatures(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> keyFeaturesRu
        AppLanguage.UA -> keyFeaturesUa
        AppLanguage.EN -> keyFeaturesEn
    }
}
