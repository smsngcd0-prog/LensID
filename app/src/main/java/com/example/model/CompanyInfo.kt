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
            AppLanguage.ES -> "Fabricante de sensores (Matrices CMOS)"
            AppLanguage.PT, AppLanguage.PT_BR -> "Fabricante de sensores (Matrizes CMOS)"
            AppLanguage.FR -> "Fonderie de capteurs (Matrices CMOS)"
            AppLanguage.IT -> "Produttore di sensori (Matrici CMOS)"
            AppLanguage.DE -> "Sensorhersteller (CMOS-Matrizen)"
            else -> "CMOS Image Sensor Foundry"
        }
        MODULE_ASSEMBLER -> when (lang) {
            AppLanguage.RU -> "Сборщик модулей и линз"
            AppLanguage.UA -> "Складальник модулів та лінз"
            AppLanguage.ES -> "Ensamblador de módulos de cámara (CCM)"
            AppLanguage.PT, AppLanguage.PT_BR -> "Montador de módulos de câmera (CCM)"
            AppLanguage.FR -> "Assembleur de modules de caméra (CCM)"
            AppLanguage.IT -> "Assemblatore di moduli fotocamera (CCM)"
            AppLanguage.DE -> "Kameramodul-Hersteller (CCM)"
            else -> "Camera Module Assembler (CCM)"
        }
        LENS_OPTICS -> when (lang) {
            AppLanguage.RU -> "Оптические бренды и партнёры"
            AppLanguage.UA -> "Оптичні бренди та партнери"
            AppLanguage.ES -> "Marcas ópticas y lentes"
            AppLanguage.PT, AppLanguage.PT_BR -> "Marcas ópticas e lentes"
            AppLanguage.FR -> "Optique et marques de lentilles"
            AppLanguage.IT -> "Marchi ottici e lenti"
            AppLanguage.DE -> "Optik-Marken und Linsen"
            else -> "Optical Brands & Partners"
        }
        ISP_CHIPSET -> when (lang) {
            AppLanguage.RU -> "Процессоры обработки (ISP)"
            AppLanguage.UA -> "Процесори обробки (ISP)"
            AppLanguage.ES -> "Procesadores de imagen (ISP)"
            AppLanguage.PT, AppLanguage.PT_BR -> "Processadores de imagem (ISP)"
            AppLanguage.FR -> "Processeurs de signal d'image (ISP)"
            AppLanguage.IT -> "Processori di segnale d'immagine (ISP)"
            AppLanguage.DE -> "Bildsignalprozessoren (ISP)"
            else -> "Image Signal Processors (ISP)"
        }
        SPECIALTY -> when (lang) {
            AppLanguage.RU -> "Специальные сенсоры (ToF / Спектр)"
            AppLanguage.UA -> "Спеціальні сенсори (ToF / Спектр)"
            AppLanguage.ES -> "Sensores especializados (ToF / Espectro)"
            AppLanguage.PT, AppLanguage.PT_BR -> "Sensores especiais (ToF / Espectro)"
            AppLanguage.FR -> "Capteurs spécialisés (ToF / Spectre)"
            AppLanguage.IT -> "Sensori speciali (ToF / Spettro)"
            AppLanguage.DE -> "Spezialsensoren (ToF / Spektrum)"
            else -> "Specialty Sensors (ToF / Flicker)"
        }
    }

    fun getBadgeText(lang: AppLanguage): String = when (this) {
        SENSOR_FOUNDRY -> when (lang) {
            AppLanguage.RU -> "СЕНСОРЫ"
            AppLanguage.UA -> "СЕНСОРИ"
            AppLanguage.ES, AppLanguage.PT, AppLanguage.PT_BR -> "SENSORES"
            AppLanguage.FR -> "CAPTEURS"
            AppLanguage.IT -> "SENSORI"
            AppLanguage.DE -> "SENSOREN"
            else -> "FOUNDRY"
        }
        MODULE_ASSEMBLER -> when (lang) {
            AppLanguage.RU -> "МОДУЛИ & ЛИНЗЫ"
            AppLanguage.UA -> "МОДУЛІ ТА ЛІНЗИ"
            AppLanguage.ES -> "MÓDULOS"
            AppLanguage.PT, AppLanguage.PT_BR -> "MÓDULOS"
            AppLanguage.FR -> "MODULES"
            AppLanguage.IT -> "MODULI"
            AppLanguage.DE -> "MODULE"
            else -> "MODULES"
        }
        LENS_OPTICS -> when (lang) {
            AppLanguage.RU, AppLanguage.UA -> "ОПТИКА"
            AppLanguage.ES -> "ÓPTICA"
            AppLanguage.PT, AppLanguage.PT_BR -> "ÓPTICA"
            AppLanguage.FR -> "OPTIQUE"
            AppLanguage.IT -> "OTTICA"
            AppLanguage.DE -> "OPTIK"
            else -> "OPTICS"
        }
        ISP_CHIPSET -> "ISP / SOC"
        SPECIALTY -> when (lang) {
            AppLanguage.RU -> "СПЕЦ-СЕНСОРЫ"
            AppLanguage.UA -> "СПЕЦ-СЕНСОРИ"
            AppLanguage.ES -> "ESPECIAL"
            AppLanguage.PT, AppLanguage.PT_BR -> "ESPECIAL"
            AppLanguage.FR -> "SPÉCIALISÉ"
            AppLanguage.IT -> "SPECIALE"
            AppLanguage.DE -> "SPEZIAL"
            else -> "SPECIALTY"
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
        else -> countryEn
    }

    fun getMarketRole(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> marketRoleRu
        AppLanguage.UA -> marketRoleUa
        else -> marketRoleEn
    }

    fun getMarketShare(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> marketShareRu
        AppLanguage.UA -> marketShareUa
        else -> marketShareEn
    }

    fun getDescription(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> descriptionRu
        AppLanguage.UA -> descriptionUa
        else -> descriptionEn
    }

    fun getRelevance(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> relevanceRu
        AppLanguage.UA -> relevanceUa
        else -> relevanceEn
    }

    fun getSpecialCommentary(lang: AppLanguage): String? = when (lang) {
        AppLanguage.RU -> specialCommentaryRu
        AppLanguage.UA -> specialCommentaryUa
        else -> specialCommentaryEn
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
        else -> summaryEn
    }
}
