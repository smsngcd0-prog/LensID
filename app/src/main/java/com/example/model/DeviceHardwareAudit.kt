package com.example.model

import com.example.localization.AppLanguage

data class CpuAudit(
    val realSocName: String,
    val vendor: String,
    val architecture: String,
    val coreCount: Int,
    val coreConfiguration: String,
    val gpuModel: String,
    val processNodeNm: String,
    val is64Bit: Boolean,
    val abiList: List<String>
)

data class StorageAudit(
    val physicalChipCapacityGb: Double,
    val reportedTotalStorageGb: Double,
    val freeStorageGb: Double,
    val flashStorageType: String,
    val isSpoofed: Boolean,
    val integrityMessageRu: String,
    val integrityMessageUa: String,
    val integrityMessageEn: String
) {
    fun getIntegrityMessage(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> integrityMessageRu
        AppLanguage.UA -> integrityMessageUa
        AppLanguage.EN -> integrityMessageEn
    }
}

data class BatteryAudit(
    val designCapacityMah: Int,
    val estimatedActualCapacityMah: Int,
    val healthPercentage: Int,
    val currentLevelPercent: Int,
    val cycleCount: Int?,
    val temperatureC: Float,
    val voltageMv: Int,
    val technology: String,
    val isCharging: Boolean,
    val healthSummaryRu: String,
    val healthSummaryUa: String,
    val healthSummaryEn: String
) {
    val wearPercentage: Int
        get() = (100 - healthPercentage).coerceAtLeast(0)

    val wearLossMah: Int
        get() = (designCapacityMah - estimatedActualCapacityMah).coerceAtLeast(0)

    fun getHealthSummary(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> healthSummaryRu
        AppLanguage.UA -> healthSummaryUa
        AppLanguage.EN -> healthSummaryEn
    }
}

data class RamAudit(
    val physicalRamGb: Double,
    val virtualRamGb: Double,
    val totalEffectiveRamGb: Double,
    val usedRamGb: Double,
    val availableRamGb: Double,
    val ramType: String
)

data class WinlatorAudit(
    val ratingStars: String,
    val ratingLabelRu: String,
    val ratingLabelUa: String,
    val ratingLabelEn: String,
    val turnipDriverSupported: Boolean,
    val box64Supported: Boolean,
    val recommendedDriver: String,
    val dxvkSupported: Boolean,
    val explanationRu: String,
    val explanationUa: String,
    val explanationEn: String,
    val playableGamesRu: String,
    val playableGamesUa: String,
    val playableGamesEn: String
) {
    fun getRatingLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> ratingLabelRu
        AppLanguage.UA -> ratingLabelUa
        AppLanguage.EN -> ratingLabelEn
    }

    fun getExplanation(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> explanationRu
        AppLanguage.UA -> explanationUa
        AppLanguage.EN -> explanationEn
    }

    fun getPlayableGames(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> playableGamesRu
        AppLanguage.UA -> playableGamesUa
        AppLanguage.EN -> playableGamesEn
    }
}

data class AntutuAudit(
    val estimatedTotalScore: Int,
    val cpuScore: Int,
    val gpuScore: Int,
    val memScore: Int,
    val uxScore: Int,
    val tierLabelRu: String,
    val tierLabelUa: String,
    val tierLabelEn: String,
    val comparisonNoteRu: String,
    val comparisonNoteUa: String,
    val comparisonNoteEn: String
) {
    fun getTierLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> tierLabelRu
        AppLanguage.UA -> tierLabelUa
        AppLanguage.EN -> tierLabelEn
    }

    fun getComparisonNote(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> comparisonNoteRu
        AppLanguage.UA -> comparisonNoteUa
        AppLanguage.EN -> comparisonNoteEn
    }
}

data class DeviceHardwareAudit(
    val cpu: CpuAudit,
    val storage: StorageAudit,
    val battery: BatteryAudit,
    val ram: RamAudit,
    val winlator: WinlatorAudit,
    val antutu: AntutuAudit
)
