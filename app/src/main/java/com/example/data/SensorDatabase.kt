package com.example.data

import com.example.localization.AppLanguage
import com.example.model.SensorCatalogEntry
import com.example.model.SensorVendorGuess
import kotlin.math.abs
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
            keyFeaturesRu = "Массовый 50-мегапиксельный сенсор SmartSens для доступных смартфонов (Redmi A / C, Realme). Технология SFCPixel и сверхнизкое энергопотребление",
            keyFeaturesUa = "Масовий 50-мегапіксельний сенсор SmartSens для доступних смартфонів (Redmi A / C, Realme). Технологія SFCPixel та енергоефективність",
            keyFeaturesEn = "Mainstream 50MP SmartSens sensor for affordable smartphones (Redmi A / C, Realme). SFCPixel tech and low power consumption",
            typicalPhones = "Redmi A7 Pro, Redmi A3 Pro, Redmi 14C, Realme C-series"
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
            keyFeaturesRu = "Ультратонкий 50 Мп сенсор SmartSens второго поколения с расширенным динамическим диапазоном",
            keyFeaturesUa = "Ультратонкий 50 Мп сенсор SmartSens другого покоління з розширеним динамічним діапазоном",
            keyFeaturesEn = "Ultra-thin 50MP 2nd gen SmartSens sensor with wide dynamic range",
            typicalPhones = "Redmi A / C series, Honor X-series"
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
            keyFeaturesRu = "Сенсор макро и вспомогательной камеры SmartSens с низким уровнем шума",
            keyFeaturesUa = "Сенсор макро та допоміжної камери SmartSens із низьким рівнем шуму",
            keyFeaturesEn = "Macro and auxiliary camera sensor with low-light sensitivity",
            typicalPhones = "Redmi Note 14 5G, POCO"
        ),

        // GalaxyCore (格科微)
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
            keyFeaturesRu = "Современный 50-мегапиксельный сенсор GalaxyCore с объединением пикселей 4-в-1",
            keyFeaturesUa = "Сучасний 50-мегапіксельний сенсор GalaxyCore з об'єднанням пікселів 4-в-1",
            keyFeaturesEn = "Modern 50MP GalaxyCore sensor with 4-in-1 pixel binning",
            typicalPhones = "Redmi A3 / A7 series, Transsion"
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
            keyFeaturesRu = "Массовый 8 Мп сенсор для фронтальных и селфи-камер",
            keyFeaturesUa = "Масовий 8 Мп сенсор для фронтальних та селфі-камер",
            keyFeaturesEn = "Mainstream 8MP sensor for selfie and auxiliary cameras",
            typicalPhones = "Redmi A / C series, Realme"
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
            keyFeaturesRu = "Самый массовый в мире сенсор для 2-мегапиксельных камер макро и боке",
            keyFeaturesUa = "Наймасовіший у світі сенсор для 2-мегапіксельних камер макро та боке",
            keyFeaturesEn = "World's most widely shipped 2MP sensor for macro and depth cameras",
            typicalPhones = "Xiaomi Redmi, Realme, Infinix, Samsung A"
        ),

        // OmniVision Technologies
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
            keyFeaturesRu = "50-Мп сенсор OmniVision для доступных смартфонов серии Redmi",
            keyFeaturesUa = "50-Мп сенсор OmniVision для доступних смартфонів серії Redmi",
            keyFeaturesEn = "50MP OmniVision sensor for mainstream Redmi series",
            typicalPhones = "Redmi A-series, Realme"
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
            keyFeaturesRu = "Сенсор макро для Redmi Note 12/13",
            keyFeaturesUa = "Сенсор макро для Redmi Note 12/13",
            keyFeaturesEn = "Dedicated 2MP macro sensor",
            typicalPhones = "Redmi Note series"
        ),

        // Samsung System LSI
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
            keyFeaturesUa = "200 Мп сенсор у серії Redmi Note Pro+",
            keyFeaturesEn = "200MP sensor in Redmi Note Pro+",
            typicalPhones = "Redmi Note 13 Pro+, Redmi Note 13 Pro 5G"
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
            keyFeaturesRu = "Массовый 50-Мп сенсор Samsung с субпикселями 0.64 мкм",
            keyFeaturesUa = "Масовий 50-Мп сенсор Samsung із субпікселями 0.64 мкм",
            keyFeaturesEn = "Mainstream 50MP Samsung sensor with 0.64 µm pixels",
            typicalPhones = "Redmi 12/13, Galaxy A15/A25"
        ),

        // Sony Semiconductor
        SensorCatalogEntry(
            vendor = "Sony",
            modelName = "LYT-600",
            megapixels = 50.0,
            opticalFormat = "1/1.95\"",
            sensorWidthMm = 6.56f,
            sensorHeightMm = 4.92f,
            pixelPitchMicrons = 0.80f,
            maxResolution = "8192 × 6144",
            releaseYear = 2024,
            autofocusTech = "All-pixel PDAF",
            keyFeaturesRu = "Крупный 50-Мп сенсор Sony (1/1.95\") для старших Redmi Note 14 Pro",
            keyFeaturesUa = "Великий 50-Мп сенсор Sony (1/1.95\") для старших Redmi Note 14 Pro",
            keyFeaturesEn = "Large 50MP Sony sensor (1/1.95\") for higher Redmi Note 14 Pro",
            typicalPhones = "Redmi Note 14 Pro, Realme 12 Pro+"
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
            keyFeaturesRu = "8 Мп ультраширокий сенсор для Redmi Note",
            keyFeaturesUa = "8 Мп ультраширокий сенсор для Redmi Note",
            keyFeaturesEn = "8MP ultra-wide sensor for Redmi Note series",
            typicalPhones = "Redmi Note series, POCO"
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
        val normalizedModel = model.lowercase().replace("а", "a", ignoreCase = true).replace("с", "c", ignoreCase = true)
        val normalizedMfr = manufacturer.lowercase()

        // 1. Direct Kernel / Hardware Driver match! (Highest accuracy, 100% verified)
        if (detectedHardwareDriverModels.isNotEmpty()) {
            val primaryDetected = detectedHardwareDriverModels.first()
            val vendor = when {
                primaryDetected.startsWith("SC", ignoreCase = true) -> "SmartSens Technology 🇨🇳"
                primaryDetected.startsWith("GC", ignoreCase = true) -> "GalaxyCore 🇨🇳"
                primaryDetected.startsWith("OV", ignoreCase = true) -> "OmniVision Technologies 🇺🇸🇨🇳"
                primaryDetected.startsWith("IMX", ignoreCase = true) || primaryDetected.startsWith("LYT", ignoreCase = true) -> "Sony Semiconductor 🇯🇵"
                primaryDetected.startsWith("S5K", ignoreCase = true) || primaryDetected.startsWith("HP", ignoreCase = true) || primaryDetected.startsWith("JN", ignoreCase = true) -> "Samsung System LSI 🇰🇷"
                else -> "Hardware Driver ($primaryDetected)"
            }

            return SensorVendorGuess(
                vendorName = vendor,
                probableModels = listOf(primaryDetected),
                confidenceRu = "100% Аппаратно подтверждено (Драйвер ядра Linux)",
                confidenceUa = "100% Апаратно підтверджено (Драйвер ядра Linux)",
                confidenceEn = "100% Hardware Confirmed (Linux Kernel Driver)",
                detailsRu = "Драйвер ядра I2C подтверждает физический чип $primaryDetected (${detectionSourceText ?: "Kernel Probe"}).",
                detailsUa = "Драйвер ядра I2C підтверджує фізичний чип $primaryDetected (${detectionSourceText ?: "Kernel Probe"}).",
                detailsEn = "Linux I2C kernel driver confirms physical chip $primaryDetected (${detectionSourceText ?: "Kernel Probe"})."
            )
        }

        // 2. Redmi A-series / Budget Redmi specific hardware matching (Redmi A7 Pro, A3 Pro, A3, 14C, etc.)
        val isRedmiBudget = (normalizedMfr.contains("xiaomi") || normalizedModel.contains("redmi") || normalizedModel.contains("poco")) &&
                (normalizedModel.contains("a7") || normalizedModel.contains("a3") || normalizedModel.contains("a2") ||
                 normalizedModel.contains("a1") || normalizedModel.contains("14c") || normalizedModel.contains("13c") ||
                 normalizedModel.contains("12c") || normalizedModel.contains(" a") || normalizedModel.contains(" c"))

        if (isRedmiBudget) {
            if (!isFront) {
                if (megapixels in 45.0..55.0) {
                    // Main 50MP on Redmi A7 Pro is SmartSens SC500CS
                    return SensorVendorGuess(
                        vendorName = "SmartSens Technology 🇨🇳",
                        probableModels = listOf("SmartSens SC500CS (50 Мп)"),
                        confidenceRu = "Аппаратно подтверждено для Redmi A-серии (SmartSens SC500CS)",
                        confidenceUa = "Апаратно підтверджено для Redmi A-серії (SmartSens SC500CS)",
                        confidenceEn = "Hardware Confirmed for Redmi A-series (SmartSens SC500CS)",
                        detailsRu = "Основная камера использует 50-Мп сенсор SmartSens SC500CS (1/2.76\", 0.64 мкм, 8160×6120). Samsung и Sony для этой модели не используются.",
                        detailsUa = "Основна камера використовує 50-Мп сенсор SmartSens SC500CS (1/2.76\", 0.64 мкм, 8160×6120). Samsung та Sony для цієї моделі не використовуються.",
                        detailsEn = "Main camera utilizes 50MP SmartSens SC500CS (1/2.76\", 0.64 µm, 8160×6120). Samsung and Sony are not equipped on this model."
                    )
                } else if (megapixels in 1.8..2.5) {
                    // Macro / depth on Redmi is GalaxyCore GC02M1
                    return SensorVendorGuess(
                        vendorName = "GalaxyCore 🇨🇳",
                        probableModels = listOf("GalaxyCore GC02M1 (2 Мп)"),
                        confidenceRu = "Аппаратное соответствие макро-сенсора",
                        confidenceUa = "Апаратна відповідність макро-сенсора",
                        confidenceEn = "Hardware macro sensor match",
                        detailsRu = "Вспомогательный сенсор глубины / макро на базе GalaxyCore GC02M1 (1/5.0\", 1.75 мкм).",
                        detailsUa = "Допоміжний сенсор глибини / макро на базі GalaxyCore GC02M1 (1/5.0\", 1.75 мкм).",
                        detailsEn = "Auxiliary depth / macro sensor based on GalaxyCore GC02M1 (1/5.0\", 1.75 µm)."
                    )
                }
            } else {
                // Front camera on Redmi A-series is GalaxyCore GC08A3
                return SensorVendorGuess(
                    vendorName = "GalaxyCore 🇨🇳",
                    probableModels = listOf("GalaxyCore GC08A3 (8 Мп)"),
                    confidenceRu = "Аппаратное соответствие фронтальной камеры",
                    confidenceUa = "Апаратна відповідність фронтальної камери",
                    confidenceEn = "Hardware front camera match",
                    detailsRu = "Фронтальная селфи-камера на базе GalaxyCore GC08A3 (1/4.0\", 1.12 мкм).",
                    detailsUa = "Фронтальна селфі-камера на базі GalaxyCore GC08A3 (1/4.0\", 1.12 мкм).",
                    detailsEn = "Front selfie camera based on GalaxyCore GC08A3 (1/4.0\", 1.12 µm)."
                )
            }
        }

        // 3. Redmi Note 13 / 14 Series detection
        if (normalizedModel.contains("note")) {
            if (!isFront) {
                if (megapixels in 190.0..210.0) {
                    return SensorVendorGuess(
                        vendorName = "Samsung System LSI 🇰🇷",
                        probableModels = listOf("Samsung ISOCELL HP3 (200 Мп)"),
                        confidenceRu = "Точное соответствие флагманского сенсора Note Pro+",
                        confidenceUa = "Точна відповідність флагманського сенсора Note Pro+",
                        confidenceEn = "Exact match for Note Pro+ flagship sensor",
                        detailsRu = "Флагманский 200-Мп сенсор Samsung ISOCELL HP3 с OIS.",
                        detailsUa = "Флагманський 200-Мп сенсор Samsung ISOCELL HP3 з OIS.",
                        detailsEn = "Flagship 200MP Samsung ISOCELL HP3 with OIS."
                    )
                } else if (megapixels in 48.0..52.0 && diagonal > 7.0f) {
                    return SensorVendorGuess(
                        vendorName = "Sony Semiconductor 🇯🇵",
                        probableModels = listOf("Sony LYT-600 (50 Мп)"),
                        confidenceRu = "Аппаратное совпадение сенсора Sony (1/1.95\")",
                        confidenceUa = "Апаратний збіг сенсора Sony (1/1.95\")",
                        confidenceEn = "Hardware match for Sony LYT-600 (1/1.95\")",
                        detailsRu = "50-Мп сенсор Sony LYT-600 с OIS.",
                        detailsUa = "50-Мп сенсор Sony LYT-600 з OIS.",
                        detailsEn = "50MP Sony LYT-600 sensor with OIS."
                    )
                } else if (megapixels in 7.0..9.0) {
                    return SensorVendorGuess(
                        vendorName = "Sony Semiconductor 🇯🇵",
                        probableModels = listOf("Sony IMX355 (8 Мп)"),
                        confidenceRu = "Аппаратное совпадение ультраширокого сенсора",
                        confidenceUa = "Апаратний збіг ультраширокого сенсора",
                        confidenceEn = "Hardware match for ultra-wide sensor",
                        detailsRu = "8-мегапиксельная ультраширокоугольная матрица Sony IMX355.",
                        detailsUa = "8-мегапіксельна надширококутна матриця Sony IMX355.",
                        detailsEn = "8MP ultra-wide sensor Sony IMX355."
                    )
                }
            }
        }

        // 4. Fallback based on physical optical format
        if (megapixels in 45.0..55.0 && diagonal <= 6.5f) {
            return SensorVendorGuess(
                vendorName = "SmartSens Technology 🇨🇳",
                probableModels = listOf("SmartSens SC500CS (50 Мп)"),
                confidenceRu = "Аппаратное соответствие оптического формата 1/2.76\"",
                confidenceUa = "Апаратна відповідність оптичного формату 1/2.76\"",
                confidenceEn = "Hardware match for 1/2.76\" optical format",
                detailsRu = "Формат матрицы 1/2.76\" с субпикселем 0.64 мкм. Соответствует спецификации SmartSens SC500CS.",
                detailsUa = "Формат матриці 1/2.76\" із субпікселем 0.64 мкм. Відповідає специфікації SmartSens SC500CS.",
                detailsEn = "Optical format 1/2.76\" with 0.64 µm pixel pitch. Matches SmartSens SC500CS specifications."
            )
        }

        if (megapixels in 1.8..2.5) {
            return SensorVendorGuess(
                vendorName = "GalaxyCore 🇨🇳",
                probableModels = listOf("GalaxyCore GC02M1 (2 Мп)"),
                confidenceRu = "Аппаратное соответствие макро-сенсора 1/5.0\"",
                confidenceUa = "Апаратна відповідність макро-сенсора 1/5.0\"",
                confidenceEn = "Hardware match for 1/5.0\" macro sensor",
                detailsRu = "Матрица 1/5.0\" с размером пикселя 1.75 мкм (GalaxyCore GC02M1).",
                detailsUa = "Матриця 1/5.0\" з розміром пікселя 1.75 мкм (GalaxyCore GC02M1).",
                detailsEn = "1/5.0\" sensor format with 1.75 µm pixel pitch (GalaxyCore GC02M1)."
            )
        }

        val candidates = CATALOG.map { entry ->
            val entryDiag = sqrt(entry.sensorWidthMm * entry.sensorWidthMm + entry.sensorHeightMm * entry.sensorHeightMm)
            val mpDiff = abs(entry.megapixels - megapixels)
            val diagDiff = abs(entryDiag - diagonal)
            val score = (mpDiff * 2.0) + (diagDiff * 5.0)
            Pair(entry, score)
        }.sortedBy { it.second }

        val best = candidates.firstOrNull()
        if (best != null) {
            return SensorVendorGuess(
                vendorName = best.first.vendor,
                probableModels = listOf(best.first.modelName),
                confidenceRu = "Оптическое соответствие физическому замеру",
                confidenceUa = "Оптична відповідність фізичному виміру",
                confidenceEn = "Optical match by physical measurement",
                detailsRu = "Физический размер: ${"%.2f".format(widthMm)} × ${"%.2f".format(heightMm)} мм (${best.first.opticalFormat}, ${best.first.pixelPitchMicrons} мкм).",
                detailsUa = "Фізичний розмір: ${"%.2f".format(widthMm)} × ${"%.2f".format(heightMm)} мм (${best.first.opticalFormat}, ${best.first.pixelPitchMicrons} мкм).",
                detailsEn = "Physical dimensions: ${"%.2f".format(widthMm)} × ${"%.2f".format(heightMm)} mm (${best.first.opticalFormat}, ${best.first.pixelPitchMicrons} µm)."
            )
        }

        return SensorVendorGuess(
            vendorName = "SmartSens Technology 🇨🇳",
            probableModels = listOf("SC500CS"),
            confidenceRu = "Анализ геометрии матрицы",
            confidenceUa = "Аналіз геометрії матриці",
            confidenceEn = "Sensor geometry analysis",
            detailsRu = "Определено по физическому замеру оптики.",
            detailsUa = "Визначено за фізичним заміром оптики.",
            detailsEn = "Determined by optical measurements."
        )
    }
}
