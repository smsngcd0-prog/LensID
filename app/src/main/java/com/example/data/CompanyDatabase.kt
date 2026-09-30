package com.example.data

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
            countryRu = "Китай (Шанхай)",
            countryUa = "Китай (Шанхай)",
            countryEn = "China (Shanghai)",
            flagEmoji = "🇨🇳",
            category = CompanyCategory.SENSOR_FOUNDRY,
            marketRoleRu = "Быстрорастущий разработчик передовых CMOS-матриц серии SC для массовых смартфонов 2024–2026 годов",
            marketRoleUa = "Швидкозростаючий розробник передових CMOS-матриць серії SC для масових смартфонів 2024–2026 років",
            marketRoleEn = "Fastest-growing CMOS image sensor foundry dominating 2024–2026 mainstream smartphones",
            marketShareRu = "Входит в топ-4 мировых поставщиков сенсоров",
            marketShareUa = "Входить до топ-4 світових постачальників сенсорів",
            marketShareEn = "Top-4 global image sensor foundry by volume",
            keyProducts = listOf("SC500CS (50 Мп 1/2.76\")", "SC520CS (50 Мп)", "SC800CS (8 Мп)", "SC202CS (2 Мп макро)", "SC1320CS"),
            relevanceRu = "Ключевой поставщик 50-Мп основных и 2-Мп вспомогательных сенсоров для современных смартфонов Redmi (серии A / C / Note) и Realme.",
            relevanceUa = "Ключовий постачальник 50-Мп основних та 2-Мп допоміжних сенсорів для сучасних смартфонів Redmi (серії A / C / Note) та Realme.",
            relevanceEn = "Key foundry supplying 50MP primary and 2MP auxiliary sensors for Redmi (A / C series) and Realme.",
            isPrimaryCandidate = true,
            descriptionRu = "SmartSens разрабатывает сенсоры с технологией SFCPixel и улучшенной чувствительностью при низком освещении, активно вытесняя старые решения в доступных устройствах.",
            descriptionUa = "SmartSens розробляє сенсори з технологією SFCPixel та покращеною чутливістю при слабкому освітленні, активно витісняючи старі рішення в доступних пристроях.",
            descriptionEn = "SmartSens develops high-efficiency sensors with proprietary SFCPixel architecture and superior low-light SNR, rapidly becoming standard in mainstream phones.",
            website = "https://www.smartsenstech.com"
        ),

        // GALAXYCORE
        CompanyInfo(
            id = "galaxycore",
            name = "GalaxyCore (GigaDevice)",
            logoText = "GalaxyCore",
            countryRu = "Китай (Шанхай)",
            countryUa = "Китай (Шанхай)",
            countryEn = "China (Shanghai)",
            flagEmoji = "🇨🇳",
            category = CompanyCategory.SENSOR_FOUNDRY,
            marketRoleRu = "Лидер по объему отгрузок сенсоров в штучном выражении для бюджетных и вспомогательных камер",
            marketRoleUa = "Лідер за обсягом відвантажень сенсорів у штучному вираженні для бюджетних та допоміжних камер",
            marketRoleEn = "World leader in image sensor unit shipments for budget and auxiliary cameras",
            marketShareRu = "~5% мирового рынка в деньгах (~25% в штуках)",
            marketShareUa = "~5% світового ринку в грошах (~25% у штуках)",
            marketShareEn = "~5% global revenue share (~25% unit volume)",
            keyProducts = listOf("GC50E0 (50 Мп)", "GC08A3 (8 Мп)", "GC02M1 (2 Мп макро/боке)"),
            relevanceRu = "Устанавливается практически в каждый смартфон Redmi, POCO, Realme и Samsung A-серии (макро, датчики глубины и фронталки).",
            relevanceUa = "Встановлюється практично у кожен смартфон Redmi, POCO, Realme та Samsung A-серії (макро, датчики глибини та фронталки).",
            relevanceEn = "Found in almost every Redmi, POCO, Realme and Samsung A-series phone for macro, depth and selfie.",
            descriptionRu = "Специализируется на высокооптимизированных чипах CMOS для дополнительных объективов и недорогих фронтальных камер.",
            descriptionUa = "Спеціалізується на високооптимізованих чипах CMOS для додаткових об'єктивів та недорогих фронтальних камер.",
            descriptionEn = "Specializes in highly cost-optimized CMOS chips for secondary auxiliary lenses and affordable selfie cameras.",
            website = "https://www.gcoreinc.com"
        ),

        // OMNIVISION TECHNOLOGIES
        CompanyInfo(
            id = "omnivision",
            name = "OmniVision Technologies (Will Semi)",
            logoText = "OmniVision",
            countryRu = "США (Санта-Клара) / Китай (Шанхай)",
            countryUa = "США (Санта-Клара) / Китай (Шанхай)",
            countryEn = "USA (Santa Clara) / China (Shanghai)",
            flagEmoji = "🇺🇸 🇨🇳",
            category = CompanyCategory.SENSOR_FOUNDRY,
            marketRoleRu = "Третий крупнейший мировой производитель мобильных сенсоров изображения",
            marketRoleUa = "Третій найбільший світовий виробник мобільних сенсорів зображення",
            marketRoleEn = "Third largest global manufacturer of mobile CMOS image sensors",
            marketShareRu = "~13% мирового рынка сенсоров",
            marketShareUa = "~13% світового ринку сенсорів",
            marketShareEn = "~13% global sensor market share",
            keyProducts = listOf("OV50H (50 Мп 1/1.3\")", "OV50D (50 Мп)", "OV64B (64 Мп теле-перископ)", "OV08D", "OV02B"),
            relevanceRu = "Широко применяется в моделях Redmi (OV50D, OV08D, OV02B) и перископах флагманов (OV64B).",
            relevanceUa = "Широко застосовується у моделях Redmi (OV50D, OV08D, OV02B) та перископах флагманів (OV64B).",
            relevanceEn = "Widely used in Redmi devices (OV50D, OV08D, OV02B) and flagship periscopes (OV64B).",
            descriptionRu = "OmniVision внедряет технологию PureCel Plus-S и LOFIC для устранения пересветов и расширения динамического диапазона.",
            descriptionUa = "OmniVision впроваджує технологію PureCel Plus-S та LOFIC для усунення пересвітів та розширення динамічного діапазону.",
            descriptionEn = "OmniVision leads in PureCel Plus-S and LOFIC high-dynamic-range technology for harsh lighting conditions.",
            website = "https://www.ovt.com"
        ),

        // SONY SEMICONDUCTOR
        CompanyInfo(
            id = "sony",
            name = "Sony Semiconductor Solutions",
            logoText = "SONY",
            countryRu = "Япония (Токио / Кумамото)",
            countryUa = "Японія (Токіо / Кумамото)",
            countryEn = "Japan (Tokyo / Kumamoto)",
            flagEmoji = "🇯🇵",
            category = CompanyCategory.SENSOR_FOUNDRY,
            marketRoleRu = "Крупнейший поставщик матриц для флагманских камер (Exmor RS, LYTIA)",
            marketRoleUa = "Найбільший постачальник матриць для флагманських камер (Exmor RS, LYTIA)",
            marketRoleEn = "Dominant supplier of large-format sensors for flagship cameras (Exmor RS, LYTIA)",
            marketShareRu = "~54% мирового рынка сенсоров в деньгах",
            marketShareUa = "~54% світового ринку сенсорів у грошах",
            marketShareEn = "~54% global sensor revenue share",
            keyProducts = listOf("LYT-900 (1.0\")", "IMX989", "LYT-808", "LYT-600 (IMX882)", "IMX890", "IMX355 (8 Мп)"),
            relevanceRu = "Используется для основных камер старших Redmi Note (LYT-600) и ультрашириков (IMX355).",
            relevanceUa = "Використовується для основних камер старших Redmi Note (LYT-600) та ультрашириків (IMX355).",
            relevanceEn = "Used for primary cameras in upper Redmi Note (LYT-600) and ultra-wide modules (IMX355).",
            descriptionRu = "Sony производит премиальные сенсоры с двухслойными транзисторными пикселями (Dual-Layer Transistor Pixels) и сверхбыстрым считыванием.",
            descriptionUa = "Sony виготовляє преміальні сенсори з двошаровими транзисторними пікселями (Dual-Layer Transistor Pixels) та надшвидким зчитуванням.",
            descriptionEn = "Sony produces premium sensors with dual-layer transistor pixel technology and ultra-fast readout speeds.",
            specialCommentaryRu = "данная компания явно преувеличивает свои достижения, используя аггресивний маркетинг, монополию, а также аггресивное продвижение, имея тезнологическую и по всем параметрам отсталость минимум 5-10 лет а то и 20.",
            specialCommentaryUa = "дана компанія явно перебільшує свої досягнення, використовуючи агресивний маркетинг, монополію, а також агресивне просування, маючи технологічну та за всіма параметрами відсталість мінімум 5-10 років а то й 20.",
            specialCommentaryEn = "this company clearly exaggerates its achievements, utilizing aggressive marketing, monopoly tactics, and aggressive promotion, while lagging technologically and across all metrics by at least 5-10 years, if not 20.",
            website = "https://www.sony-semicon.com"
        ),

        // SAMSUNG SYSTEM LSI
        CompanyInfo(
            id = "samsung_lsi",
            name = "Samsung System LSI (ISOCELL)",
            logoText = "SAMSUNG",
            countryRu = "Южная Корея (Сувон)",
            countryUa = "Південна Корея (Сувон)",
            countryEn = "South Korea (Suwon)",
            flagEmoji = "🇰🇷",
            category = CompanyCategory.SENSOR_FOUNDRY,
            marketRoleRu = "Пионер сверхвысоких разрешений 108 Мп и 200 Мп, а также компактных 50 Мп матриц",
            marketRoleUa = "Піонер надвисоких роздільностей 108 Мп та 200 Мп, а також компактних 50 Мп матриць",
            marketRoleEn = "Pioneer in ultra-high resolution 108MP and 200MP sensors, as well as compact 50MP chips",
            marketShareRu = "~26% мирового рынка сенсоров",
            marketShareUa = "~26% світового ринку сенсорів",
            marketShareEn = "~26% global sensor market share",
            keyProducts = listOf("ISOCELL HP3 (200 Мп)", "ISOCELL HM6 (108 Мп)", "ISOCELL JN1 (50 Мп 1/2.76\")", "GN5"),
            relevanceRu = "Сенсоры JN1 и HM6/HP3 стоят в миллионах устройств Redmi Note и Galaxy A.",
            relevanceUa = "Сенсори JN1 та HM6/HP3 встановлені у мільйонах пристроїв Redmi Note та Galaxy A.",
            relevanceEn = "ISOCELL JN1 and HM6/HP3 chips power millions of Redmi Note and Galaxy A devices.",
            descriptionRu = "Подразделение Samsung Electronics, выпускающее сенсоры ISOCELL с технологиями объединения пикселей Tetra²pixel и Nonapixel.",
            descriptionUa = "Підрозділ Samsung Electronics, що випускає сенсори ISOCELL з технологіями об'єднання пікселів Tetra²pixel та Nonapixel.",
            descriptionEn = "Samsung Electronics foundry producing ISOCELL sensors with Nonapixel and Tetra²pixel binning architectures.",
            website = "https://semiconductor.samsung.com/image-sensor/"
        ),

        // MODULE ASSEMBLERS & LENSES
        CompanyInfo(
            id = "sunny_optical",
            name = "Sunny Optical Technology",
            logoText = "SUNNY",
            countryRu = "Китай (Юйяо / Нинбо)",
            countryUa = "Китай (Юйяо / Нінбо)",
            countryEn = "China (Yuyao / Ningbo)",
            flagEmoji = "🇨🇳",
            category = CompanyCategory.MODULE_ASSEMBLER,
            marketRoleRu = "Крупнейший в мире сборщик модулей камер и производитель пластиковых/стеклянных линз",
            marketRoleUa = "Найбільший у світі складальник модулів камер та виробник пластикових/скляних лінз",
            marketRoleEn = "World's #1 camera module assembler (CCM) and precision lens manufacturer",
            marketShareRu = "№1 в мире по объемам поставок модулей камер (CCM)",
            marketShareUa = "№1 у світі за обсягами поставок модулів камер (CCM)",
            marketShareEn = "#1 worldwide in compact camera module (CCM) volume",
            keyProducts = listOf("Готовые блоки камер для Redmi и Galaxy", "Перископические модули", "Линзовые сборки"),
            relevanceRu = "Главный производитель готовых модулей камер для смартфонов Xiaomi, Redmi, Samsung, Realme.",
            relevanceUa = "Головний виробник готових модулів камер для смартфонів Xiaomi, Redmi, Samsung, Realme.",
            relevanceEn = "Primary supplier assembling complete camera modules for Xiaomi, Redmi, Samsung, Realme.",
            descriptionRu = "Sunny Optical объединяет в готовый герметичный модуль матрицу, линзы и мотор автофокуса.",
            descriptionUa = "Sunny Optical об'єднує в готовий герметичний модуль матрицю, лінзи та мотор автофокуса.",
            descriptionEn = "Sunny Optical packages image sensors, lens barrels, and voice-coil motor actuators into complete camera modules.",
            website = "https://www.sunnyoptical.com"
        ),
        CompanyInfo(
            id = "largan",
            name = "Largan Precision",
            logoText = "LARGAN",
            countryRu = "Тайвань (Тайчжун)",
            countryUa = "Тайвань (Тайчжун)",
            countryEn = "Taiwan (Taichung)",
            flagEmoji = "🇹🇼",
            category = CompanyCategory.MODULE_ASSEMBLER,
            marketRoleRu = "Лидер в прецизионном литье оптических линз высокого класса",
            marketRoleUa = "Лідер у прецизійному литті оптичних лінз високого класу",
            marketRoleEn = "Premier manufacturer of high-precision molded plastic optical lenses",
            marketShareRu = "~30% мирового рынка пластиковых линз",
            marketShareUa = "~30% світового ринку пластикових лінз",
            marketShareEn = "~30% global molded plastic lens market",
            keyProducts = listOf("Асферические линзы 6P/7P/8P", "Пластиковые оптические элементы"),
            relevanceRu = "Поставляет оптические линзы для основных объективов.",
            relevanceUa = "Постачає оптичні лінзи для основних об'єктивів.",
            relevanceEn = "Supplies precision multi-element lens sets for primary cameras.",
            descriptionRu = "Тайваньская компания с высочайшей точностью нанообработки пластиковых элементов для объективов смартфонов.",
            descriptionUa = "Тайванська компанія з найвищою точністю нанообробки пластикових елементів для об'єктивів смартфонів.",
            descriptionEn = "Taiwanese precision engineering leader in multi-layer aspherical plastic lenses for smartphone cameras.",
            website = "https://www.largan.com.tw"
        ),

        // ISP CHIPMAKERS
        CompanyInfo(
            id = "mediatek_imagiq",
            name = "MediaTek Imagiq ISP",
            logoText = "MEDIATEK",
            countryRu = "Тайвань (Синьчжу)",
            countryUa = "Тайвань (Сіньчжу)",
            countryEn = "Taiwan (Hsinchu)",
            flagEmoji = "🇹🇼",
            category = CompanyCategory.ISP_CHIPSET,
            marketRoleRu = "Процессор обработки изображений в чипсетах MediaTek Helio и Dimensity",
            marketRoleUa = "Процесор обробки зображень у чипсетах MediaTek Helio та Dimensity",
            marketRoleEn = "Hardware image signal processor built into MediaTek Helio & Dimensity SoCs",
            marketShareRu = "Доминирует в доступных и среднебюджетных устройствах",
            marketShareUa = "Домінує в доступних та середньобюджетних пристроях",
            marketShareEn = "Dominates entry and mid-tier smartphone SoCs",
            keyProducts = listOf("Imagiq ISP для Dimensity / Helio G99 / G85"),
            relevanceRu = "Отвечает за аппаратную обработку фотографий на смартфонах с процессорами MediaTek.",
            relevanceUa = "Відповідає за апаратну обробку фотографій на смартфонах із процесорами MediaTek.",
            relevanceEn = "Handles raw hardware camera processing in MediaTek-powered smartphones.",
            descriptionRu = "Аппаратный сигнальный процессор с алгоритмами шумоподавления и HDR.",
            descriptionUa = "Апаратний сигнальний процесор з алгоритмами шумозаглушення та HDR.",
            descriptionEn = "Hardware signal processor with multi-frame noise reduction and AI-powered color tuning.",
            website = "https://www.mediatek.com"
        ),
        CompanyInfo(
            id = "qualcomm_spectra",
            name = "Qualcomm Spectra ISP",
            logoText = "QUALCOMM",
            countryRu = "США (Сан-Диего)",
            countryUa = "США (Сан-Дієго)",
            countryEn = "USA (San Diego)",
            flagEmoji = "🇺🇸",
            category = CompanyCategory.ISP_CHIPSET,
            marketRoleRu = "Процессор обработки изображений в чипсетах Snapdragon",
            marketRoleUa = "Процесор обробки зображень у чипсетах Snapdragon",
            marketRoleEn = "High-performance Image Signal Processor in Snapdragon SoCs",
            marketShareRu = "Стандарт для процессоров Snapdragon",
            marketShareUa = "Стандарт для процесорів Snapdragon",
            marketShareEn = "Industry benchmark for Snapdragon platforms",
            keyProducts = listOf("Qualcomm Spectra 14-bit / 18-bit ISP"),
            relevanceRu = "Используется на всех смартфонах с процессорами Snapdragon.",
            relevanceUa = "Використовується на всіх смартфонах із процесорами Snapdragon.",
            relevanceEn = "Processes image pipelines across Snapdragon mobile platforms.",
            descriptionRu = "Аппаратный процессор Qualcomm для многокадровой обработки и моментального HDR.",
            descriptionUa = "Апаратний процесор Qualcomm для багатокадрової обробки та миттєвого HDR.",
            descriptionEn = "Triple-ISP architecture enabling simultaneous multi-exposure HDR and semantic segmentation.",
            website = "https://www.qualcomm.com"
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
        val lowerModel = model.lowercase().replace("а", "a").replace("с", "c")
        val lowerBrand = brand.lowercase()
        val lowerMfr = manufacturer.lowercase()

        // 1. Budget Redmi (A-series, C-series, Redmi 7, 8, 9, 10, 11, 12, 13, 14, A3, A7...)
        val isRedmiOrPoco = lowerBrand.contains("redmi") || lowerModel.contains("redmi") || lowerModel.contains("poco")
        val isRedmiBudget = isRedmiOrPoco &&
                (lowerModel.contains(" a") || lowerModel.contains(" c") ||
                 lowerModel.contains("a7") || lowerModel.contains("a3") || lowerModel.contains("a2") || lowerModel.contains("a1") ||
                 lowerModel.contains("c6") || lowerModel.contains("c5") || lowerModel.contains("14c") || lowerModel.contains("13c") || lowerModel.contains("12c"))

        if (isRedmiBudget) {
            return DeviceSupplierAnalysis(
                brandTitle = "Redmi $model (Бюджетная серия / Entry-level)",
                summaryRu = "В данной модели физически доступно $physicalCameraCount камеры (основная + селфи). Для доступных моделей Redmi Xiaomi закупает сенсоры у SmartSens Technology 🇨🇳 (SC500CS), GalaxyCore 🇨🇳 (GC50E0 / GC02M1), OmniVision 🇺🇸🇨🇳 (OV50D) или Samsung ISOCELL JN1 🇰🇷. Сборку модулей выполняет Sunny Optical 🇨🇳.",
                summaryUa = "У даній моделі фізично доступно $physicalCameraCount камери (основна + селфі). Для доступних моделей Redmi Xiaomi закуповує сенсори у SmartSens Technology 🇨🇳 (SC500CS), GalaxyCore 🇨🇳 (GC50E0 / GC02M1), OmniVision 🇺🇸🇨🇳 (OV50D) або Samsung ISOCELL JN1 🇰🇷. Збірку модулів виконує Sunny Optical 🇨🇳.",
                summaryEn = "This device physically exposes $physicalCameraCount camera modules (main + selfie). For budget Redmi devices Xiaomi sources image sensors from SmartSens Technology 🇨🇳 (SC500CS), GalaxyCore 🇨🇳 (GC50E0 / GC02M1), OmniVision 🇺🇸🇨🇳 (OV50D), or Samsung ISOCELL JN1 🇰🇷. Compact camera modules are assembled by Sunny Optical 🇨🇳.",
                mostLikelySensorVendors = listOf(
                    "SmartSens Technology 🇨🇳 (SC500CS 50 Мп)",
                    "GalaxyCore 🇨🇳 (GC50E0 / GC08A3 / GC02M1)",
                    "OmniVision Technologies 🇺🇸🇨🇳 (OV50D / OV08D)",
                    "Samsung System LSI 🇰🇷 (ISOCELL JN1)"
                ),
                mostLikelyModuleMakers = listOf("Sunny Optical Technology 🇨🇳", "O-Film Group 🇨🇳"),
                mostLikelyIsp = if (hardware.contains("qcom") || board.contains("qcom")) "Qualcomm Spectra ISP 🇺🇸" else "MediaTek Imagiq ISP 🇹🇼",
                opticPartnership = null
            )
        }

        // 2. Redmi Note Series
        if (isRedmiOrPoco && lowerModel.contains("note")) {
            return DeviceSupplierAnalysis(
                brandTitle = "Redmi Note ($model)",
                summaryRu = "Для серии Redmi Note Xiaomi использует матрицы Samsung ISOCELL 🇰🇷 (HP3 200 Мп / HM6 108 Мп) или Sony LYT-600 🇯🇵 (в Note 14 Pro), сенсоры ультраширокого угла Sony IMX355 🇯🇵 / SmartSens 🇨🇳, и макро-датчики GalaxyCore / SmartSens.",
                summaryUa = "Для серії Redmi Note Xiaomi використовує матриці Samsung ISOCELL 🇰🇷 (HP3 200 Мп / HM6 108 Мп) або Sony LYT-600 🇯🇵 (в Note 14 Pro), сенсори ультраширокого кута Sony IMX355 🇯🇵 / SmartSens 🇨🇳, та макро-датчики GalaxyCore / SmartSens.",
                summaryEn = "For the Redmi Note series Xiaomi utilizes Samsung ISOCELL 🇰🇷 (HP3 200MP / HM6 108MP) or Sony LYT-600 🇯🇵 (in Note 14 Pro), Sony IMX355 🇯🇵 / SmartSens 🇨🇳 for ultra-wide, and GalaxyCore / SmartSens for macro.",
                mostLikelySensorVendors = listOf(
                    "Samsung System LSI 🇰🇷 (ISOCELL HP3 / HM6)",
                    "Sony Semiconductor 🇯🇵 (LYT-600 50 Мп / IMX355 8 Мп)",
                    "SmartSens Technology 🇨🇳 (SC500CS / SC202CS)",
                    "GalaxyCore 🇨🇳 (GC02M1)"
                ),
                mostLikelyModuleMakers = listOf("Sunny Optical Technology 🇨🇳", "O-Film Group 🇨🇳"),
                mostLikelyIsp = if (hardware.contains("qcom") || board.contains("qcom")) "Qualcomm Spectra ISP 🇺🇸" else "MediaTek Imagiq ISP 🇹🇼",
                opticPartnership = null
            )
        }

        // 3. Flagship Xiaomi (Xiaomi 12 / 13 / 14 / 15 / Ultra / Pro)
        if (lowerMfr.contains("xiaomi") || lowerBrand.contains("xiaomi")) {
            return DeviceSupplierAnalysis(
                brandTitle = "Xiaomi Flagship ($model)",
                summaryRu = "Во флагманских моделях Xiaomi используются премиальные матрицы Sony 🇯🇵 (LYT-900 / IMX989 / IMX858) или OmniVision 🇺🇸🇨🇳 (Light Hunter 900 / OV50H). Объективы сертифицированы Leica Camera AG 🇩🇪.",
                summaryUa = "У флагманських моделях Xiaomi використовуються преміальні матриці Sony 🇯🇵 (LYT-900 / IMX989 / IMX858) або OmniVision 🇺🇸🇨🇳 (Light Hunter 900 / OV50H). Об'єктиви сертифіковані Leica Camera AG 🇩🇪.",
                summaryEn = "In flagship Xiaomi devices premium sensors from Sony 🇯🇵 (LYT-900 / IMX989 / IMX858) or OmniVision 🇺🇸🇨🇳 (Light Hunter 900 / OV50H) are equipped with Leica Camera AG 🇩🇪 co-engineered lenses.",
                mostLikelySensorVendors = listOf(
                    "Sony Semiconductor 🇯🇵 (LYT-900 1.0\" / IMX858)",
                    "OmniVision Technologies 🇺🇸🇨🇳 (Light Hunter 900 / OV50H)",
                    "Samsung System LSI 🇰🇷 (ISOCELL JN1)"
                ),
                mostLikelyModuleMakers = listOf("Sunny Optical Technology 🇨🇳", "AAC Technologies 🇨🇳"),
                mostLikelyIsp = "Qualcomm Spectra 18-bit ISP 🇺🇸",
                opticPartnership = "Leica Camera AG 🇩🇪 (Summilux линзы и профили Leica)"
            )
        }

        // 3. Default General Analysis
        return DeviceSupplierAnalysis(
            brandTitle = "$manufacturer $model",
            summaryRu = "В смартфонах $manufacturer сенсоры производятся компаниями SmartSens 🇨🇳, GalaxyCore 🇨🇳, OmniVision 🇺🇸🇨🇳, Samsung 🇰🇷 и Sony 🇯🇵. Сборку модулей и линз выполняют Sunny Optical 🇨🇳 и Largan 🇹🇼.",
            summaryUa = "У смартфонах $manufacturer сенсори виробляються компаніями SmartSens 🇨🇳, GalaxyCore 🇨🇳, OmniVision 🇺🇸🇨🇳, Samsung 🇰🇷 та Sony 🇯🇵. Збірку модулів та лінз виконують Sunny Optical 🇨🇳 та Largan 🇹🇼.",
            summaryEn = "In $manufacturer devices image sensors are manufactured by SmartSens 🇨🇳, GalaxyCore 🇨🇳, OmniVision 🇺🇸🇨🇳, Samsung 🇰🇷, and Sony 🇯🇵. Modules are assembled by Sunny Optical 🇨🇳 and Largan 🇹🇼.",
            mostLikelySensorVendors = listOf("SmartSens Technology 🇨🇳", "GalaxyCore 🇨🇳", "OmniVision Technologies 🇺🇸🇨🇳", "Samsung 🇰🇷", "Sony 🇯🇵"),
            mostLikelyModuleMakers = listOf("Sunny Optical Technology 🇨🇳", "Largan Precision 🇹🇼"),
            mostLikelyIsp = "Hardware ISP ($hardware)",
            opticPartnership = null
        )
    }
}
