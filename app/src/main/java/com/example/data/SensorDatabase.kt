package com.example.data

import com.example.localization.AppLanguage
import com.example.model.SensorCatalogEntry
import com.example.model.SensorVendorGuess
import kotlin.math.abs
import kotlin.math.sqrt

object SensorDatabase {

    val CATALOG: List<SensorCatalogEntry> = listOf(
        // Sony Semiconductor (🇯🇵 Japan)
        SensorCatalogEntry(
            vendor = "Sony Semiconductor 🇯🇵",
            modelName = "LYT-900",
            megapixels = 50.0,
            opticalFormat = "1.0\"",
            sensorWidthMm = 12.8f,
            sensorHeightMm = 9.6f,
            pixelPitchMicrons = 1.60f,
            maxResolution = "8192 × 6144",
            releaseYear = 2024,
            autofocusTech = "Dual-Pixel Pro PDAF",
            keyFeaturesRu = "Флагманский 1-дюймовый сенсор второго поколения с 2-слойным транзистором (Stacked CMOS)",
            keyFeaturesUa = "Флагманський 1-дюймовий сенсор другого покоління з 2-шаровим транзистором (Stacked CMOS)",
            keyFeaturesEn = "Flagship 1-inch 2nd-gen sensor with 2-layer stacked transistor pixel tech",
            typicalPhones = "Xiaomi 14 Ultra, Vivo X100 Ultra, Oppo Find X7 Ultra"
        ),
        SensorCatalogEntry(
            vendor = "Sony Semiconductor 🇯🇵",
            modelName = "IMX989",
            megapixels = 50.0,
            opticalFormat = "1.0\"",
            sensorWidthMm = 12.8f,
            sensorHeightMm = 9.6f,
            pixelPitchMicrons = 1.60f,
            maxResolution = "8192 × 6144",
            releaseYear = 2022,
            autofocusTech = "Octa-PD PDAF",
            keyFeaturesRu = "1-дюймовый сенсор для премиальных флагманов совместной разработки Xiaomi и Sony",
            keyFeaturesUa = "1-дюймовий сенсор для преміальних флагманів спільної розробки Xiaomi та Sony",
            keyFeaturesEn = "1.0-inch sensor for premium flagships co-developed by Xiaomi and Sony",
            typicalPhones = "Xiaomi 13 Ultra, Xiaomi 13 Pro, Vivo X90 Pro+"
        ),
        SensorCatalogEntry(
            vendor = "Sony Semiconductor 🇯🇵",
            modelName = "LYT-808",
            megapixels = 50.0,
            opticalFormat = "1/1.43\"",
            sensorWidthMm = 8.96f,
            sensorHeightMm = 6.72f,
            pixelPitchMicrons = 1.12f,
            maxResolution = "8192 × 6144",
            releaseYear = 2023,
            autofocusTech = "All-Pixel Omni-directional PDAF",
            keyFeaturesRu = "Премиальный сенсор 1/1.43\" с двухслойной структурой пикселей Pixel Stacked",
            keyFeaturesUa = "Преміальний сенсор 1/1.43\" із двошаровою структурою пікселів Pixel Stacked",
            keyFeaturesEn = "Premium 1/1.43\" sensor with dual-layer Pixel Stacked architecture",
            typicalPhones = "OnePlus 12, Realme GT5 Pro"
        ),
        SensorCatalogEntry(
            vendor = "Sony Semiconductor 🇯🇵",
            modelName = "IMX890",
            megapixels = 50.0,
            opticalFormat = "1/1.56\"",
            sensorWidthMm = 8.19f,
            sensorHeightMm = 6.14f,
            pixelPitchMicrons = 1.00f,
            maxResolution = "8192 × 6144",
            releaseYear = 2023,
            autofocusTech = "All-Pixel Omni-directional PDAF",
            keyFeaturesRu = "Популярный субфлагманский 50-Мп сенсор Sony 1/1.56\" с OIS",
            keyFeaturesUa = "Популярний субфлагманський 50-Мп сенсор Sony 1/1.56\" з OIS",
            keyFeaturesEn = "Widely popular sub-flagship 50MP Sony sensor 1/1.56\" with OIS",
            typicalPhones = "OnePlus 11, Realme GT3, Nothing Phone (2)"
        ),
        SensorCatalogEntry(
            vendor = "Sony Semiconductor 🇯🇵",
            modelName = "LYT-600",
            megapixels = 50.0,
            opticalFormat = "1/1.95\"",
            sensorWidthMm = 6.56f,
            sensorHeightMm = 4.92f,
            pixelPitchMicrons = 0.80f,
            maxResolution = "8192 × 6144",
            releaseYear = 2024,
            autofocusTech = "All-pixel PDAF",
            keyFeaturesRu = "Крупный 50-Мп сенсор Sony (1/1.95\") для старших Redmi Note 14 Pro, Realme и Vivo",
            keyFeaturesUa = "Великий 50-Мп сенсор Sony (1/1.95\") для старших Redmi Note 14 Pro, Realme та Vivo",
            keyFeaturesEn = "Large 50MP Sony sensor (1/1.95\") for higher Redmi Note 14 Pro, Realme, and Vivo",
            typicalPhones = "Redmi Note 14 Pro, Realme 12 Pro+, Vivo V30"
        ),
        SensorCatalogEntry(
            vendor = "Sony Semiconductor 🇯🇵",
            modelName = "IMX355",
            megapixels = 8.0,
            opticalFormat = "1/4.0\"",
            sensorWidthMm = 3.20f,
            sensorHeightMm = 2.40f,
            pixelPitchMicrons = 1.12f,
            maxResolution = "3264 × 2448",
            releaseYear = 2018,
            autofocusTech = "Fixed Focus",
            keyFeaturesRu = "8 Мп ультраширокий сенсор для Redmi Note и POCO",
            keyFeaturesUa = "8 Мп ультраширокий сенсор для Redmi Note та POCO",
            keyFeaturesEn = "8MP ultra-wide sensor for Redmi Note series and POCO",
            typicalPhones = "Redmi Note series, POCO X-series, Xiaomi"
        ),

        // Samsung System LSI (🇰🇷 South Korea)
        SensorCatalogEntry(
            vendor = "Samsung System LSI 🇰🇷",
            modelName = "ISOCELL HP2",
            megapixels = 200.0,
            opticalFormat = "1/1.3\"",
            sensorWidthMm = 9.84f,
            sensorHeightMm = 7.38f,
            pixelPitchMicrons = 0.60f,
            maxResolution = "16320 × 12240",
            releaseYear = 2023,
            autofocusTech = "Super QPD PDAF",
            keyFeaturesRu = "Флагманский 200 Мп сенсор с технологией объединения пикселей 16-в-1 Tetra2pixel",
            keyFeaturesUa = "Флагманський 200 Мп сенсор з технологією об'єднання пікселів 16-в-1 Tetra2pixel",
            keyFeaturesEn = "Flagship 200MP sensor with 16-in-1 Tetra2pixel binning technology",
            typicalPhones = "Samsung Galaxy S23 Ultra, Galaxy S24 Ultra"
        ),
        SensorCatalogEntry(
            vendor = "Samsung System LSI 🇰🇷",
            modelName = "ISOCELL HP3",
            megapixels = 200.0,
            opticalFormat = "1/1.4\"",
            sensorWidthMm = 9.14f,
            sensorHeightMm = 6.85f,
            pixelPitchMicrons = 0.56f,
            maxResolution = "16320 × 12240",
            releaseYear = 2022,
            autofocusTech = "Super QPD",
            keyFeaturesRu = "200 Мп сенсор в серии Redmi Note Pro+ и Honor",
            keyFeaturesUa = "200 Мп сенсор у серії Redmi Note Pro+ та Honor",
            keyFeaturesEn = "200MP sensor in Redmi Note Pro+ series and Honor",
            typicalPhones = "Redmi Note 13 Pro+, Redmi Note 13 Pro 5G, Honor 90"
        ),
        SensorCatalogEntry(
            vendor = "Samsung System LSI 🇰🇷",
            modelName = "ISOCELL GN3",
            megapixels = 50.0,
            opticalFormat = "1/1.57\"",
            sensorWidthMm = 8.16f,
            sensorHeightMm = 6.12f,
            pixelPitchMicrons = 1.00f,
            maxResolution = "8160 × 6120",
            releaseYear = 2022,
            autofocusTech = "Dual Pixel Pro",
            keyFeaturesRu = "Основная 50-Мп матрица флагманских смартфонов Samsung Galaxy S22/S23/S24",
            keyFeaturesUa = "Основна 50-Мп матриця флагманських смартфонів Samsung Galaxy S22/S23/S24",
            keyFeaturesEn = "Primary 50MP sensor of Samsung Galaxy S22/S23/S24 flagship series",
            typicalPhones = "Samsung Galaxy S24, Galaxy S23, Galaxy S22, Galaxy Z Fold5"
        ),
        SensorCatalogEntry(
            vendor = "Samsung System LSI 🇰🇷",
            modelName = "ISOCELL JN1",
            megapixels = 50.0,
            opticalFormat = "1/2.76\"",
            sensorWidthMm = 4.64f,
            sensorHeightMm = 3.48f,
            pixelPitchMicrons = 0.64f,
            maxResolution = "8160 × 6120",
            releaseYear = 2021,
            autofocusTech = "Double Super PD",
            keyFeaturesRu = "Самый массовый в мире 50-Мп сенсор Samsung с субпикселями 0.64 мкм (ISOCELL 2.0)",
            keyFeaturesUa = "Наймасовіший у світі 50-Мп сенсор Samsung із субпікселями 0.64 мкм (ISOCELL 2.0)",
            keyFeaturesEn = "World's most widely used 50MP Samsung sensor with 0.64 µm pixels (ISOCELL 2.0)",
            typicalPhones = "Galaxy A15/A25/A05s, Redmi 12/13, Motorola Moto G, Nothing, OnePlus"
        ),

        // OmniVision Technologies (🇺🇸🇨🇳 USA / China)
        SensorCatalogEntry(
            vendor = "OmniVision Technologies 🇺🇸🇨🇳",
            modelName = "OV50H (Light Hunter 900)",
            megapixels = 50.0,
            opticalFormat = "1/1.31\"",
            sensorWidthMm = 9.75f,
            sensorHeightMm = 7.31f,
            pixelPitchMicrons = 1.20f,
            maxResolution = "8192 × 6144",
            releaseYear = 2023,
            autofocusTech = "QPD (Quad Phase Detection)",
            keyFeaturesRu = "Флагманский сенсор OmniVision с динамическим диапазоном 13.5 EV и Dual Native ISO Fusion Max",
            keyFeaturesUa = "Флагманський сенсор OmniVision із динамічним діапазоном 13.5 EV та Dual Native ISO Fusion Max",
            keyFeaturesEn = "Flagship OmniVision sensor with 13.5 EV dynamic range and Dual Native ISO Fusion Max",
            typicalPhones = "Xiaomi 14, Xiaomi 14 Pro, Honor Magic6 Pro, iQOO 12"
        ),
        SensorCatalogEntry(
            vendor = "OmniVision Technologies 🇺🇸🇨🇳",
            modelName = "OV64B",
            megapixels = 64.0,
            opticalFormat = "1/2.0\"",
            sensorWidthMm = 6.40f,
            sensorHeightMm = 4.80f,
            pixelPitchMicrons = 0.70f,
            maxResolution = "9248 × 6936",
            releaseYear = 2020,
            autofocusTech = "Type-2 2x2 ML-PDAF",
            keyFeaturesRu = "Матрица 64 Мп, эталон перископических зум-модулей и основных камер среднего сегмента",
            keyFeaturesUa = "Матриця 64 Мп, еталон перископічних зум-модулів та основних камер середнього сегменту",
            keyFeaturesEn = "64MP sensor, standard benchmark for periscope telephoto and upper-mid main cameras",
            typicalPhones = "OnePlus 12, POCO X6 Pro, Realme GT5 Pro"
        ),
        SensorCatalogEntry(
            vendor = "OmniVision Technologies 🇺🇸🇨🇳",
            modelName = "OV50D / OV50M",
            megapixels = 50.0,
            opticalFormat = "1/2.88\"",
            sensorWidthMm = 4.40f,
            sensorHeightMm = 3.30f,
            pixelPitchMicrons = 0.61f,
            maxResolution = "8192 × 6144",
            releaseYear = 2023,
            autofocusTech = "PDAF",
            keyFeaturesRu = "50-Мп сенсор OmniVision для доступных и массовых смартфонов",
            keyFeaturesUa = "50-Мп сенсор OmniVision для доступних та масових смартфонів",
            keyFeaturesEn = "50MP OmniVision sensor for affordable and mainstream smartphones",
            typicalPhones = "Redmi series, Realme, Motorola, Vivo"
        ),
        SensorCatalogEntry(
            vendor = "OmniVision Technologies 🇺🇸🇨🇳",
            modelName = "OV02B10",
            megapixels = 2.0,
            opticalFormat = "1/5.0\"",
            sensorWidthMm = 2.40f,
            sensorHeightMm = 1.80f,
            pixelPitchMicrons = 1.75f,
            maxResolution = "1600 × 1200",
            releaseYear = 2019,
            autofocusTech = "Fixed Focus",
            keyFeaturesRu = "Массовый 2 Мп вспомогательный датчик глубины и макросъемки",
            keyFeaturesUa = "Масовий 2 Мп допоміжний датчик глибини та макрозйомки",
            keyFeaturesEn = "Mass production 2MP auxiliary depth and macro sensor",
            typicalPhones = "Redmi Note, Xiaomi, OnePlus, Realme, Motorola"
        ),

        // SmartSens Technology (思特威 🇨🇳 China)
        SensorCatalogEntry(
            vendor = "SmartSens Technology 🇨🇳",
            modelName = "SC500CS",
            megapixels = 50.0,
            opticalFormat = "1/2.76\"",
            sensorWidthMm = 4.64f,
            sensorHeightMm = 3.48f,
            pixelPitchMicrons = 0.64f,
            maxResolution = "8160 × 6120",
            releaseYear = 2023,
            autofocusTech = "PDAF",
            keyFeaturesRu = "50-мегапиксельный сенсор SmartSens с технологией SFCPixel для бюджетных линеек",
            keyFeaturesUa = "50-мегапіксельний сенсор SmartSens із технологією SFCPixel для бюджетних лінійок",
            keyFeaturesEn = "50MP SmartSens sensor with SFCPixel technology for budget devices",
            typicalPhones = "Redmi A3/A7, Redmi 14C, Realme C-series"
        ),
        SensorCatalogEntry(
            vendor = "SmartSens Technology 🇨🇳",
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

        // GalaxyCore (格科微 🇨🇳 China)
        SensorCatalogEntry(
            vendor = "GalaxyCore 🇨🇳",
            modelName = "GC50E0",
            megapixels = 50.0,
            opticalFormat = "1/2.88\"",
            sensorWidthMm = 4.40f,
            sensorHeightMm = 3.30f,
            pixelPitchMicrons = 0.61f,
            maxResolution = "8192 × 6144",
            releaseYear = 2024,
            autofocusTech = "PDAF",
            keyFeaturesRu = "50-мегапиксельный сенсор GalaxyCore с объединением пикселей 4-в-1",
            keyFeaturesUa = "50-мегапіксельний сенсор GalaxyCore з об'єднанням пікселів 4-в-1",
            keyFeaturesEn = "50MP GalaxyCore sensor with 4-in-1 pixel binning",
            typicalPhones = "Entry-level smartphones (Transsion, budget ODM)"
        ),
        SensorCatalogEntry(
            vendor = "GalaxyCore 🇨🇳",
            modelName = "GC08A3",
            megapixels = 8.0,
            opticalFormat = "1/4.0\"",
            sensorWidthMm = 3.20f,
            sensorHeightMm = 2.40f,
            pixelPitchMicrons = 1.12f,
            maxResolution = "3264 × 2448",
            releaseYear = 2021,
            autofocusTech = "Fixed Focus",
            keyFeaturesRu = "8 Мп сенсор для бюджетных селфи и вспомогательных модулей",
            keyFeaturesUa = "8 Мп сенсор для бюджетних селфі та допоміжних модулів",
            keyFeaturesEn = "8MP sensor for budget selfie and auxiliary modules",
            typicalPhones = "Redmi A-series, Realme C-series, Transsion"
        ),
        SensorCatalogEntry(
            vendor = "GalaxyCore 🇨🇳",
            modelName = "GC02M1",
            megapixels = 2.0,
            opticalFormat = "1/5.0\"",
            sensorWidthMm = 2.40f,
            sensorHeightMm = 1.80f,
            pixelPitchMicrons = 1.75f,
            maxResolution = "1600 × 1200",
            releaseYear = 2018,
            autofocusTech = "Fixed Focus",
            keyFeaturesRu = "2-мегапиксельный датчик макро и боке для бюджетных аппаратов",
            keyFeaturesUa = "2-мегапіксельний датчик макро та боке для бюджетних апаратів",
            keyFeaturesEn = "2MP macro and depth sensor for entry-level devices",
            typicalPhones = "Budget devices (Redmi A, Realme, Infinix, Tecno)"
        ),

        // SK Hynix (🇰🇷 South Korea)
        SensorCatalogEntry(
            vendor = "SK Hynix 🇰🇷",
            modelName = "Hi-5021",
            megapixels = 50.0,
            opticalFormat = "1/2.55\"",
            sensorWidthMm = 5.60f,
            sensorHeightMm = 4.20f,
            pixelPitchMicrons = 0.70f,
            maxResolution = "8192 × 6144",
            releaseYear = 2023,
            autofocusTech = "PDAF",
            keyFeaturesRu = "50-Мп сенсор SK Hynix Black Pearl для смартфонов среднего уровня",
            keyFeaturesUa = "50-Мп сенсор SK Hynix Black Pearl для смартфонів середнього рівня",
            keyFeaturesEn = "50MP SK Hynix Black Pearl sensor for mid-range smartphones",
            typicalPhones = "Motorola, Vivo, Transsion"
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
        val normalizedModel = model.lowercase().trim()
        val normalizedMfr = manufacturer.lowercase().trim()

        // 1. Direct Kernel / Hardware Driver match! (Highest priority, 100% verified)
        if (detectedHardwareDriverModels.isNotEmpty()) {
            val primaryDetected = detectedHardwareDriverModels.first()
            val vendor = when {
                primaryDetected.startsWith("SC", ignoreCase = true) -> "SmartSens Technology 🇨🇳"
                primaryDetected.startsWith("GC", ignoreCase = true) -> "GalaxyCore 🇨🇳"
                primaryDetected.startsWith("OV", ignoreCase = true) -> "OmniVision Technologies 🇺🇸🇨🇳"
                primaryDetected.startsWith("IMX", ignoreCase = true) || primaryDetected.startsWith("LYT", ignoreCase = true) -> "Sony Semiconductor 🇯🇵"
                primaryDetected.startsWith("S5K", ignoreCase = true) || primaryDetected.startsWith("HP", ignoreCase = true) || primaryDetected.startsWith("JN", ignoreCase = true) -> "Samsung System LSI 🇰🇷"
                primaryDetected.startsWith("HI", ignoreCase = true) -> "SK Hynix 🇰🇷"
                else -> "Hardware Driver ($primaryDetected)"
            }

            return SensorVendorGuess(
                vendorName = vendor,
                probableModels = listOf(primaryDetected),
                confidenceRu = "100% Аппаратно подтверждено (Драйвер ядра / HAL)",
                confidenceUa = "100% Апаратно підтверджено (Драйвер ядра / HAL)",
                confidenceEn = "100% Hardware Confirmed (Kernel / HAL Driver)",
                detailsRu = "Драйвер ядра подтверждает физический чип $primaryDetected (${detectionSourceText ?: "HAL Probe"}).",
                detailsUa = "Драйвер ядра підтверджує фізичний чип $primaryDetected (${detectionSourceText ?: "HAL Probe"}).",
                detailsEn = "Kernel driver confirms physical chip $primaryDetected (${detectionSourceText ?: "HAL Probe"})."
            )
        }

        // 2. Strict Model-Specific Hardware Matching (NEVER loose substring matching)

        // SAMSUNG GALAXY DEVICES
        if (normalizedMfr.contains("samsung") || normalizedModel.contains("galaxy") || normalizedModel.startsWith("sm-")) {
            val isUltra = normalizedModel.contains("ultra")
            val isSOrFold = normalizedModel.contains("s2") || normalizedModel.contains("fold") || normalizedModel.contains("flip")
            if (isUltra && megapixels >= 180.0) {
                return SensorVendorGuess(
                    vendorName = "Samsung System LSI 🇰🇷",
                    probableModels = listOf("Samsung ISOCELL HP2 (200 Мп)"),
                    confidenceRu = "Флагманский сенсор Samsung Galaxy Ultra",
                    confidenceUa = "Флагманський сенсор Samsung Galaxy Ultra",
                    confidenceEn = "Flagship Samsung Galaxy Ultra sensor",
                    detailsRu = "200-Мп сенсор Samsung ISOCELL HP2 с Tetra2pixel и OIS.",
                    detailsUa = "200-Мп сенсор Samsung ISOCELL HP2 з Tetra2pixel та OIS.",
                    detailsEn = "200MP Samsung ISOCELL HP2 with Tetra2pixel and OIS."
                )
            }
            if (isSOrFold && !isFront && megapixels in 48.0..52.0) {
                return SensorVendorGuess(
                    vendorName = "Samsung System LSI 🇰🇷",
                    probableModels = listOf("Samsung ISOCELL GN3 / GN5 (50 Мп)"),
                    confidenceRu = "Флагманский сенсор серии Galaxy S",
                    confidenceUa = "Флагманський сенсор серії Galaxy S",
                    confidenceEn = "Flagship Galaxy S series sensor",
                    detailsRu = "50-Мп сенсор Samsung ISOCELL GN3/GN5 (1/1.57\", Dual Pixel Pro).",
                    detailsUa = "50-Мп сенсор Samsung ISOCELL GN3/GN5 (1/1.57\", Dual Pixel Pro).",
                    detailsEn = "50MP Samsung ISOCELL GN3/GN5 (1/1.57\", Dual Pixel Pro)."
                )
            }
            if (!isFront && megapixels in 48.0..52.0) {
                // Galaxy A-series (A15, A25, A35, A55, A05s)
                return SensorVendorGuess(
                    vendorName = "Samsung System LSI 🇰🇷",
                    probableModels = listOf("Samsung ISOCELL JN1 / GN5 (50 Мп)"),
                    confidenceRu = "Фирменный сенсор Samsung ISOCELL для линейки Galaxy",
                    confidenceUa = "Фірмовий сенсор Samsung ISOCELL для лінійки Galaxy",
                    confidenceEn = "Proprietary Samsung ISOCELL sensor for Galaxy lineup",
                    detailsRu = "В смартфонах Samsung Galaxy в качестве основной 50 Мп камеры устанавливается матрица Samsung ISOCELL.",
                    detailsUa = "У смартфонах Samsung Galaxy як основна 50 Мп камера встановлюється матриця Samsung ISOCELL.",
                    detailsEn = "Samsung Galaxy smartphones utilize proprietary Samsung ISOCELL 50MP sensors."
                )
            }
            if (megapixels in 1.8..2.5) {
                return SensorVendorGuess(
                    vendorName = "OmniVision / GalaxyCore 🇺🇸🇨🇳",
                    probableModels = listOf("OmniVision OV02B10 (2 Мп)", "GalaxyCore GC02M1 (2 Мп)"),
                    confidenceRu = "Вспомогательный макро-сенсор Galaxy A-серии",
                    confidenceUa = "Допоміжний макро-сенсор Galaxy A-серії",
                    confidenceEn = "Auxiliary macro sensor of Galaxy A-series",
                    detailsRu = "Samsung использует мульти-поставку вспомогательных 2 Мп модулей от OmniVision Technologies и GalaxyCore.",
                    detailsUa = "Samsung використовує мульти-постачання допоміжних 2 Мп модулів від OmniVision Technologies та GalaxyCore.",
                    detailsEn = "Samsung dual-sources auxiliary 2MP modules from OmniVision Technologies and GalaxyCore."
                )
            }
        }

        // GOOGLE PIXEL DEVICES
        if (normalizedMfr.contains("google") || normalizedModel.contains("pixel")) {
            if (!isFront && megapixels in 48.0..52.0) {
                return SensorVendorGuess(
                    vendorName = "Samsung System LSI 🇰🇷",
                    probableModels = listOf("Samsung ISOCELL GNK / GN1 / GNV (50 Мп)"),
                    confidenceRu = "Фирменный сенсор Google Pixel (Samsung ISOCELL)",
                    confidenceUa = "Фірмовий сенсор Google Pixel (Samsung ISOCELL)",
                    confidenceEn = "Google Pixel primary sensor (Samsung ISOCELL)",
                    detailsRu = "Google оснащает смартфоны Pixel 50-Мп сенсорами Samsung ISOCELL (GNK/GN1, 1/1.31\").",
                    detailsUa = "Google оснащує смартфони Pixel 50-Мп сенсорами Samsung ISOCELL (GNK/GN1, 1/1.31\").",
                    detailsEn = "Google equips Pixel devices with custom Samsung ISOCELL 50MP sensors (GNK/GN1, 1/1.31\")."
                )
            }
            if (megapixels in 11.0..13.0) {
                return SensorVendorGuess(
                    vendorName = "Sony Semiconductor 🇯🇵",
                    probableModels = listOf("Sony IMX386 / IMX363 (12 Мп)"),
                    confidenceRu = "Ультраширокий / селфи сенсор Google Pixel",
                    confidenceUa = "Ультраширокий / селфі сенсор Google Pixel",
                    confidenceEn = "Ultra-wide / selfie sensor Google Pixel",
                    detailsRu = "Матрица Sony Semiconductor для сверхширокоугольного модуля Pixel.",
                    detailsUa = "Матриця Sony Semiconductor для надширококутного модуля Pixel.",
                    detailsEn = "Sony Semiconductor sensor for Pixel ultra-wide module."
                )
            }
        }

        // XIAOMI FLAGSHIP (Xiaomi 12 / 13 / 14 / 15 / Ultra / Pro)
        val isXiaomiFlagship = (normalizedMfr.contains("xiaomi") && !normalizedModel.contains("redmi") && !normalizedModel.contains("poco"))
        if (isXiaomiFlagship) {
            if (!isFront && megapixels in 48.0..52.0) {
                return if (diagonal >= 9.0f) {
                    SensorVendorGuess(
                        vendorName = "Sony Semiconductor 🇯🇵",
                        probableModels = listOf("Sony LYT-900 / IMX989 (1.0\" 50 Мп)"),
                        confidenceRu = "Премиальный 1-дюймовый сенсор Xiaomi Flagship",
                        confidenceUa = "Преміальний 1-дюймовий сенсор Xiaomi Flagship",
                        confidenceEn = "Premium 1-inch Xiaomi Flagship sensor",
                        detailsRu = "1-дюймовая матрица Sony со оптикой Leica Summilux.",
                        detailsUa = "1-дюймова матриця Sony з оптикою Leica Summilux.",
                        detailsEn = "1-inch Sony sensor with Leica Summilux optics."
                    )
                } else {
                    SensorVendorGuess(
                        vendorName = "OmniVision Technologies 🇺🇸🇨🇳",
                        probableModels = listOf("OmniVision OV50H / Light Hunter 900 (50 Мп)", "Sony LYT-808 (50 Мп)"),
                        confidenceRu = "Флагманский сенсор Xiaomi Light Hunter / Sony",
                        confidenceUa = "Флагманський сенсор Xiaomi Light Hunter / Sony",
                        confidenceEn = "Xiaomi Light Hunter / Sony flagship sensor",
                        detailsRu = "Сенсор OmniVision OV50H (Light Hunter 900) 1/1.31\" или Sony LYT-808 с OIS.",
                        detailsUa = "Сенсор OmniVision OV50H (Light Hunter 900) 1/1.31\" або Sony LYT-808 з OIS.",
                        detailsEn = "OmniVision OV50H (Light Hunter 900) 1/1.31\" or Sony LYT-808 with OIS."
                    )
                }
            }
        }

        // REDMI NOTE SERIES (Strict match)
        val isRedmiNote = (normalizedMfr.contains("xiaomi") || normalizedModel.contains("redmi") || normalizedModel.contains("poco")) &&
                normalizedModel.contains("note")
        if (isRedmiNote) {
            if (!isFront) {
                if (megapixels in 180.0..210.0) {
                    return SensorVendorGuess(
                        vendorName = "Samsung System LSI 🇰🇷",
                        probableModels = listOf("Samsung ISOCELL HP3 / HPX (200 Мп)"),
                        confidenceRu = "Флагманский 200-Мп сенсор Redmi Note Pro+",
                        confidenceUa = "Флагманський 200-Мп сенсор Redmi Note Pro+",
                        confidenceEn = "Flagship 200MP Redmi Note Pro+ sensor",
                        detailsRu = "200-мегапиксельная матрица Samsung ISOCELL HP3 с OIS.",
                        detailsUa = "200-мегапіксельна матриця Samsung ISOCELL HP3 з OIS.",
                        detailsEn = "200MP Samsung ISOCELL HP3 with OIS."
                    )
                } else if (megapixels in 100.0..115.0) {
                    return SensorVendorGuess(
                        vendorName = "Samsung System LSI 🇰🇷",
                        probableModels = listOf("Samsung ISOCELL HM6 (108 Мп)"),
                        confidenceRu = "108-Мп сенсор Samsung ISOCELL для Redmi Note",
                        confidenceUa = "108-Мп сенсор Samsung ISOCELL для Redmi Note",
                        confidenceEn = "108MP Samsung ISOCELL sensor for Redmi Note",
                        detailsRu = "Матрица Samsung ISOCELL HM6 (1/1.67\", Nonapixel).",
                        detailsUa = "Матриця Samsung ISOCELL HM6 (1/1.67\", Nonapixel).",
                        detailsEn = "Samsung ISOCELL HM6 sensor (1/1.67\", Nonapixel)."
                    )
                } else if (megapixels in 48.0..52.0) {
                    return SensorVendorGuess(
                        vendorName = "Sony Semiconductor 🇯🇵",
                        probableModels = listOf("Sony LYT-600 / IMX882 (50 Мп)", "Samsung ISOCELL JN1 (50 Мп)"),
                        confidenceRu = "Основной 50-Мп сенсор Redmi Note",
                        confidenceUa = "Основний 50-Мп сенсор Redmi Note",
                        confidenceEn = "Main 50MP Redmi Note sensor",
                        detailsRu = "Матрица Sony LYT-600 (1/1.95\") в старших версиях или Samsung ISOCELL JN1 в стандартных.",
                        detailsUa = "Матриця Sony LYT-600 (1/1.95\") у старших версіях або Samsung ISOCELL JN1 у стандартних.",
                        detailsEn = "Sony LYT-600 (1/1.95\") in Pro models or Samsung ISOCELL JN1 in standard variants."
                    )
                } else if (megapixels in 7.0..9.0) {
                    return SensorVendorGuess(
                        vendorName = "Sony Semiconductor 🇯🇵",
                        probableModels = listOf("Sony IMX355 (8 Мп)", "OmniVision OV08D (8 Мп)"),
                        confidenceRu = "Ультраширокий сенсор Redmi Note",
                        confidenceUa = "Ультраширокий сенсор Redmi Note",
                        confidenceEn = "Ultra-wide Redmi Note sensor",
                        detailsRu = "8-мегапиксельный сенсор Sony IMX355 или OmniVision OV08D.",
                        detailsUa = "8-мегапіксельний сенсор Sony IMX355 або OmniVision OV08D.",
                        detailsEn = "8MP Sony IMX355 or OmniVision OV08D sensor."
                    )
                } else if (megapixels in 1.8..2.5) {
                    return SensorVendorGuess(
                        vendorName = "OmniVision Technologies 🇺🇸🇨🇳",
                        probableModels = listOf("OmniVision OV02B10 (2 Мп)", "SmartSens SC202CS (2 Мп)"),
                        confidenceRu = "Вспомогательный макро-сенсор Redmi Note",
                        confidenceUa = "Допоміжний макро-сенсор Redmi Note",
                        confidenceEn = "Auxiliary macro sensor of Redmi Note",
                        detailsRu = "В линейке Redmi Note 12/13/14 макро-камера комплектуется чипами OmniVision OV02B10 или SmartSens SC202CS.",
                        detailsUa = "У лінійці Redmi Note 12/13/14 макро-камера комплектується чипами OmniVision OV02B10 або SmartSens SC202CS.",
                        detailsEn = "In the Redmi Note series macro cameras are supplied by OmniVision OV02B10 or SmartSens SC202CS."
                    )
                }
            }
        }

        // STRICT ENTRY-LEVEL BUDGET REDMI/POCO (Redmi A1/A2/A3/A7, Redmi 12C/13C/14C, Poco C55/C65)
        // Strictly matched by whole word pattern - NOT by generic " a" or " c"!
        val isStrictBudgetRedmi = Regex("""\b(redmi\s*a[1-7]|poco\s*c\d{1,2}|redmi\s*1[234]c|redmi\s*[1-9]c)\b""", RegexOption.IGNORE_CASE).containsMatchIn(normalizedModel)
        if (isStrictBudgetRedmi) {
            if (!isFront) {
                if (megapixels in 45.0..55.0) {
                    return SensorVendorGuess(
                        vendorName = "SmartSens / OmniVision / GalaxyCore 🇨🇳",
                        probableModels = listOf("SmartSens SC500CS (50 Мп)", "OmniVision OV50D (50 Мп)", "GalaxyCore GC50E0 (50 Мп)"),
                        confidenceRu = "Мульти-поставка сенсора 50 Мп для бюджетной серии",
                        confidenceUa = "Мульти-постачання сенсора 50 Мп для бюджетної серії",
                        confidenceEn = "Multi-sourced 50MP sensor for budget series",
                        detailsRu = "Xiaomi использует параллельные поставки сенсоров SmartSens SC500CS, OmniVision OV50D и GalaxyCore GC50E0 в зависимости от партии.",
                        detailsUa = "Xiaomi використовує паралельні поставки сенсорів SmartSens SC500CS, OmniVision OV50D та GalaxyCore GC50E0 залежно від партії.",
                        detailsEn = "Xiaomi multi-sources 50MP sensors from SmartSens SC500CS, OmniVision OV50D, and GalaxyCore GC50E0 depending on batch."
                    )
                } else if (megapixels in 1.8..2.5) {
                    return SensorVendorGuess(
                        vendorName = "GalaxyCore / OmniVision 🇨🇳",
                        probableModels = listOf("GalaxyCore GC02M1 (2 Мп)", "OmniVision OV02B10 (2 Мп)"),
                        confidenceRu = "Вспомогательный датчик бюджетной серии",
                        confidenceUa = "Допоміжний датчик бюджетної серії",
                        confidenceEn = "Auxiliary sensor of budget series",
                        detailsRu = "Сенсор макро/глубины GalaxyCore GC02M1 или OmniVision OV02B10.",
                        detailsUa = "Сенсор макро/глибини GalaxyCore GC02M1 або OmniVision OV02B10.",
                        detailsEn = "Macro/depth sensor GalaxyCore GC02M1 or OmniVision OV02B10."
                    )
                }
            } else {
                return SensorVendorGuess(
                    vendorName = "GalaxyCore / OmniVision 🇨🇳",
                    probableModels = listOf("GalaxyCore GC08A3 (8 Мп)", "OmniVision OV08D (8 Мп)"),
                    confidenceRu = "Фронтальный сенсор бюджетной серии",
                    confidenceUa = "Фронтальний сенсор бюджетної серії",
                    confidenceEn = "Front sensor of budget series",
                    detailsRu = "Фронтальная камера на базе GalaxyCore GC08A3 или OmniVision OV08D.",
                    detailsUa = "Фронтальна камера на базі GalaxyCore GC08A3 або OmniVision OV08D.",
                    detailsEn = "Front camera based on GalaxyCore GC08A3 or OmniVision OV08D."
                )
            }
        }

        // 3. Fallback based on Optical Geometry, Megapixels & Market Frequency (NEVER default to GalaxyCore)

        // 200 MP
        if (megapixels in 180.0..220.0) {
            return SensorVendorGuess(
                vendorName = "Samsung System LSI 🇰🇷",
                probableModels = listOf("Samsung ISOCELL HP2 / HP3 / HPX (200 Мп)"),
                confidenceRu = "Аппаратное совпадение 200-Мп сенсора Samsung ISOCELL",
                confidenceUa = "Апаратний збіг 200-Мп сенсора Samsung ISOCELL",
                confidenceEn = "Hardware match for 200MP Samsung ISOCELL sensor",
                detailsRu = "200-мегапиксельные матрицы для мобильных устройств производятся эксклюзивно компанией Samsung System LSI.",
                detailsUa = "200-мегапіксельні матриці для мобільних пристроїв виготовляються ексклюзивно компанією Samsung System LSI.",
                detailsEn = "200MP smartphone sensors are manufactured exclusively by Samsung System LSI."
            )
        }

        // 108 MP
        if (megapixels in 100.0..115.0) {
            return SensorVendorGuess(
                vendorName = "Samsung System LSI 🇰🇷",
                probableModels = listOf("Samsung ISOCELL HM6 / HM2 (108 Мп)"),
                confidenceRu = "Аппаратное совпадение 108-Мп сенсора Samsung ISOCELL",
                confidenceUa = "Апаратний збіг 108-Мп сенсора Samsung ISOCELL",
                confidenceEn = "Hardware match for 108MP Samsung ISOCELL sensor",
                detailsRu = "108-мегапиксельная матрица Samsung ISOCELL с технологией Nonapixel.",
                detailsUa = "108-мегапіксельна матриця Samsung ISOCELL з технологією Nonapixel.",
                detailsEn = "108MP Samsung ISOCELL sensor with Nonapixel technology."
            )
        }

        // 64 MP
        if (megapixels in 60.0..68.0) {
            return SensorVendorGuess(
                vendorName = "OmniVision / Samsung / Sony",
                probableModels = listOf("OmniVision OV64B (64 Мп)", "Samsung ISOCELL GW3 (64 Мп)", "Sony IMX686 (64 Мп)"),
                confidenceRu = "Аппаратное соответствие 64-Мп матрицы",
                confidenceUa = "Апаратна відповідність 64-Мп матриці",
                confidenceEn = "Hardware match for 64MP sensor format",
                detailsRu = "Матрица 64 Мп от OmniVision Technologies (OV64B) или Samsung ISOCELL (GW3).",
                detailsUa = "Матриця 64 Мп від OmniVision Technologies (OV64B) або Samsung ISOCELL (GW3).",
                detailsEn = "64MP sensor from OmniVision Technologies (OV64B) or Samsung ISOCELL (GW3)."
            )
        }

        // 50 MP (Large Format >= 1/1.6", diagonal > 7.5mm)
        if (megapixels in 48.0..52.0 && diagonal >= 7.5f) {
            return SensorVendorGuess(
                vendorName = "Sony / OmniVision / Samsung",
                probableModels = listOf("Sony LYT-808 / IMX890 (50 Мп)", "OmniVision OV50H (50 Мп)", "Samsung ISOCELL GN3/GN5 (50 Мп)"),
                confidenceRu = "Крупноформатная флагманская матрица 50 Мп (1/1.3\" - 1/1.56\")",
                confidenceUa = "Великоформатна флагманська матриця 50 Мп (1/1.3\" - 1/1.56\")",
                confidenceEn = "Large-format flagship 50MP sensor (1/1.3\" - 1/1.56\")",
                detailsRu = "Крупная матрица с оптическим форматом 1/1.3\" - 1/1.56\" от Sony Semiconductor, OmniVision или Samsung.",
                detailsUa = "Велика матриця з оптичним форматом 1/1.3\" - 1/1.56\" від Sony Semiconductor, OmniVision або Samsung.",
                detailsEn = "Large optical format 1/1.3\" - 1/1.56\" sensor from Sony Semiconductor, OmniVision, or Samsung."
            )
        }

        // 50 MP (Standard / Mainstream Format 1/2.76" - 1/2.88", diagonal < 7.5mm)
        if (megapixels in 48.0..52.0) {
            return SensorVendorGuess(
                vendorName = "Samsung ISOCELL / OmniVision / SmartSens",
                probableModels = listOf("Samsung ISOCELL JN1 (50 Мп)", "OmniVision OV50D (50 Мп)", "SmartSens SC500CS (50 Мп)"),
                confidenceRu = "Массовый оптический формат 50 Мп (1/2.76\")",
                confidenceUa = "Масовий оптичний формат 50 Мп (1/2.76\")",
                confidenceEn = "Mainstream 50MP optical format (1/2.76\")",
                detailsRu = "Матрица 1/2.76\" с субпикселем 0.64 мкм. Наиболее вероятные поставщики: Samsung System LSI (ISOCELL JN1 — лидер мирового рынка), OmniVision (OV50D) или SmartSens (SC500CS).",
                detailsUa = "Матриця 1/2.76\" із субпікселем 0.64 мкм. Найбільш імовірні постачальники: Samsung System LSI (ISOCELL JN1 — лідер світового ринку), OmniVision (OV50D) або SmartSens (SC500CS).",
                detailsEn = "1/2.76\" optical format with 0.64 µm pixel pitch. Most probable vendors: Samsung System LSI (ISOCELL JN1, market leader), OmniVision (OV50D), or SmartSens (SC500CS)."
            )
        }

        // 8 MP (Ultra-Wide / Selfie)
        if (megapixels in 7.0..9.0) {
            return SensorVendorGuess(
                vendorName = "Sony / OmniVision / GalaxyCore",
                probableModels = listOf("Sony IMX355 (8 Мп)", "OmniVision OV08D10 (8 Мп)", "GalaxyCore GC08A3 (8 Мп)"),
                confidenceRu = "Оптический формат 8 Мп (1/4.0\")",
                confidenceUa = "Оптичний формат 8 Мп (1/4.0\")",
                confidenceEn = "Optical format 8MP (1/4.0\")",
                detailsRu = "8-мегапиксельная матрица ультраширокого угла или фронтальной камеры. Производитель: Sony (IMX355), OmniVision (OV08D) или GalaxyCore (GC08A3).",
                detailsUa = "8-мегапіксельна матриця надширокого кута або фронтальної камери. Виробник: Sony (IMX355), OmniVision (OV08D) або GalaxyCore (GC08A3).",
                detailsEn = "8MP sensor for ultra-wide or selfie modules from Sony (IMX355), OmniVision (OV08D), or GalaxyCore (GC08A3)."
            )
        }

        // 2 MP (Macro / Depth) - BALANCED MULTI-VENDOR
        if (megapixels in 1.8..2.5) {
            return SensorVendorGuess(
                vendorName = "OmniVision / GalaxyCore / SmartSens",
                probableModels = listOf("OmniVision OV02B10 (2 Мп)", "GalaxyCore GC02M1 (2 Мп)", "SmartSens SC202CS (2 Мп)"),
                confidenceRu = "Вспомогательный сенсор макро / глубины (1/5.0\")",
                confidenceUa = "Допоміжний сенсор макро / глибини (1/5.0\")",
                confidenceEn = "Auxiliary macro / depth sensor (1/5.0\")",
                detailsRu = "2-мегапиксельный вспомогательный сенсор. Производители чипов: OmniVision Technologies (OV02B10), GalaxyCore (GC02M1) или SmartSens (SC202CS).",
                detailsUa = "2-мегапіксельний допоміжний сенсор. Виробники чипів: OmniVision Technologies (OV02B10), GalaxyCore (GC02M1) або SmartSens (SC202CS).",
                detailsEn = "2MP auxiliary sensor. Manufactured by OmniVision Technologies (OV02B10), GalaxyCore (GC02M1), or SmartSens (SC202CS)."
            )
        }

        // Nearest catalog candidate
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
            vendorName = "Sony / Samsung / OmniVision",
            probableModels = listOf("Camera2 HAL Optical Sensor"),
            confidenceRu = "Анализ геометрии матрицы",
            confidenceUa = "Аналіз геометрії матриці",
            confidenceEn = "Sensor geometry analysis",
            detailsRu = "Определено по физическим оптическим параметрам Camera2 HAL.",
            detailsUa = "Визначено за фізичними оптичними параметрами Camera2 HAL.",
            detailsEn = "Determined by Camera2 HAL optical measurements."
        )
    }

    /**
     * Formats a single comprehensive string listing all verified potential sensor manufacturing
     * companies specific to the device model and manufacturer.
     */
    fun getVerifiedDeviceSensorMakersString(
        manufacturer: String,
        model: String,
        deviceCode: String,
        totalCameras: Int
    ): String {
        val mfr = manufacturer.lowercase().trim()
        val mdl = model.lowercase().trim()

        return when {
            mfr.contains("samsung") || mdl.contains("galaxy") -> {
                "Samsung System LSI 🇰🇷 (ISOCELL), Sony Semiconductor 🇯🇵, OmniVision Technologies 🇺🇸🇨🇳, GalaxyCore 🇨🇳 (макро)"
            }
            mfr.contains("google") || mdl.contains("pixel") -> {
                "Samsung System LSI 🇰🇷 (ISOCELL GN1/GNK), Sony Semiconductor 🇯🇵 (IMX386/IMX858)"
            }
            mfr.contains("xiaomi") || mdl.contains("redmi") || mdl.contains("poco") -> {
                when {
                    mdl.contains("ultra") || mdl.contains("pro+") || mdl.contains("14 pro") || mdl.contains("13 pro") || mdl.contains("15") -> {
                        "Sony Semiconductor 🇯🇵 (LYT-900 / IMX989 / LYT-600), OmniVision Technologies 🇺🇸🇨🇳 (OV50H / Light Hunter 900), Samsung System LSI 🇰🇷 (ISOCELL HP3)"
                    }
                    mdl.contains("note") -> {
                        "Samsung System LSI 🇰🇷 (ISOCELL HP3 / HM6 / JN1), Sony Semiconductor 🇯🇵 (LYT-600 / IMX355), OmniVision Technologies 🇺🇸🇨🇳 (OV02B10 / OV08D), SmartSens 🇨🇳"
                    }
                    Regex("""\b(redmi\s*a[1-7]|poco\s*c\d{1,2}|redmi\s*1[234]c)\b""", RegexOption.IGNORE_CASE).containsMatchIn(mdl) -> {
                        "SmartSens Technology 🇨🇳 (SC500CS), OmniVision Technologies 🇺🇸🇨🇳 (OV50D), GalaxyCore 🇨🇳 (GC50E0 / GC08A3 / GC02M1)"
                    }
                    else -> {
                        "OmniVision Technologies 🇺🇸🇨🇳, Sony Semiconductor 🇯🇵, Samsung System LSI 🇰🇷, SmartSens Technology 🇨🇳, GalaxyCore 🇨🇳"
                    }
                }
            }
            mfr.contains("oneplus") || mfr.contains("oppo") || mfr.contains("realme") -> {
                "Sony Semiconductor 🇯🇵 (LYT-808 / IMX890 / IMX766), OmniVision Technologies 🇺🇸🇨🇳 (OV64B / OV50H), Samsung System LSI 🇰🇷 (JN1), GalaxyCore 🇨🇳"
            }
            mfr.contains("vivo") || mfr.contains("iqoo") -> {
                "Sony Semiconductor 🇯🇵 (LYT-900 / IMX920 / IMX882), Samsung System LSI 🇰🇷 (ISOCELL HP9 / JN1), OmniVision Technologies 🇺🇸🇨🇳"
            }
            mfr.contains("honor") || mfr.contains("huawei") -> {
                "OmniVision Technologies 🇺🇸🇨🇳 (OV50H / OV50D), Sony Semiconductor 🇯🇵 (IMX906 / IMX800), Samsung System LSI 🇰🇷 (HP3 / JN1)"
            }
            mfr.contains("motorola") || mfr.contains("moto") -> {
                "OmniVision Technologies 🇺🇸🇨🇳 (OV50E / OV50D), Samsung System LSI 🇰🇷 (ISOCELL JN1 / HP1), Sony Semiconductor 🇯🇵, SK Hynix 🇰🇷"
            }
            mfr.contains("transsion") || mfr.contains("infinix") || mfr.contains("tecno") -> {
                "Samsung System LSI 🇰🇷 (ISOCELL HM6 / JN1), OmniVision Technologies 🇺🇸🇨🇳 (OV50D), GalaxyCore 🇨🇳 (GC50E0 / GC08A3), SmartSens 🇨🇳"
            }
            else -> {
                "Sony Semiconductor 🇯🇵, Samsung System LSI 🇰🇷 (ISOCELL), OmniVision Technologies 🇺🇸🇨🇳, SmartSens Technology 🇨🇳, GalaxyCore 🇨🇳, SK Hynix 🇰🇷"
            }
        }
    }
}
