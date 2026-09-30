package com.example.model

enum class CompanyCategory(val titleRu: String, val badge: String) {
    SENSOR_FOUNDRY("Производитель сенсоров (Матрицы)", "СЕНСОРЫ"),
    MODULE_ASSEMBLER("Сборщик модулей и линз", "МОДУЛИ & ЛИНЗЫ"),
    LENS_OPTICS("Оптические бренды и партнёры", "ОПТИКА"),
    ISP_CHIPSET("Процессоры обработки (ISP)", "ISP / ЧИПСЕТ"),
    SPECIALTY("Специальные сенсоры (ToF / Спектр)", "СПЕЦ-СЕНСОРЫ")
}

data class CompanyInfo(
    val id: String,
    val name: String,
    val logoText: String,
    val country: String,
    val flagEmoji: String,
    val category: CompanyCategory,
    val marketRoleRu: String,
    val marketShareRu: String,
    val keyProducts: List<String>,
    val relevanceToThisDevice: String,
    val isPrimaryCandidate: Boolean,
    val descriptionRu: String,
    val specialCommentary: String? = null,
    val website: String
)

data class DeviceSupplierAnalysis(
    val brandTitle: String,
    val summaryRu: String,
    val mostLikelySensorVendors: List<String>,
    val mostLikelyModuleMakers: List<String>,
    val mostLikelyIsp: String,
    val opticPartnership: String? = null
)
