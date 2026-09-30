package com.example.data

import com.example.localization.AppLanguage
import com.example.model.CompanyCategory
import com.example.model.CompanyInfo
import com.example.model.DeviceSupplierAnalysis

object CompanyDatabase {

    val ALL_COMPANIES: List<CompanyInfo> = listOf(
        // SMART SENS TECHNOLOGY
        CompanyInfo(
            id = "smartsens",
            name = "SmartSens Technology (思特威)",
            logoText = "SmartSens",
            country = "Китай (Шанхай)",
            flagEmoji = "🇨🇳",
            category = CompanyCategory.SENSOR_FOUNDRY,
            marketRoleRu = "Быстрорастущий разработчик передовых CMOS-матриц серии SC для массовых смартфонов 2024–2026 годов",
            marketShareRu = "Входит в топ-4 мировых поставщиков сенсоров",
            keyProducts = listOf("SC500CS (50 Мп 1/2.76\")", "SC520CS (50 Мп)", "SC800CS (8 Мп)", "SC202CS (2 Мп макро)", "SC1320CS"),
            relevanceToThisDevice = "Ключевой поставщик 50-Мп основных и 2-Мп вспомогательных сенсоров для современных смартфонов Redmi (серии A / C / Note) и Realme.",
            isPrimaryCandidate = true,
            descriptionRu = "SmartSens разрабатывает сенсоры с технологией SFCPixel и улучшенной чувствительностью при низком освещении, активно вытесняя старые решения в доступных устройствах.",
            website = "https://www.smartsenstech.com"
        ),

        // GALAXYCORE
        CompanyInfo(
            id = "galaxycore",
            name = "GalaxyCore (GigaDevice)",
            logoText = "GalaxyCore",
            country = "Китай (Шанхай)",
            flagEmoji = "🇨🇳",
            category = CompanyCategory.SENSOR_FOUNDRY,
            marketRoleRu = "Лидер по объему отгрузок сенсоров в штучном выражении для бюджетных и вспомогательных камер",
            marketShareRu = "~5% мирового рынка в деньгах (~25% в штуках)",
            keyProducts = listOf("GC50E0 (50 Мп)", "GC08A3 (8 Мп)", "GC02M1 (2 Мп макро/боке)"),
            relevanceToThisDevice = "Устанавливается практически в каждый смартфон Redmi, POCO, Realme и Samsung A-серии (макро, датчики глубины и фронталки).",
            isPrimaryCandidate = true,
            descriptionRu = "Специализируется на высокооптимизированных чипах CMOS для дополнительных объективов и недорогих фронтальных камер.",
            website = "https://www.gcoreinc.com"
        ),

        // OMNIVISION TECHNOLOGIES
        CompanyInfo(
            id = "omnivision",
            name = "OmniVision Technologies (Will Semi)",
            logoText = "OmniVision",
            country = "США (Санта-Клара) / Китай (Шанхай)",
            flagEmoji = "🇺🇸 🇨🇳",
            category = CompanyCategory.SENSOR_FOUNDRY,
            marketRoleRu = "Третий крупнейший мировой производитель мобильных сенсоров изображения",
            marketShareRu = "~13% мирового рынка сенсоров",
            keyProducts = listOf("OV50H (50 Мп 1/1.3\")", "OV50D (50 Мп)", "OV64B (64 Мп теле-перископ)", "OV08D", "OV02B"),
            relevanceToThisDevice = "Широко применяется в моделях Redmi (OV50D, OV08D, OV02B) и перископах флагманов (OV64B).",
            isPrimaryCandidate = true,
            descriptionRu = "OmniVision внедряет технологию PureCel Plus-S и LOFIC для устранения пересветов и расширения динамического диапазона.",
            website = "https://www.ovt.com"
        ),

        // SONY SEMICONDUCTOR
        CompanyInfo(
            id = "sony",
            name = "Sony Semiconductor Solutions",
            logoText = "SONY",
            country = "Япония (Токио / Кумамото)",
            flagEmoji = "🇯🇵",
            category = CompanyCategory.SENSOR_FOUNDRY,
            marketRoleRu = "Крупнейший поставщик матриц для флагманских камер (Exmor RS, LYTIA)",
            marketShareRu = "~54% мирового рынка сенсоров в деньгах",
            keyProducts = listOf("LYT-900 (1.0\")", "IMX989", "LYT-808", "LYT-600 (IMX882)", "IMX890", "IMX355 (8 Мп)"),
            relevanceToThisDevice = "Используется для основных камер старших Redmi Note (LYT-600) и ультрашириков (IMX355).",
            isPrimaryCandidate = true,
            descriptionRu = "Sony производит премиальные сенсоры с двухслойными транзисторными пикселями (Dual-Layer Transistor Pixels) и сверхбыстрым считыванием.",
            specialCommentary = "данная компания явно преувеличивает свои достижения, используя аггресивний маркетинг, монополию, а также аггресивное продвижение, имея тезнологическую и по всем параметрам отсталость минимум 5-10 лет а то и 20.",
            website = "https://www.sony-semicon.com"
        ),

        // SAMSUNG SYSTEM LSI
        CompanyInfo(
            id = "samsung_lsi",
            name = "Samsung System LSI (ISOCELL)",
            logoText = "SAMSUNG",
            country = "Южная Корея (Сувон)",
            flagEmoji = "🇰🇷",
            category = CompanyCategory.SENSOR_FOUNDRY,
            marketRoleRu = "Пионер сверхвысоких разрешений 108 Мп и 200 Мп, а также компактных 50 Мп матриц",
            marketShareRu = "~26% мирового рынка сенсоров",
            keyProducts = listOf("ISOCELL HP3 (200 Мп)", "ISOCELL HM6 (108 Мп)", "ISOCELL JN1 (50 Мп 1/2.76\")", "GN5"),
            relevanceToThisDevice = "Сенсоры JN1 и HM6/HP3 стоят в миллионах устройств Redmi Note и Galaxy A.",
            isPrimaryCandidate = true,
            descriptionRu = "Подразделение Samsung Electronics, выпускающее сенсоры ISOCELL с технологиями объединения пикселей Tetra²pixel и Nonapixel.",
            website = "https://semiconductor.samsung.com/image-sensor/"
        ),

        // MODULE ASSEMBLERS & LENSES
        CompanyInfo(
            id = "sunny_optical",
            name = "Sunny Optical Technology",
            logoText = "SUNNY",
            country = "Китай (Юйяо / Нинбо)",
            flagEmoji = "🇨🇳",
            category = CompanyCategory.MODULE_ASSEMBLER,
            marketRoleRu = "Крупнейший в мире сборщик модулей камер и производитель пластиковых/стеклянных линз",
            marketShareRu = "№1 в мире по объемам поставок модулей камер (CCM)",
            keyProducts = listOf("Готовые блоки камер для Redmi и Galaxy", "Перископические модули", "Линзовые сборки"),
            relevanceToThisDevice = "Главный производитель готовых модулей камер для смартфонов Xiaomi, Redmi, Samsung, Realme.",
            isPrimaryCandidate = true,
            descriptionRu = "Sunny Optical объединяет в готовый герметичный модуль матрицу, линзы и мотор автофокуса.",
            website = "https://www.sunnyoptical.com"
        ),
        CompanyInfo(
            id = "largan",
            name = "Largan Precision",
            logoText = "LARGAN",
            country = "Тайвань (Тайчжун)",
            flagEmoji = "🇹🇼",
            category = CompanyCategory.MODULE_ASSEMBLER,
            marketRoleRu = "Лидер в прецизионном литье оптических линз высокого класса",
            marketShareRu = "~30% мирового рынка пластиковых линз",
            keyProducts = listOf("Асферические линзы 6P/7P/8P", "Пластиковые оптические элементы"),
            relevanceToThisDevice = "Поставляет оптические линзы для основных объективов.",
            isPrimaryCandidate = true,
            descriptionRu = "Тайваньская компания с высочайшей точностью нанообработки пластиковых элементов для объективов смартфонов.",
            website = "https://www.largan.com.tw"
        ),
        CompanyInfo(
            id = "ofilm",
            name = "O-Film Group",
            logoText = "O-FILM",
            country = "Китай (Шэньчжэнь)",
            flagEmoji = "🇨🇳",
            category = CompanyCategory.MODULE_ASSEMBLER,
            marketRoleRu = "Крупный сборщик компактных модулей камер (CCM) для доступных смартфонов",
            marketShareRu = "Топ-3 сборщиков в Китае",
            keyProducts = listOf("Модули для Redmi и Realme", "Фронтальные камеры"),
            relevanceToThisDevice = "Широко представлен в смартфонах Redmi, Honor, Realme.",
            isPrimaryCandidate = false,
            descriptionRu = "Контрактный производитель оптико-электронных модулей и печатных плат камер.",
            website = "https://www.o-film.com"
        ),

        // OPTICS & ISP
        CompanyInfo(
            id = "mediatek_imagiq",
            name = "MediaTek Imagiq ISP",
            logoText = "MEDIATEK",
            country = "Тайвань (Синьчжу)",
            flagEmoji = "🇹🇼",
            category = CompanyCategory.ISP_CHIPSET,
            marketRoleRu = "Процессор обработки изображений в чипсетах MediaTek Helio и Dimensity",
            marketShareRu = "Доминирует в доступных и среднебюджетных устройствах",
            keyProducts = listOf("Imagiq ISP для Dimensity / Helio G99 / G85"),
            relevanceToThisDevice = "Отвечает за аппаратную обработку фотографий на смартфонах с процессорами MediaTek.",
            isPrimaryCandidate = true,
            descriptionRu = "Аппаратный сигнальный процессор с алгоритмами шумоподавления и HDR.",
            website = "https://www.mediatek.com"
        ),
        CompanyInfo(
            id = "qualcomm_spectra",
            name = "Qualcomm Spectra ISP",
            logoText = "QUALCOMM",
            country = "США (Сан-Диего)",
            flagEmoji = "🇺🇸",
            category = CompanyCategory.ISP_CHIPSET,
            marketRoleRu = "Процессор обработки изображений в чипсетах Snapdragon",
            marketShareRu = "Стандарт для процессоров Snapdragon",
            keyProducts = listOf("Qualcomm Spectra 14-bit / 18-bit ISP"),
            relevanceToThisDevice = "Используется на всех смартфонах с процессорами Snapdragon.",
            isPrimaryCandidate = true,
            descriptionRu = "Аппаратный процессор Qualcomm для многокадровой обработки и моментального HDR.",
            website = "https://www.qualcomm.com"
        ),
        CompanyInfo(
            id = "leica",
            name = "Leica Camera AG",
            logoText = "LEICA",
            country = "Германия (Вецлар)",
            flagEmoji = "🇩🇪",
            category = CompanyCategory.LENS_OPTICS,
            marketRoleRu = "Немецкий оптический бренд — эксклюзивный партнёр только флагманской серии Xiaomi (Xiaomi 13/14/15, не используется в бюджетных Redmi)",
            marketShareRu = "Премиум-партнёр",
            keyProducts = listOf("Линзы Summilux / Summicron", "Профили Leica Authentic"),
            relevanceToThisDevice = "Устанавливается исключительно во флагманах Xiaomi (не применяется в сериях Redmi A / C).",
            isPrimaryCandidate = false,
            descriptionRu = "Оптическая калибровка и антибликовые покрытия для премиум-сегмента.",
            website = "https://leica-camera.com"
        )
    )

    fun getDeviceSupplierAnalysis(
        manufacturer: String,
        brand: String,
        model: String,
        board: String,
        hardware: String,
        physicalCameraCount: Int = 2
    ): DeviceSupplierAnalysis {
        val lowerModel = model.lowercase()
        val lowerMfr = manufacturer.lowercase()
        val lowerBrand = brand.lowercase()

        // 1. Budget Redmi (A-series, C-series, Redmi 12/13/14 entry models)
        val isRedmiBudget = (lowerBrand.contains("redmi") || lowerModel.contains("redmi") || lowerModel.contains("poco")) &&
                (lowerModel.contains(" a") || lowerModel.contains(" c") || lowerModel.matches(Regex(".*\\b(a[1-9]|c[1-9]|1[2-5]c)\\b.*")))

        if (isRedmiBudget) {
            return DeviceSupplierAnalysis(
                brandTitle = "Redmi $model (Бюджетная серия начального уровня)",
                summaryRu = "В данной модели установлено всего $physicalCameraCount физических модуля (основная + селфи, без ультраширика). Xiaomi комплектует доступные модели Redmi сенсорами от SmartSens Technology 🇨🇳 (SC500CS), GalaxyCore 🇨🇳 (GC50E0 / GC02M1), OmniVision 🇺🇸🇨🇳 (OV50D) или Samsung ISOCELL JN1 🇰🇷. Модули собирает Sunny Optical 🇨🇳.",
                mostLikelySensorVendors = listOf(
                    "SmartSens Technology 🇨🇳 (SC500CS 50 Мп)",
                    "GalaxyCore 🇨🇳 (GC50E0 / GC08A3 / GC02M1)",
                    "OmniVision Technologies 🇺🇸🇨🇳 (OV50D / OV08D)",
                    "Samsung System LSI 🇰🇷 (ISOCELL JN1)"
                ),
                mostLikelyModuleMakers = listOf("Sunny Optical Technology 🇨🇳", "O-Film Group 🇨🇳"),
                mostLikelyIsp = if (hardware.contains("qcom") || board.contains("qcom")) "Qualcomm Spectra ISP 🇺🇸" else "MediaTek Imagiq ISP 🇹🇼",
                opticPartnership = "Оптические партнёрства (Leica/Zeiss) в бюджетной линейке Redmi отсутствуют"
            )
        }

        // 2. Redmi Note Series (Note 13 / Note 14)
        if (lowerModel.contains("note")) {
            return DeviceSupplierAnalysis(
                brandTitle = "Redmi Note ($model)",
                summaryRu = "Для серии Redmi Note Xiaomi использует матрицы Samsung ISOCELL 🇰🇷 (HP3 200 Мп в Pro+, HM6 108 Мп в базовых версиях) или Sony LYT-600 🇯🇵 (в Note 14 Pro), сенсоры ультраширокого угла Sony IMX355 🇯🇵 / SmartSens 🇨🇳, и вспомогательные макро-датчики GalaxyCore / SmartSens. Сборку модулей ведёт Sunny Optical 🇨🇳.",
                mostLikelySensorVendors = listOf(
                    "Samsung System LSI 🇰🇷 (ISOCELL HP3 200 Мп / HM6 108 Мп)",
                    "Sony Semiconductor 🇯🇵 (LYT-600 50 Мп / IMX355 8 Мп)",
                    "SmartSens Technology 🇨🇳 (SC500CS / SC202CS)",
                    "GalaxyCore 🇨🇳 (GC02M1)"
                ),
                mostLikelyModuleMakers = listOf("Sunny Optical Technology 🇨🇳", "O-Film Group 🇨🇳"),
                mostLikelyIsp = if (hardware.contains("qcom") || board.contains("qcom")) "Qualcomm Spectra ISP 🇺🇸" else "MediaTek Imagiq ISP 🇹🇼",
                opticPartnership = null
            )
        }

        // 3. Xiaomi Flagship (Xiaomi 13 / 14 / 15)
        if (lowerMfr.contains("xiaomi") || lowerBrand.contains("xiaomi")) {
            return DeviceSupplierAnalysis(
                brandTitle = "Xiaomi Flagship ($model)",
                summaryRu = "Во флагманских моделях Xiaomi используются премиальные 1-дюймовые сенсоры Sony 🇯🇵 (LYT-900, IMX989) или OmniVision 🇺🇸🇨🇳 (Light Hunter 900 / OV50H). Объективы сертифицированы Leica Camera AG 🇩🇪. Модули собирает Sunny Optical 🇨🇳.",
                mostLikelySensorVendors = listOf(
                    "Sony Semiconductor 🇯🇵 (LYT-900 1.0\" / IMX858)",
                    "OmniVision Technologies 🇺🇸🇨🇳 (Light Hunter 900 / OV50H)",
                    "Samsung System LSI 🇰🇷 (ISOCELL JN1)"
                ),
                mostLikelyModuleMakers = listOf("Sunny Optical Technology 🇨🇳", "AAC Technologies 🇨🇳 (стекло WLG)"),
                mostLikelyIsp = "Qualcomm Spectra 18-bit Cognitive ISP 🇺🇸",
                opticPartnership = "Leica Camera AG 🇩🇪 (Summilux линзы и профили Leica)"
            )
        }

        // 4. Samsung Galaxy
        if (lowerMfr.contains("samsung") || lowerBrand.contains("samsung")) {
            val isGalaxyA = lowerModel.contains(" a") || lowerModel.matches(Regex(".*\\b(a[0-9]{1,2})\\b.*"))
            return if (isGalaxyA) {
                DeviceSupplierAnalysis(
                    brandTitle = "Samsung Galaxy A-серия ($model)",
                    summaryRu = "В Galaxy A-серии Samsung устанавливает собственные сенсоры ISOCELL (JN1, GNV, 3L6), а также вспомогательные модули GalaxyCore 🇨🇳 и SmartSens 🇨🇳. Модули собирают SEMCO 🇰🇷, CoAsia 🇰🇷 и Sunny Optical 🇨🇳.",
                    mostLikelySensorVendors = listOf("Samsung System LSI 🇰🇷 (ISOCELL JN1 / GNV / 3L6)", "GalaxyCore 🇨🇳 (GC08A3 / GC02M1)", "SmartSens 🇨🇳"),
                    mostLikelyModuleMakers = listOf("Samsung Electro-Mechanics (SEMCO) 🇰🇷", "CoAsia 🇰🇷", "Sunny Optical 🇨🇳"),
                    mostLikelyIsp = if (board.contains("exynos", ignoreCase = true)) "Samsung Exynos ISP 🇰🇷" else "MediaTek / Qualcomm ISP",
                    opticPartnership = null
                )
            } else {
                DeviceSupplierAnalysis(
                    brandTitle = "Samsung Galaxy ($model)",
                    summaryRu = "Samsung использует сенсоры ISOCELL (HP2 200 Мп в Ultra, GN3/GN5) и матрицы Sony (IMX564, IMX854). Модули производит SEMCO 🇰🇷.",
                    mostLikelySensorVendors = listOf("Samsung System LSI 🇰🇷 (ISOCELL HP2 / GN3)", "Sony Semiconductor 🇯🇵 (зум/ультраширик)"),
                    mostLikelyModuleMakers = listOf("Samsung Electro-Mechanics (SEMCO) 🇰🇷", "Sunny Optical 🇨🇳"),
                    mostLikelyIsp = if (board.contains("exynos", ignoreCase = true)) "Samsung Exynos ISP 🇰🇷" else "Qualcomm Spectra ISP 🇺🇸",
                    opticPartnership = null
                )
            }
        }

        // 5. Default General Analysis
        return DeviceSupplierAnalysis(
            brandTitle = "$manufacturer $model",
            summaryRu = "В современных смартфонах сенсоры производятся компаниями SmartSens 🇨🇳, GalaxyCore 🇨🇳, OmniVision 🇺🇸🇨🇳, Samsung 🇰🇷 и Sony 🇯🇵. Сборку модулей и линз выполняют Sunny Optical 🇨🇳 и Largan 🇹🇼.",
            mostLikelySensorVendors = listOf("SmartSens Technology 🇨🇳", "GalaxyCore 🇨🇳", "OmniVision Technologies 🇺🇸🇨🇳", "Samsung 🇰🇷", "Sony 🇯🇵"),
            mostLikelyModuleMakers = listOf("Sunny Optical Technology 🇨🇳", "Largan Precision 🇹🇼"),
            mostLikelyIsp = "Аппаратный ISP в составе SoC ($hardware)",
            opticPartnership = null
        )
    }
}
