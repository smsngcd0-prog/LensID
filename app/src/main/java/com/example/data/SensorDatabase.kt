package com.example.data

import com.example.localization.AppLanguage
import com.example.model.SensorCatalogEntry
import com.example.model.SensorVendorGuess
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sqrt

object SensorDatabase {

    val CATALOG: List<SensorCatalogEntry> = listOf(
        // SmartSens Technology (思特威)
        SensorCatalogEntry(
            vendor = "SmartSens",
            modelName = "SC500CS",
            megapixels = 50.0,
            opticalFormat = "1/2.76\"",
            sensorWidthMm = 4.64f,
            sensorHeightMm = 3.48f,
            pixelPitchMicrons = 0.64f,
            maxResolution = "8160 × 6120",
            releaseYear = 2023,
            autofocusTech = "PDAF (All-Direction)",
            keyFeaturesRu = "Массовый 50-мегапиксельный сенсор SmartSens для бюджетных и среднебюджетных смартфонов 2024–2026 годов (Redmi, Realme, Transsion). SFCPixel и сверхнизкое энергопотребление",
            typicalPhones = "Redmi 13, Redmi 14C, Redmi A3/A4 series, Realme C-серия, Tecno Spark"
        ),
        SensorCatalogEntry(
            vendor = "SmartSens",
            modelName = "SC520CS",
            megapixels = 50.0,
            opticalFormat = "1/2.76\"",
            sensorWidthMm = 4.64f,
            sensorHeightMm = 3.48f,
            pixelPitchMicrons = 0.64f,
            maxResolution = "8160 × 6120",
            releaseYear = 2024,
            autofocusTech = "Double PD PDAF",
            keyFeaturesRu = "Ультратонкий 50 Мп сенсор второго поколения SmartSens с улучшенным динамическим диапазоном в контровом свете",
            typicalPhones = "Смартфоны Xiaomi Redmi, Honor X-серия, Infinix Hot"
        ),
        SensorCatalogEntry(
            vendor = "SmartSens",
            modelName = "SC202CS",
            megapixels = 2.0,
            opticalFormat = "1/5.0\"",
            sensorWidthMm = 2.40f,
            sensorHeightMm = 1.80f,
            pixelPitchMicrons = 1.75f,
            maxResolution = "1600 × 1200",
            releaseYear = 2023,
            autofocusTech = "Fixed Focus",
            keyFeaturesRu = "Сенсор макро и вспомогательной камеры SmartSens со сверхнизким уровнем шума при слабом освещении",
            typicalPhones = "Множество моделей Redmi, POCO, Realme 2024–2026 годов"
        ),
        SensorCatalogEntry(
            vendor = "SmartSens",
            modelName = "SC800CS",
            megapixels = 8.0,
            opticalFormat = "1/4.0\"",
            sensorWidthMm = 3.20f,
            sensorHeightMm = 2.40f,
            pixelPitchMicrons = 1.12f,
            maxResolution = "3264 × 2448",
            releaseYear = 2023,
            autofocusTech = "Fixed Focus / Contrast AF",
            keyFeaturesRu = "8-мегапиксельный сенсор для сверхширокоугольных и фронтальных камер от SmartSens",
            typicalPhones = "Redmi, Honor, Realme, Vivo Y-серия"
        ),

        // GalaxyCore
        SensorCatalogEntry(
            vendor = "GalaxyCore",
            modelName = "GC50E0",
            megapixels = 50.0,
            opticalFormat = "1/2.88\"",
            sensorWidthMm = 4.40f,
            sensorHeightMm = 3.30f,
            pixelPitchMicrons = 0.61f,
            maxResolution = "8192 × 6144",
            releaseYear = 2024,
            autofocusTech = "PDAF",
            keyFeaturesRu = "Современный 50-мегапиксельный сенсор GalaxyCore начального уровня с объединением 4-в-1 в 12.5 Мп",
            typicalPhones = "Redmi A3 Pro, Redmi 14C, бюджетные модели Transsion"
        ),
        SensorCatalogEntry(
            vendor = "GalaxyCore",
            modelName = "GC08A3",
            megapixels = 8.0,
            opticalFormat = "1/4.0\"",
            sensorWidthMm = 3.20f,
            sensorHeightMm = 2.40f,
            pixelPitchMicrons = 1.12f,
            maxResolution = "3264 × 2448",
            releaseYear = 2021,
            autofocusTech = "PDAF / Contrast",
            keyFeaturesRu = "Популярный 8 Мп сенсор для фронтальных и ультрашироких камер",
            typicalPhones = "Бюджетные смартфоны Xiaomi Redmi, Transsion, Realme"
        ),
        SensorCatalogEntry(
            vendor = "GalaxyCore",
            modelName = "GC02M1",
            megapixels = 2.0,
            opticalFormat = "1/5.0\"",
            sensorWidthMm = 2.40f,
            sensorHeightMm = 1.80f,
            pixelPitchMicrons = 1.75f,
            maxResolution = "1600 × 1200",
            releaseYear = 2018,
            autofocusTech = "Fixed Focus",
            keyFeaturesRu = "Самый массовый сенсор для 2-мегапиксельных камер макро или датчиков размытия",
            typicalPhones = "Xiaomi Redmi 9/10/11/12/13/14, Realme, Infinix, Tecno"
        ),

        // Sony Semiconductor (Exmor RS & LYTIA)
        SensorCatalogEntry(
            vendor = "Sony",
            modelName = "LYT-900",
            megapixels = 50.3,
            opticalFormat = "1.0\" (1/0.98\")",
            sensorWidthMm = 13.06f,
            sensorHeightMm = 9.8f,
            pixelPitchMicrons = 1.60f,
            maxResolution = "8192 × 6144",
            releaseYear = 2024,
            autofocusTech = "All-pixel Omni-directional PDAF (Octa PD)",
            keyFeaturesRu = "Флагманский 1-дюймовый сенсор второго поколения, 22-нм техпроцесс, 2-слойные транзисторные пиксели",
            typicalPhones = "Xiaomi 14 Ultra, Oppo Find X7 Ultra, Vivo X100 Ultra"
        ),
        SensorCatalogEntry(
            vendor = "Sony",
            modelName = "LYT-808 / LYT-700",
            megapixels = 50.0,
            opticalFormat = "1/1.43\"",
            sensorWidthMm = 8.96f,
            sensorHeightMm = 6.72f,
            pixelPitchMicrons = 1.12f,
            maxResolution = "8192 × 6144",
            releaseYear = 2023,
            autofocusTech = "Dual Pixel Pro PDAF",
            keyFeaturesRu = "Двухслойный транзисторный пиксель с удвоенной емкостью насыщения",
            typicalPhones = "OnePlus 12, Realme GT5 Pro, OnePlus Open, Realme 13 Pro+"
        ),
        SensorCatalogEntry(
            vendor = "Sony",
            modelName = "IMX882 / LYT-600",
            megapixels = 50.0,
            opticalFormat = "1/1.95\"",
            sensorWidthMm = 6.56f,
            sensorHeightMm = 4.92f,
            pixelPitchMicrons = 0.80f,
            maxResolution = "8192 × 6144",
            releaseYear = 2024,
            autofocusTech = "All-pixel Omni-directional PDAF",
            keyFeaturesRu = "Популярный сенсор для Redmi Note 14, Realme 12/13, OnePlus Nord",
            typicalPhones = "Redmi Note 14 5G / Pro, Realme 12 Pro+, Vivo V30, Poco F6"
        ),
        SensorCatalogEntry(
            vendor = "Sony",
            modelName = "IMX890",
            megapixels = 50.3,
            opticalFormat = "1/1.56\"",
            sensorWidthMm = 8.20f,
            sensorHeightMm = 6.15f,
            pixelPitchMicrons = 1.00f,
            maxResolution = "8192 × 6144",
            releaseYear = 2022,
            autofocusTech = "All-pixel Omni-directional PDAF",
            keyFeaturesRu = "Преемник IMX766 с поддержкой 4K 60fps и DOL-HDR",
            typicalPhones = "OnePlus 11, Realme GT3, Nothing Phone (2)"
        ),
        SensorCatalogEntry(
            vendor = "Sony",
            modelName = "IMX355",
            megapixels = 8.0,
            opticalFormat = "1/4.0\"",
            sensorWidthMm = 3.20f,
            sensorHeightMm = 2.40f,
            pixelPitchMicrons = 1.12f,
            maxResolution = "3264 × 2448",
            releaseYear = 2018,
            autofocusTech = "Fixed Focus",
            keyFeaturesRu = "Классический 8 Мп сенсор ультраширокой камеры в Redmi Note 10/11/12/13/14",
            typicalPhones = "Redmi Note series, Poco F3/F4/X3/X5, Pixel 3"
        ),

        // Samsung System LSI (ISOCELL)
        SensorCatalogEntry(
            vendor = "Samsung",
            modelName = "ISOCELL HP3",
            megapixels = 200.0,
            opticalFormat = "1/1.4\"",
            sensorWidthMm = 9.14f,
            sensorHeightMm = 6.85f,
            pixelPitchMicrons = 0.56f,
            maxResolution = "16320 × 12240",
            releaseYear = 2022,
            autofocusTech = "Super QPD",
            keyFeaturesRu = "200 Мп сенсор в серии Redmi Note Pro+",
            typicalPhones = "Redmi Note 13 Pro+, Redmi Note 13 Pro 5G, Realme 11 Pro+"
        ),
        SensorCatalogEntry(
            vendor = "Samsung",
            modelName = "ISOCELL HM6",
            megapixels = 108.0,
            opticalFormat = "1/1.67\"",
            sensorWidthMm = 7.68f,
            sensorHeightMm = 5.76f,
            pixelPitchMicrons = 0.64f,
            maxResolution = "12000 × 9000",
            releaseYear = 2022,
            autofocusTech = "Super PD",
            keyFeaturesRu = "108 Мп Nonapixel сенсор с биннингом 9-в-1",
            typicalPhones = "Redmi Note 13 4G/5G, Realme 9, Poco X5 Pro"
        ),
        SensorCatalogEntry(
            vendor = "Samsung",
            modelName = "ISOCELL JN1",
            megapixels = 50.0,
            opticalFormat = "1/2.76\"",
            sensorWidthMm = 4.64f,
            sensorHeightMm = 3.48f,
            pixelPitchMicrons = 0.64f,
            maxResolution = "8160 × 6120",
            releaseYear = 2021,
            autofocusTech = "Double Super PD",
            keyFeaturesRu = "Один из самых массовых 50 Мп сенсоров для доступных смартфонов и селфи-камер",
            typicalPhones = "Redmi 12/13, Redmi Note 11/12, Galaxy A15/A25, Motorola G"
        ),

        // OmniVision Technologies
        SensorCatalogEntry(
            vendor = "OmniVision",
            modelName = "OV50H / Light Hunter 900",
            megapixels = 50.0,
            opticalFormat = "1/1.3\"",
            sensorWidthMm = 9.84f,
            sensorHeightMm = 7.38f,
            pixelPitchMicrons = 1.20f,
            maxResolution = "8192 × 6144",
            releaseYear = 2023,
            autofocusTech = "QPD (Quad Phase Detection)",
            keyFeaturesRu = "Флагманский сенсор PureCel Plus-S с Dual Conversion Gain",
            typicalPhones = "Xiaomi 14 / 14 Pro (Light Hunter 900), Honor Magic 6 Pro, iQOO 12"
        ),
        SensorCatalogEntry(
            vendor = "OmniVision",
            modelName = "OV50D",
            megapixels = 50.0,
            opticalFormat = "1/2.88\"",
            sensorWidthMm = 4.40f,
            sensorHeightMm = 3.30f,
            pixelPitchMicrons = 0.61f,
            maxResolution = "8192 × 6144",
            releaseYear = 2023,
            autofocusTech = "PDAF",
            keyFeaturesRu = "50-Мп сенсор OmniVision для доступных смартфонов Redmi и Realme",
            typicalPhones = "Redmi серии A и C, Realme C-серия"
        ),
        SensorCatalogEntry(
            vendor = "OmniVision",
            modelName = "OV64B",
            megapixels = 64.0,
            opticalFormat = "1/2.0\"",
            sensorWidthMm = 6.40f,
            sensorHeightMm = 4.80f,
            pixelPitchMicrons = 0.70f,
            maxResolution = "9248 × 6936",
            releaseYear = 2020,
            autofocusTech = "2x2 Microlens PDAF",
            keyFeaturesRu = "Мировой эталон для перископических зум-объективов",
            typicalPhones = "OnePlus 12, Realme GT5 Pro, Oppo Find X7 Ultra, Poco F6"
        ),
        SensorCatalogEntry(
            vendor = "OmniVision",
            modelName = "OV02B10",
            megapixels = 2.0,
            opticalFormat = "1/5.0\"",
            sensorWidthMm = 2.40f,
            sensorHeightMm = 1.80f,
            pixelPitchMicrons = 1.75f,
            maxResolution = "1600 × 1200",
            releaseYear = 2019,
            autofocusTech = "Fixed Focus",
            keyFeaturesRu = "Специализированный 2-мегапиксельный сенсор для макросъемки",
            typicalPhones = "Redmi Note 12/13, множество бюджетных моделей"
        )
    )

    fun matchSensor(
        megapixels: Double,
        widthMm: Float,
        heightMm: Float,
        pixelArrayW: Int,
        pixelArrayH: Int,
        isFront: Boolean,
        focalLengthMm: Float?,
        manufacturer: String,
        model: String,
        detectedHardwareDriverModels: List<String> = emptyList(),
        detectionSourceText: String? = null
    ): SensorVendorGuess {
        val diagonal = sqrt(widthMm * widthMm + heightMm * heightMm)
        val lowerModel = model.lowercase()
        val lowerMfr = manufacturer.lowercase()

        // 1. Direct Kernel / Hardware Driver match! (Highest accuracy)
        if (detectedHardwareDriverModels.isNotEmpty()) {
            val primaryDetected = detectedHardwareDriverModels.first()
            val matchedInCatalog = CATALOG.firstOrNull {
                it.modelName.contains(primaryDetected, ignoreCase = true) ||
                primaryDetected.contains(it.modelName.substringBefore(" "), ignoreCase = true)
            }

            val vendor = when {
                primaryDetected.startsWith("SC", ignoreCase = true) -> "SmartSens Technology 🇨🇳"
                primaryDetected.startsWith("GC", ignoreCase = true) -> "GalaxyCore 🇨🇳"
                primaryDetected.startsWith("OV", ignoreCase = true) -> "OmniVision Technologies 🇺🇸🇨🇳"
                primaryDetected.startsWith("IMX", ignoreCase = true) || primaryDetected.startsWith("LYT", ignoreCase = true) -> "Sony Semiconductor 🇯🇵"
                primaryDetected.startsWith("S5K", ignoreCase = true) || primaryDetected.startsWith("HP", ignoreCase = true) -> "Samsung System LSI 🇰🇷"
                matchedInCatalog != null -> matchedInCatalog.vendor
                else -> "Hardware HAL Driver"
            }

            return SensorVendorGuess(
                vendorName = vendor,
                probableModels = listOf(primaryDetected),
                confidence = "100% Аппаратно определено (Драйвер ядра / HAL)",
                details = "Драйвер камеры устройства подтверждает физический сенсор: $primaryDetected (${detectionSourceText ?: "Kernel Probe"})."
            )
        }

        // 2. Specific Model Hardware Mapping for 2024–2026 devices
        // (Redmi A-series, Redmi C-series, Redmi Note series, etc.)
        val isRedmiBudget = (lowerModel.contains("redmi") || lowerModel.contains("poco")) &&
                (lowerModel.contains(" a") || lowerModel.contains(" c") || lowerModel.matches(Regex(".*\\b(a[1-9]|c[1-9]|1[2-5]c)\\b.*")))

        if (isRedmiBudget) {
            if (!isFront) {
                if (megapixels in 48.0..52.0) {
                    return SensorVendorGuess(
                        vendorName = "SmartSens / GalaxyCore / Samsung",
                        probableModels = listOf("SmartSens SC500CS", "GalaxyCore GC50E0", "Samsung ISOCELL JN1"),
                        confidence = "Высокая (База моделей Redmi начального уровня)",
                        details = "В бюджетной серии Redmi (A / C) для основной 50 Мп камеры Xiaomi использует сенсоры SmartSens SC500CS, GalaxyCore GC50E0 или Samsung JN1 (матрица 1/2.76\", 0.64 мкм)."
                    )
                } else if (megapixels in 1.8..2.5) {
                    return SensorVendorGuess(
                        vendorName = "GalaxyCore / SmartSens",
                        probableModels = listOf("GalaxyCore GC02M1", "SmartSens SC202CS"),
                        confidence = "Высокая (Вспомогательный модуль)",
                        details = "Вспомогательный 2-Мп датчик глубины/макро на базе GalaxyCore GC02M1 или SmartSens SC202CS."
                    )
                }
            } else {
                // Front selfie
                return SensorVendorGuess(
                    vendorName = "GalaxyCore / OmniVision",
                    probableModels = listOf("GalaxyCore GC08A3", "OmniVision OV08D"),
                    confidence = "Высокая (Фронтальная камера)",
                    details = "Селфи-камера на базе GalaxyCore GC08A3 или OmniVision OV08D."
                )
            }
        }

        // Redmi Note 13 / 14 Series detection
        if (lowerModel.contains("note 14") || lowerModel.contains("note 13")) {
            if (!isFront) {
                if (megapixels in 190.0..210.0) {
                    return SensorVendorGuess(
                        vendorName = "Samsung System LSI 🇰🇷",
                        probableModels = listOf("Samsung ISOCELL HP3 (200 Мп)"),
                        confidence = "Точное совпадение (Redmi Note Pro+)",
                        details = "Флагманский 200-Мп сенсор Samsung ISOCELL HP3 с оптической стабилизацией OIS."
                    )
                } else if (megapixels in 100.0..115.0) {
                    return SensorVendorGuess(
                        vendorName = "Samsung System LSI 🇰🇷",
                        probableModels = listOf("Samsung ISOCELL HM6 (108 Мп)"),
                        confidence = "Точное совпадение (Redmi Note 13)",
                        details = "108-Мп матрица Samsung ISOCELL HM6 с объединением пикселей 9-в-1."
                    )
                } else if (megapixels in 48.0..52.0 && diagonal in 7.0f..8.5f) {
                    return SensorVendorGuess(
                        vendorName = "Sony Semiconductor 🇯🇵 / OmniVision",
                        probableModels = listOf("Sony LYT-600 (IMX882)", "OmniVision Light Hunter 800"),
                        confidence = "Высокая (Redmi Note 14 5G/Pro)",
                        details = "50-Мп сенсор Sony LYT-600 (1/1.95\") с оптической стабилизацией OIS."
                    )
                } else if (megapixels in 48.0..52.0 && diagonal < 6.0f) {
                    return SensorVendorGuess(
                        vendorName = "SmartSens / Samsung",
                        probableModels = listOf("SmartSens SC500CS", "Samsung ISOCELL JN1"),
                        confidence = "Высокая (Сенсор 1/2.76\")",
                        details = "50-Мп компактная матрица SmartSens SC500CS или Samsung ISOCELL JN1."
                    )
                }
            }
        }

        // 3. Fallback to Catalog match by optical physical dimensions
        val candidates = CATALOG.map { entry ->
            val entryDiag = sqrt(entry.sensorWidthMm * entry.sensorWidthMm + entry.sensorHeightMm * entry.sensorHeightMm)
            val mpDiff = abs(entry.megapixels - megapixels)
            val diagDiff = abs(entryDiag - diagonal)
            val score = (mpDiff * 2.0) + (diagDiff * 5.0)
            Pair(entry, score)
        }.sortedBy { it.second }

        val best = candidates.firstOrNull()
        if (best != null && best.second < 2.0) {
            val vendor = best.first.vendor
            val matchedNames = candidates.filter { it.second < 2.5 }.map { it.first.modelName }.distinct().take(3)
            return SensorVendorGuess(
                vendorName = "$vendor (${best.first.opticalFormat})",
                probableModels = matchedNames,
                confidence = "Аппаратное совпадение физических параметров",
                details = "Физический размер матрицы: ${"%.2f".format(widthMm)} × ${"%.2f".format(heightMm)} мм, диагональ: ${"%.2f".format(diagonal)} мм (${best.first.opticalFormat}), ${best.first.pixelPitchMicrons} мкм."
            )
        }

        val topModels = candidates.take(3).map { "${it.first.vendor} ${it.first.modelName}" }
        return SensorVendorGuess(
            vendorName = "SmartSens / GalaxyCore / OmniVision / Sony",
            probableModels = topModels,
            confidence = "Физический анализ сенсора",
            details = "Определено по физическому замеру оптики: ${"%.2f".format(widthMm)} × ${"%.2f".format(heightMm)} мм (~${"%.1f".format(megapixels)} Мп)."
        )
    }
}
