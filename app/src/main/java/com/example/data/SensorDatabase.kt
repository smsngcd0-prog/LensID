package com.example.data

import com.example.model.SensorCatalogEntry
import com.example.model.SensorVendorGuess
import kotlin.math.abs
import kotlin.math.sqrt

object SensorDatabase {

    val CATALOG: List<SensorCatalogEntry> = listOf(
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
            keyFeaturesRu = "Флагманский 1-дюймовый сенсор второго поколения, 22-нм техпроцесс, технология 2-слойных транзисторных пикселей, экстремальный динамический диапазон",
            typicalPhones = "Xiaomi 14 Ultra, Oppo Find X7 Ultra, Vivo X100 Ultra"
        ),
        SensorCatalogEntry(
            vendor = "Sony",
            modelName = "IMX989",
            megapixels = 50.3,
            opticalFormat = "1.0\" (1/0.98\")",
            sensorWidthMm = 13.06f,
            sensorHeightMm = 9.8f,
            pixelPitchMicrons = 1.60f,
            maxResolution = "8192 × 6144",
            releaseYear = 2022,
            autofocusTech = "Octa-PD PDAF",
            keyFeaturesRu = "Первый массовый 1-дюймовый сенсор для смартфонов, разработанный Sony в партнёрстве с Xiaomi. Quad Bayer, пиксели 3.2 мкм при биннинге 4-в-1",
            typicalPhones = "Xiaomi 13 Ultra, Xiaomi 12S Ultra, Vivo X90 Pro+"
        ),
        SensorCatalogEntry(
            vendor = "Sony",
            modelName = "LYT-808",
            megapixels = 50.0,
            opticalFormat = "1/1.43\"",
            sensorWidthMm = 8.96f,
            sensorHeightMm = 6.72f,
            pixelPitchMicrons = 1.12f,
            maxResolution = "8192 × 6144",
            releaseYear = 2023,
            autofocusTech = "Dual Pixel Pro PDAF",
            keyFeaturesRu = "Двухслойный транзисторный пиксель (Dual-Layer Transistor Pixel), емкость насыщения увеличена в 2 раза по сравнению с обычными сенсорами",
            typicalPhones = "OnePlus 12, Realme GT5 Pro, OnePlus Open"
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
            keyFeaturesRu = "Прямой преемник легендарного IMX766. Поддерживает запись 4K 60fps со всенаправленным автофокусом и кадровым DOL-HDR",
            typicalPhones = "OnePlus 11, Realme GT3, Oppo Find N2, Nothing Phone (2)"
        ),
        SensorCatalogEntry(
            vendor = "Sony",
            modelName = "IMX766",
            megapixels = 50.3,
            opticalFormat = "1/1.56\"",
            sensorWidthMm = 8.19f,
            sensorHeightMm = 6.14f,
            pixelPitchMicrons = 1.00f,
            maxResolution = "8192 × 6144",
            releaseYear = 2020,
            autofocusTech = "All-pixel Omni-directional PDAF",
            keyFeaturesRu = "Самый популярный сенсор субфлагманов 2021-2023 годов. Баланс светосилы, динамического диапазона и скорости считывания",
            typicalPhones = "Xiaomi 12, Asus Zenfone 9, Realme GT Neo 3, Huawei P50 Pro"
        ),
        SensorCatalogEntry(
            vendor = "Sony",
            modelName = "IMX858",
            megapixels = 50.0,
            opticalFormat = "1/2.51\"",
            sensorWidthMm = 5.76f,
            sensorHeightMm = 4.32f,
            pixelPitchMicrons = 0.70f,
            maxResolution = "8192 × 6144",
            releaseYear = 2023,
            autofocusTech = "All-pixel Omni-directional PDAF",
            keyFeaturesRu = "Специализированный сенсор для телеобъективов и ультраширокоугольных камер с поддержкой MCSS (Multi-Camera Sync System)",
            typicalPhones = "Xiaomi 13 Ultra (зум 3.2x, 5x и ультраширик), Xiaomi 14 Ultra"
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
            keyFeaturesRu = "Современный энергоэффективный сенсор Sony LYTIA с внутрисенсорным 2x/4x зумом без потери качества",
            typicalPhones = "Realme 12 Pro+, Vivo V30, OnePlus Nord CE4"
        ),
        SensorCatalogEntry(
            vendor = "Sony",
            modelName = "IMX586",
            megapixels = 48.0,
            opticalFormat = "1/2.0\"",
            sensorWidthMm = 6.40f,
            sensorHeightMm = 4.80f,
            pixelPitchMicrons = 0.80f,
            maxResolution = "8000 × 6000",
            releaseYear = 2018,
            autofocusTech = "PDAF (Phase Detection Auto Focus)",
            keyFeaturesRu = "Сенсор, начавший революцию Quad Bayer 48 Мп. Биннинг в 12 Мп с размером пикселя 1.6 мкм",
            typicalPhones = "OnePlus 7 Pro, Xiaomi Mi 9, Galaxy A90"
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
            autofocusTech = "Fixed Focus / Contrast AF",
            keyFeaturesRu = "Самый массовый ультраширокоугольный 8-мегапиксельный сенсор в смартфонах среднего и базового уровня",
            typicalPhones = "Poco F3, Poco X3 Pro, Pixel 3 (фронталка), Realme GT"
        ),
        SensorCatalogEntry(
            vendor = "Sony",
            modelName = "IMX363",
            megapixels = 12.2,
            opticalFormat = "1/2.55\"",
            sensorWidthMm = 5.64f,
            sensorHeightMm = 4.23f,
            pixelPitchMicrons = 1.40f,
            maxResolution = "4032 × 3024",
            releaseYear = 2018,
            autofocusTech = "Dual Pixel PDAF",
            keyFeaturesRu = "Культовый сенсор линейки Google Pixel (от Pixel 3 до Pixel 6a). Непревзойденный фазовый фокус Dual Pixel",
            typicalPhones = "Google Pixel 3, 4, 5, 5a, 6a, Xiaomi Mi 8, Asus ROG Phone"
        ),

        // Samsung System LSI (ISOCELL)
        SensorCatalogEntry(
            vendor = "Samsung",
            modelName = "ISOCELL HP2",
            megapixels = 200.0,
            opticalFormat = "1/1.3\"",
            sensorWidthMm = 9.84f,
            sensorHeightMm = 7.38f,
            pixelPitchMicrons = 0.60f,
            maxResolution = "16320 × 12240",
            releaseYear = 2023,
            autofocusTech = "Super QPD (Quad Phase Detection)",
            keyFeaturesRu = "Флагманский 200 Мп сенсор. Технология биннинга Tetra²pixel (16-в-1 до 12.5 Мп с пикселями 2.4 мкм), D-VTG затворы и Dual Slope Gain",
            typicalPhones = "Samsung Galaxy S23 Ultra, Galaxy S24 Ultra"
        ),
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
            keyFeaturesRu = "Компактный 200 Мп сенсор для субфлагманов и смартфонов среднего класса. Доступный 4x кроп-зум без потери резкости",
            typicalPhones = "Realme 11 Pro+, Redmi Note 13 Pro+, Honor 90"
        ),
        SensorCatalogEntry(
            vendor = "Samsung",
            modelName = "ISOCELL GN2",
            megapixels = 50.0,
            opticalFormat = "1/1.12\"",
            sensorWidthMm = 11.43f,
            sensorHeightMm = 8.57f,
            pixelPitchMicrons = 1.40f,
            maxResolution = "8160 × 6120",
            releaseYear = 2021,
            autofocusTech = "Dual Pixel Pro PDAF",
            keyFeaturesRu = "Один из крупнейших сенсоров Samsung. Натуральный размер пикселя 1.4 мкм (2.8 мкм в биннинге), Smart-ISO Pro и Staggered HDR",
            typicalPhones = "Xiaomi 11 Ultra, Google Pixel 8 Pro (модификация)"
        ),
        SensorCatalogEntry(
            vendor = "Samsung",
            modelName = "ISOCELL GNV / GN5",
            megapixels = 50.0,
            opticalFormat = "1/1.57\"",
            sensorWidthMm = 8.16f,
            sensorHeightMm = 6.12f,
            pixelPitchMicrons = 1.00f,
            maxResolution = "8160 × 6120",
            releaseYear = 2022,
            autofocusTech = "Dual Pixel Pro PDAF",
            keyFeaturesRu = "Быстрый всенаправленный автофокус Dual Pixel Pro с диагональным считыванием фазовых разностей",
            typicalPhones = "Samsung Galaxy S22 / S23 / S24, Vivo X80 Pro"
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
            keyFeaturesRu = "Сенсор Nonapixel 108 Мп с технологией объединения 9 пикселей в 1 (1.92 мкм) и улучшенной технологией Smart ISO",
            typicalPhones = "Realme 9, Xiaomi 12T, Poco X5 Pro"
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
            keyFeaturesRu = "Самый популярный 50-Мп сенсор для ультраширокоугольных и селфи-камер благодаря ультратонкому профилю",
            typicalPhones = "Xiaomi 12 Pro (UW/Tele), OnePlus 10 Pro (UW), Poco X4 GT, Motorola Edge"
        ),
        SensorCatalogEntry(
            vendor = "Samsung",
            modelName = "ISOCELL 3L6",
            megapixels = 13.0,
            opticalFormat = "1/3.1\"",
            sensorWidthMm = 4.10f,
            sensorHeightMm = 3.08f,
            pixelPitchMicrons = 1.12f,
            maxResolution = "4160 × 3120",
            releaseYear = 2019,
            autofocusTech = "PDAF",
            keyFeaturesRu = "Стандартный сенсор для сверхширокоугольных и дополнительных камер",
            typicalPhones = "Xiaomi Redmi Note 10 Pro, Samsung Galaxy M/A series"
        ),

        // OmniVision Technologies
        SensorCatalogEntry(
            vendor = "OmniVision",
            modelName = "OV50H",
            megapixels = 50.0,
            opticalFormat = "1/1.3\"",
            sensorWidthMm = 9.84f,
            sensorHeightMm = 7.38f,
            pixelPitchMicrons = 1.20f,
            maxResolution = "8192 × 6144",
            releaseYear = 2023,
            autofocusTech = "QPD (Quad Phase Detection)",
            keyFeaturesRu = "Флагманский сенсор с технологией PureCel Plus-S и Dual Conversion Gain. Прямой конкурент Sony LYT-808 и IMX989",
            typicalPhones = "Xiaomi 14 / 14 Pro (Light Fusion 900), Honor Magic 6 Pro, iQOO 12"
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
            autofocusTech = "Type-2 2x2 Microlens PDAF",
            keyFeaturesRu = "Самый востребованный в мире сенсор для перископических зум-объективов благодаря тонкому корпусу и высокой детализации",
            typicalPhones = "OnePlus 12 (зум 3x), Oppo Find X7 Ultra, Realme GT5 Pro, Poco F6"
        ),
        SensorCatalogEntry(
            vendor = "OmniVision",
            modelName = "OV08D10 / OV08A10",
            megapixels = 8.0,
            opticalFormat = "1/4.0\"",
            sensorWidthMm = 3.20f,
            sensorHeightMm = 2.40f,
            pixelPitchMicrons = 1.12f,
            maxResolution = "3264 × 2448",
            releaseYear = 2020,
            autofocusTech = "Fixed Focus",
            keyFeaturesRu = "Широко распространённый ультраширокоугольный сенсор начального и среднего уровня",
            typicalPhones = "Redmi Note series, Realme number series, Motorola G"
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
            keyFeaturesRu = "Специализированный вспомогательный сенсор для макросъемки или замера глубины резкости",
            typicalPhones = "Множество бюджетных и среднебюджетных смартфонов"
        ),

        // GalaxyCore
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
            keyFeaturesRu = "Популярный сенсор для 2-мегапиксельных камер макро или датчиков глубины портрета",
            typicalPhones = "Xiaomi Redmi 9/10/11, Realme, Infinix, Tecno"
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
            keyFeaturesRu = "Недорогой 8 Мп сенсор для фронтальных или ультрашироких модулей",
            typicalPhones = "Бюджетные смартфоны Transsion, Xiaomi Redmi"
        ),

        // SK Hynix
        SensorCatalogEntry(
            vendor = "SK Hynix",
            modelName = "Hi-846",
            megapixels = 8.0,
            opticalFormat = "1/4.0\"",
            sensorWidthMm = 3.20f,
            sensorHeightMm = 2.40f,
            pixelPitchMicrons = 1.12f,
            maxResolution = "3264 × 2448",
            releaseYear = 2019,
            autofocusTech = "Fixed Focus",
            keyFeaturesRu = "8-мегапиксельный сенсор от южнокорейского гиганта полупроводников SK Hynix",
            typicalPhones = "Samsung Galaxy A-серия, Vivo, Oppo"
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
        manufacturer: String
    ): SensorVendorGuess {
        val diagonal = sqrt(widthMm * widthMm + heightMm * heightMm)

        // Find closest candidates by MP and Diagonal
        val candidates = CATALOG.map { entry ->
            val entryDiag = sqrt(entry.sensorWidthMm * entry.sensorWidthMm + entry.sensorHeightMm * entry.sensorHeightMm)
            val mpDiff = abs(entry.megapixels - megapixels)
            val diagDiff = abs(entryDiag - diagonal)
            
            // Weight difference score (lower is better)
            val score = (mpDiff * 2.0) + (diagDiff * 5.0)
            Pair(entry, score)
        }.sortedBy { it.second }

        val best = candidates.firstOrNull()

        // Check if confidence is high
        if (best != null && best.second < 1.5) {
            val vendor = best.first.vendor
            val matchedNames = candidates.filter { it.second < 2.0 }.map { it.first.modelName }.distinct().take(3)
            return SensorVendorGuess(
                vendorName = vendor,
                probableModels = matchedNames,
                confidence = "Высокая (совпадение оптических габаритов и Мп)",
                details = "Сенсор ${best.first.modelName} (${best.first.opticalFormat}, ${best.first.pixelPitchMicrons} мкм). ${best.first.keyFeaturesRu}"
            )
        }

        // Secondary heuristics by Megapixels and Size
        val vendorHint = when {
            manufacturer.contains("Samsung", ignoreCase = true) && megapixels in 100.0..210.0 -> "Samsung (ISOCELL)"
            manufacturer.contains("Google", ignoreCase = true) && megapixels in 48.0..52.0 -> "Samsung (ISOCELL GN) / Sony (LYT)"
            manufacturer.contains("Xiaomi", ignoreCase = true) && megapixels in 48.0..52.0 && diagonal > 9.0f -> "OmniVision (OV50H) / Sony (LYT/IMX)"
            megapixels in 190.0..210.0 -> "Samsung (ISOCELL HP серия)"
            megapixels in 100.0..115.0 -> "Samsung (ISOCELL HM серия)"
            megapixels in 62.0..66.0 && diagonal < 8.5f -> "OmniVision (OV64B) / Samsung (GW)"
            megapixels in 48.0..52.0 && diagonal in 7.8f..8.6f -> "Sony (IMX766/IMX890) / Samsung (GN5)"
            megapixels in 48.0..52.0 && diagonal < 6.0f -> "Samsung (ISOCELL JN1)"
            megapixels in 11.5..13.0 && diagonal in 6.0f..7.5f -> "Sony (IMX363/IMX374) / Samsung (2L4)"
            megapixels in 7.5..8.5 -> "Sony (IMX355) / OmniVision (OV08D) / SK Hynix (Hi-846)"
            megapixels in 1.8..2.5 -> "OmniVision (OV02B) / GalaxyCore (GC02M1)"
            else -> "Sony / Samsung / OmniVision"
        }

        val topModels = candidates.take(3).map { "${it.first.vendor} ${it.first.modelName}" }

        return SensorVendorGuess(
            vendorName = vendorHint,
            probableModels = topModels,
            confidence = "Аппаратная оценка характеристик",
            details = "Определено по физическому размеру сенсора ${"%.2f".format(widthMm)} × ${"%.2f".format(heightMm)} мм (диагональ ${"%.2f".format(diagonal)} мм, ~${"%.1f".format(megapixels)} Мп)."
        )
    }
}
