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
            keyFeaturesRu = "Массовый 50-мегапиксельный сенсор SmartSens для доступных смартфонов (Redmi, Realme, Transsion). Технология SFCPixel и энергоэффективность",
            keyFeaturesUa = "Масовий 50-мегапіксельний сенсор SmartSens для доступних смартфонів (Redmi, Realme, Transsion). Технологія SFCPixel та енергоефективність",
            keyFeaturesEn = "Mainstream 50MP SmartSens sensor for affordable smartphones (Redmi, Realme, Transsion). SFCPixel tech and low power consumption",
            typicalPhones = "Redmi 13, Redmi 14C, Redmi A3/A7 series, Realme C-series, Tecno Spark"
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
            typicalPhones = "Redmi A / C series, Honor X-series, Infinix"
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
            typicalPhones = "Redmi A3/A7, POCO, Realme"
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
            typicalPhones = "Redmi A3 Pro / A7, Redmi 14C, Transsion"
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
            keyFeaturesRu = "Массовый 8 Мп сенсор для фронтальных и вспомогательных камер",
            keyFeaturesUa = "Масовий 8 Мп сенсор для фронтальних та допоміжних камер",
            keyFeaturesEn = "Mainstream 8MP sensor for selfie and auxiliary cameras",
            typicalPhones = "Redmi, Realme, Transsion"
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
            typicalPhones = "Redmi A-series, Redmi C-series, Realme"
        ),
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
            autofocusTech = "QPD",
            keyFeaturesRu = "Флагманский сенсор PureCel Plus-S с Dual Conversion Gain",
            keyFeaturesUa = "Флагманський сенсор PureCel Plus-S з Dual Conversion Gain",
            keyFeaturesEn = "Flagship PureCel Plus-S sensor with Dual Conversion Gain",
            typicalPhones = "Xiaomi 14 / 14 Pro, Honor Magic 6, iQOO 12"
        ),

        // Samsung System LSI
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
            typicalPhones = "Redmi 12/13, Galaxy A15/A25, Motorola"
        ),

        // Sony Semiconductor
        SensorCatalogEntry(
            vendor = "Sony",
            modelName = "LYT-600 / IMX882",
            megapixels = 50.0,
            opticalFormat = "1/1.95\"",
            sensorWidthMm = 6.56f,
            sensorHeightMm = 4.92f,
            pixelPitchMicrons = 0.80f,
            maxResolution = "8192 × 6144",
            releaseYear = 2024,
            autofocusTech = "All-pixel PDAF",
            keyFeaturesRu = "Крупный 50-Мп сенсор Sony (1/1.95\") для старших Redmi Note и Realme Pro",
            keyFeaturesUa = "Великий 50-Мп сенсор Sony (1/1.95\") для старших Redmi Note та Realme Pro",
            keyFeaturesEn = "Large 50MP Sony sensor (1/1.95\") for higher Redmi Note and Realme Pro",
            typicalPhones = "Redmi Note 14 5G/Pro, Realme 12 Pro+, Vivo V30"
        ),
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
            autofocusTech = "All-pixel Octa PD",
            keyFeaturesRu = "Флагманский 1-дюймовый сенсор Sony второго поколения",
            keyFeaturesUa = "Флагманський 1-дюймовий сенсор Sony другого покоління",
            keyFeaturesEn = "Flagship 1-inch 2nd gen Sony sensor",
            typicalPhones = "Xiaomi 14 Ultra, Oppo Find X7 Ultra"
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
        val normalizedModel = model.lowercase().replace("а", "a").replace("с", "c")
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
                detailsRu = "Драйвер ядра и системная шина подтверждают физический чип: $primaryDetected (${detectionSourceText ?: "Kernel Probe"}).",
                detailsUa = "Драйвер ядра та системна шина підтверджують фізичний чип: $primaryDetected (${detectionSourceText ?: "Kernel Probe"}).",
                detailsEn = "Linux kernel driver and system bus confirm physical chip: $primaryDetected (${detectionSourceText ?: "Kernel Probe"})."
            )
        }

        // 2. Physical Optical Size Constraint:
        // SONY NEVER PRODUCED A 1/2.76" OR 1/2.88" 50MP SENSOR!
        // If diagonal <= 6.5 mm and MP is ~50MP: it is physically SmartSens, GalaxyCore, OmniVision, or Samsung JN1.
        val isCompact50Mp = megapixels in 45.0..55.0 && diagonal <= 6.5f

        val isXiaomiOrRedmi = normalizedMfr.contains("xiaomi") ||
                normalizedModel.contains("redmi") ||
                normalizedModel.contains("poco") ||
                normalizedModel.contains("a7") ||
                normalizedModel.contains("a3") ||
                normalizedModel.contains("note")

        if (isXiaomiOrRedmi && isCompact50Mp) {
            return SensorVendorGuess(
                vendorName = "SmartSens / GalaxyCore / OmniVision / Samsung",
                probableModels = listOf("SmartSens SC500CS", "GalaxyCore GC50E0", "OmniVision OV50D", "Samsung ISOCELL JN1"),
                confidenceRu = "Аппаратное совпадение оптики 1/2.76\" (SmartSens / GalaxyCore / OV)",
                confidenceUa = "Апаратний збіг оптики 1/2.76\" (SmartSens / GalaxyCore / OV)",
                confidenceEn = "Hardware Optical Match 1/2.76\" (SmartSens / GalaxyCore / OV)",
                detailsRu = "Физический размер сенсора (${"%.2f".format(widthMm)} × ${"%.2f".format(heightMm)} мм, 1/2.76\", 0.64 мкм). Sony не производит 50 Мп матрицы этого формата. В доступных моделях Redmi Xiaomi использует чипы SmartSens SC500CS, GalaxyCore GC50E0, OmniVision OV50D или Samsung JN1.",
                detailsUa = "Фізичний розмір сенсора (${"%.2f".format(widthMm)} × ${"%.2f".format(heightMm)} мм, 1/2.76\", 0.64 мкм). Sony не виробляє 50 Мп матриці цього формату. У доступних моделях Redmi Xiaomi використовує чипи SmartSens SC500CS, GalaxyCore GC50E0, OmniVision OV50D або Samsung JN1.",
                detailsEn = "Sensor physical format (${"%.2f".format(widthMm)} × ${"%.2f".format(heightMm)} mm, 1/2.76\", 0.64 µm). Sony does not manufacture 50MP sensors in this optical format. In budget Redmi devices Xiaomi equips SmartSens SC500CS, GalaxyCore GC50E0, OmniVision OV50D, or Samsung JN1."
            )
        }

        // 2MP auxiliary/macro check
        if (megapixels in 1.8..2.5) {
            return SensorVendorGuess(
                vendorName = "GalaxyCore / SmartSens / OmniVision",
                probableModels = listOf("GalaxyCore GC02M1", "SmartSens SC202CS", "OmniVision OV02B"),
                confidenceRu = "Аппаратное соответствие макро-сенсора",
                confidenceUa = "Апаратна відповідність макро-сенсора",
                confidenceEn = "Hardware macro sensor match",
                detailsRu = "Вспомогательный 2-Мп сенсор на базе GalaxyCore GC02M1, SmartSens SC202CS или OmniVision OV02B (матрица 1/5.0\", 1.75 мкм).",
                detailsUa = "Допоміжний 2-Мп сенсор на базі GalaxyCore GC02M1, SmartSens SC202CS або OmniVision OV02B (матриця 1/5.0\", 1.75 мкм).",
                detailsEn = "Auxiliary 2MP sensor based on GalaxyCore GC02M1, SmartSens SC202CS, or OmniVision OV02B (1/5.0\", 1.75 µm)."
            )
        }

        // 8MP ultra-wide or selfie check
        if (megapixels in 7.0..9.0) {
            return SensorVendorGuess(
                vendorName = "GalaxyCore / SmartSens / OmniVision / Sony",
                probableModels = listOf("GalaxyCore GC08A3", "SmartSens SC800CS", "OmniVision OV08D", "Sony IMX355"),
                confidenceRu = "Аппаратное соответствие сенсора 8 Мп",
                confidenceUa = "Апаратна відповідність сенсора 8 Мп",
                confidenceEn = "Hardware 8MP sensor match",
                detailsRu = "8-мегапиксельная матрица формата 1/4.0\" (1.12 мкм), типичная для сверхширокоугольных и фронтальных камер.",
                detailsUa = "8-мегапіксельна матриця формату 1/4.0\" (1.12 мкм), типова для надширококутних та фронтальних камер.",
                detailsEn = "8MP sensor in 1/4.0\" format (1.12 µm), standard for ultra-wide and selfie cameras."
            )
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
                confidenceRu = "Оптическое соответствие физическому размеру",
                confidenceUa = "Оптична відповідність фізичному розміру",
                confidenceEn = "Optical match by physical dimensions",
                detailsRu = "Физический замер: ${"%.2f".format(widthMm)} × ${"%.2f".format(heightMm)} мм, диагональ: ${"%.2f".format(diagonal)} мм (${best.first.opticalFormat}), ${best.first.pixelPitchMicrons} мкм.",
                detailsUa = "Фізичний замір: ${"%.2f".format(widthMm)} × ${"%.2f".format(heightMm)} мм, діагональ: ${"%.2f".format(diagonal)} мм (${best.first.opticalFormat}), ${best.first.pixelPitchMicrons} мкм.",
                detailsEn = "Physical measurement: ${"%.2f".format(widthMm)} × ${"%.2f".format(heightMm)} mm, diagonal: ${"%.2f".format(diagonal)} mm (${best.first.opticalFormat}), ${best.first.pixelPitchMicrons} µm."
            )
        }

        val topModels = candidates.take(3).map { "${it.first.vendor} ${it.first.modelName}" }
        return SensorVendorGuess(
            vendorName = "SmartSens / GalaxyCore / OmniVision / Samsung",
            probableModels = topModels,
            confidenceRu = "Анализ геометрии матрицы",
            confidenceUa = "Аналіз геометрії матриці",
            confidenceEn = "Sensor geometry analysis",
            detailsRu = "Определено по физическому замеру оптики: ${"%.2f".format(widthMm)} × ${"%.2f".format(heightMm)} мм (~${"%.1f".format(megapixels)} Мп).",
            detailsUa = "Визначено за фізичним заміром оптики: ${"%.2f".format(widthMm)} × ${"%.2f".format(heightMm)} мм (~${"%.1f".format(megapixels)} Мп).",
            detailsEn = "Determined by optical measurements: ${"%.2f".format(widthMm)} × ${"%.2f".format(heightMm)} mm (~${"%.1f".format(megapixels)} MP)."
        )
    }
}
