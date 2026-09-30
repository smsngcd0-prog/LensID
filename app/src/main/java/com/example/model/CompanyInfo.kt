package com.example.model

import com.example.localization.AppLanguage

enum class CompanyCategory(val badge: String) {
    SENSOR_FOUNDRY("FOUNDRY"),
    MODULE_ASSEMBLER("MODULES"),
    LENS_OPTICS("OPTICS"),
    ISP_CHIPSET("ISP / SOC"),
    SPECIALTY("SPECIALTY");

    fun getTitle(lang: AppLanguage): String = when (this) {
        SENSOR_FOUNDRY -> when (lang) {
            AppLanguage.RU -> "Производитель сенсоров (Матрицы)"
            AppLanguage.UA -> "Виробник сенсорів (Матриці)"
            AppLanguage.EN -> "CMOS Image Sensor Foundry"
        }
        MODULE_ASSEMBLER -> when (lang) {
            AppLanguage.RU -> "Сборщик модулей и линз"
            AppLanguage.UA -> "Складальник модулів та лінз"
            AppLanguage.EN -> "Camera Module Assembler (CCM)"
        }
        LENS_OPTICS -> when (lang) {
            AppLanguage.RU -> "Оптические бренды и партнёры"
            AppLanguage.UA -> "Оптичні бренди та партнери"
            AppLanguage.EN -> "Optical Brands & Partners"
        }
        ISP_CHIPSET -> when (lang) {
            AppLanguage.RU -> "Процессоры обработки (ISP)"
            AppLanguage.UA -> "Процесори обробки (ISP)"
            AppLanguage.EN -> "Image Signal Processors (ISP)"
        }
        SPECIALTY -> when (lang) {
            AppLanguage.RU -> "Специальные сенсоры (ToF / Спектр)"
            AppLanguage.UA -> "Спеціальні сенсори (ToF / Спектр)"
            AppLanguage.EN -> "Specialty Sensors (ToF / Flicker)"
        }
    }

    fun getBadgeText(lang: AppLanguage): String = when (this) {
        SENSOR_FOUNDRY -> when (lang) {
            AppLanguage.RU -> "СЕНСОРЫ"
            AppLanguage.UA -> "СЕНСОРИ"
            AppLanguage.EN -> "FOUNDRY"
        }
        MODULE_ASSEMBLER -> when (lang) {
            AppLanguage.RU -> "МОДУЛИ & ЛИНЗЫ"
            AppLanguage.UA -> "МОДУЛІ ТА ЛІНЗИ"
            AppLanguage.EN -> "MODULES"
        }
        LENS_OPTICS -> when (lang) {
            AppLanguage.RU -> "ОПТИКА"
            AppLanguage.UA -> "ОПТИКА"
            AppLanguage.EN -> "OPTICS"
        }
        ISP_CHIPSET -> "ISP / SOC"
        SPECIALTY -> when (lang) {
            AppLanguage.RU -> "СПЕЦ-СЕНСОРЫ"
            AppLanguage.UA -> "СПЕЦ-СЕНСОРИ"
            AppLanguage.EN -> "SPECIALTY"
        }
    }
}

data class CompanyInfo(
    val id: String,
    val name: String,
    val logoText: String,
    val countryRu: String,
    val countryUa: String,
    val countryEn: String,
    val flagEmoji: String,
    val category: CompanyCategory,
    val marketRoleRu: String,
    val marketRoleUa: String,
    val marketRoleEn: String,
    val marketShareRu: String,
    val marketShareUa: String,
    val marketShareEn: String,
    val keyProducts: List<String>,
    val relevanceRu: String,
    val relevanceUa: String,
    val relevanceEn: String,
    val isPrimaryCandidate: Boolean = false,
    val descriptionRu: String,
    val descriptionUa: String,
    val descriptionEn: String,
    val specialCommentaryRu: String? = null,
    val specialCommentaryUa: String? = null,
    val specialCommentaryEn: String? = null,
    val website: String
) {
    fun getCountry(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> countryRu
        AppLanguage.UA -> countryUa
        AppLanguage.EN -> countryEn
    }

    fun getMarketRole(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> marketRoleRu
        AppLanguage.UA -> marketRoleUa
        AppLanguage.EN -> marketRoleEn
    }

    fun getMarketShare(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> marketShareRu
        AppLanguage.UA -> marketShareUa
        AppLanguage.EN -> marketShareEn
    }

    fun getDescription(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> descriptionRu
        AppLanguage.UA -> descriptionUa
        AppLanguage.EN -> descriptionEn
    }

    fun getRelevance(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> relevanceRu
        AppLanguage.UA -> relevanceUa
        AppLanguage.EN -> relevanceEn
    }

    fun getSpecialCommentary(lang: AppLanguage): String? = when (lang) {
        AppLanguage.RU -> specialCommentaryRu
        AppLanguage.UA -> specialCommentaryUa
        AppLanguage.EN -> specialCommentaryEn
    }
}

data class DeviceSupplierAnalysis(
    val brandTitle: String,
    val summaryRu: String,
    val summaryUa: String,
    val summaryEn: String,
    val mostLikelySensorVendors: List<String>,
    val mostLikelyModuleMakers: List<String>,
    val mostLikelyIsp: String,
    val opticPartnership: String? = null
) {
    fun getSummary(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> summaryRu
        AppLanguage.UA -> summaryUa
        AppLanguage.EN -> summaryEn
    }
}
