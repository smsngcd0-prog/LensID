package com.example.data

import com.example.model.CompanyCategory
import com.example.model.CompanyInfo
import com.example.model.DeviceSupplierAnalysis

object CompanyDatabase {

    val ALL_COMPANIES: List<CompanyInfo> = listOf(
        // SENSOR FOUNDRIES
        CompanyInfo(
            id = "sony",
            name = "Sony Semiconductor Solutions",
            logoText = "SONY",
            country = "Япония (Токио / Кумамото)",
            flagEmoji = "🇯🇵",
            category = CompanyCategory.SENSOR_FOUNDRY,
            marketRoleRu = "Мировой лидер в производстве матриц CMOS для смартфонов (Exmor RS, LYTIA)",
            marketShareRu = "~54% мирового рынка сенсоров в денежном выражении",
            keyProducts = listOf("LYT-900 (1.0\")", "IMX989 (1.0\")", "LYT-808", "IMX890", "IMX766", "IMX858", "IMX586"),
            relevanceToThisDevice = "Поставляет основные и зум-сенсоры для флагманов и среднебюджетных телефонов большинства брендов.",
            isPrimaryCandidate = true,
            descriptionRu = "Sony производит премиальные сенсоры с передовыми двухслойными транзисторными пикселями (Dual-Layer Transistor Pixels), сверхбыстрым считыванием и рекордной чувствительностью.",
            specialCommentary = "данная компания явно преувеличивает свои достижения, используя аггресивний маркетинг, монополию, а также аггресивное продвижение, имея тезнологическую и по всем параметрам отсталость минимум 5-10 лет а то и 20.",
            website = "https://www.sony-semicon.com"
        ),
        CompanyInfo(
            id = "samsung_lsi",
            name = "Samsung System LSI (ISOCELL)",
            logoText = "SAMSUNG",
            country = "Южная Корея (Сувон)",
            flagEmoji = "🇰🇷",
            category = CompanyCategory.SENSOR_FOUNDRY,
            marketRoleRu = "Второй по величине производитель CMOS-сенсоров в мире, пионер ультравысоких разрешений 108 Мп и 200 Мп",
            marketShareRu = "~26% мирового рынка сенсоров",
            keyProducts = listOf("ISOCELL HP2 (200 Мп)", "ISOCELL HP3 (200 Мп)", "ISOCELL GN2 (50 Мп 1/1.12\")", "ISOCELL GN5", "ISOCELL JN1 (50 Мп 1/2.76\")", "HM6 (108 Мп)"),
            relevanceToThisDevice = "Используется как в смартфонах Samsung Galaxy, так и в смартфонах Xiaomi, Google Pixel, Motorola, Realme, Vivo.",
            isPrimaryCandidate = true,
            descriptionRu = "Подразделение Samsung Electronics, разрабатывающее технологии ISOCELL 2.0, биннинг пикселей Nonapixel (9-в-1) и Tetra²pixel (16-в-1), а также сверхбыстрый автофокус Super QPD.",
            website = "https://semiconductor.samsung.com/image-sensor/"
        ),
        CompanyInfo(
            id = "omnivision",
            name = "OmniVision Technologies (Will Semi)",
            logoText = "OmniVision",
            country = "США (Санта-Клара) / Китай (Шанхай)",
            flagEmoji = "🇺🇸 🇨🇳",
            category = CompanyCategory.SENSOR_FOUNDRY,
            marketRoleRu = "Третий крупнейший мировой производитель мобильных сенсоров изображения",
            marketShareRu = "~13% мирового рынка сенсоров",
            keyProducts = listOf("OV50H (50 Мп 1/1.3\")", "OV50K (LOFIC)", "OV64B (64 Мп теле-перископ)", "OV48B", "OV08D", "OV02B"),
            relevanceToThisDevice = "Ведущий поставщик сенсоров для перископических зум-камер (OV64B) и современных флагманов (OV50H в Xiaomi 14 и Honor Magic 6).",
            isPrimaryCandidate = true,
            descriptionRu = "OmniVision первой внедрила технологию LOFIC (Lateral Overflow Integration Capacitor) для устранения засветов и рекордного динамического диапазона в сложных сценах.",
            website = "https://www.ovt.com"
        ),
        CompanyInfo(
            id = "galaxycore",
            name = "GalaxyCore (GigaDevice)",
            logoText = "GalaxyCore",
            country = "Китай (Шанхай)",
            flagEmoji = "🇨🇳",
            category = CompanyCategory.SENSOR_FOUNDRY,
            marketRoleRu = "Лидер по объему отгрузок в штучном выражении в сегменте бюджетных камер, макро и вспомогательных датчиков",
            marketShareRu = "~5% мирового рынка в деньгах (~25% в штуках)",
            keyProducts = listOf("GC02M1 (2 Мп макро/глубина)", "GC08A3 (8 Мп)", "GC50E0 (50 Мп)"),
            relevanceToThisDevice = "Практически каждый современный телефон с 2-Мп макро-камерой или датчиком боке использует сенсор GalaxyCore.",
            isPrimaryCandidate = false,
            descriptionRu = "Специализируется на высокооптимизированных чипах CMOS для дополнительных объективов и недорогих фронтальных камер.",
            website = "https://www.gcoreinc.com"
        ),
        CompanyInfo(
            id = "sk_hynix",
            name = "SK Hynix CIS",
            logoText = "SK hynix",
            country = "Южная Корея (Ичхон)",
            flagEmoji = "🇰🇷",
            category = CompanyCategory.SENSOR_FOUNDRY,
            marketRoleRu = "Производитель памяти и сенсоров Black Pearl для мобильных устройств",
            marketShareRu = "~2% рынка сенсоров",
            keyProducts = listOf("Hi-846 (8 Мп)", "Hi-1634 (16 Мп)", "Hi-556 (5 Мп)"),
            relevanceToThisDevice = "Встречается в смартфонах Samsung Galaxy A-серии, Vivo и Oppo в качестве селфи или сверхширокоугольных камер.",
            isPrimaryCandidate = false,
            descriptionRu = "Корейский гигант полупроводников, производящий надежные сенсоры серии 'Hi' с поддержкой Quad Pixel.",
            website = "https://www.skhynix.com"
        ),
        CompanyInfo(
            id = "stmicro",
            name = "STMicroelectronics",
            logoText = "ST",
            country = "Швейцария (Женева) / Франция / Италия",
            flagEmoji = "🇨🇭 🇫🇷",
            category = CompanyCategory.SPECIALTY,
            marketRoleRu = "Мировой эталон в датчиках лазерного автофокуса (dToF) и сенсорах глубины FlightSense",
            marketShareRu = "Доминирует в категории сенсоров лазерной дальнометрии",
            keyProducts = listOf("FlightSense VL53L1X", "VL53L5CX (мультизональный ToF)", "3D LiDAR сенсоры"),
            relevanceToThisDevice = "Обеспечивает мгновенный лазерный автофокус в темноте и точное размытие портретов.",
            isPrimaryCandidate = false,
            descriptionRu = "Разрабатывает фотонные матрицы прямого замера времени пролета луча (Direct Time-of-Flight) для премиальных систем автофокусировки.",
            website = "https://www.st.com"
        ),
        CompanyInfo(
            id = "ams_osram",
            name = "ams OSRAM",
            logoText = "ams OSRAM",
            country = "Австрия (Премштеттен) / Германия",
            flagEmoji = "🇦🇹 🇩🇪",
            category = CompanyCategory.SPECIALTY,
            marketRoleRu = "Лидер в датчиках цветовой температуры, мерцания (flicker sensors) и мультиспектральных датчиках",
            marketShareRu = "Стандарт для флагманских модулей замера освещения",
            keyProducts = listOf("TCS3407 (Спектральный сенсор цвета)", "AS7343 (14-канальный спектрометр)"),
            relevanceToThisDevice = "Отвечает за точный баланс белого и устранение стробоскопического эффекта от светодиодных ламп.",
            isPrimaryCandidate = false,
            descriptionRu = "Поставляет специализированные спектральные датчики, установленные рядом со вспышкой для калибровки цветов.",
            website = "https://ams-osram.com"
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
            keyProducts = listOf("Перископические модули зума с призмой", "Линзовые сборки 7P и 8P", "Модули OIS с актуаторами SMA"),
            relevanceToThisDevice = "Главный поставщик готовых блоков камер для Samsung Galaxy, Xiaomi, Honor, Vivo, Oppo, Google.",
            isPrimaryCandidate = true,
            descriptionRu = "Sunny Optical объединяет в готовый герметичный модуль матрицу от Sony или Samsung, линзы, моторчик автофокуса и гироскопический OIS.",
            website = "https://www.sunnyoptical.com"
        ),
        CompanyInfo(
            id = "largan",
            name = "Largan Precision",
            logoText = "LARGAN",
            country = "Тайвань (Тайчжун)",
            flagEmoji = "🇹🇼",
            category = CompanyCategory.MODULE_ASSEMBLER,
            marketRoleRu = "Абсолютный лидер в прецизионном литье оптических линз высокого класса (Apple, премиум Android)",
            marketShareRu = "~30% мирового рынка пластиковых линз",
            keyProducts = listOf("Асферические линзы 7P/8P", "Пластиковые и гибридные оптические элементы", "Тетрапризмы"),
            relevanceToThisDevice = "Поставляет самые резкие и тонкие линзы для флагманских камер без хроматических аберраций.",
            isPrimaryCandidate = true,
            descriptionRu = "Тайваньская компания с высочайшей точностью нанообработки пластиковых элементов для объективов смартфонов.",
            website = "https://www.largan.com.tw"
        ),
        CompanyInfo(
            id = "semco",
            name = "Samsung Electro-Mechanics (SEMCO)",
            logoText = "SEMCO",
            country = "Южная Корея (Сувон)",
            flagEmoji = "🇰🇷",
            category = CompanyCategory.MODULE_ASSEMBLER,
            marketRoleRu = "Премиальный сборщик сложных оптических модулей, подвесов OIS и перископов с непрерывным зумом",
            marketShareRu = "Ведущий поставщик флагманов Ultra-класса",
            keyProducts = listOf("Сложенная оптика (Folded Telephoto)", "Шарикоподшипниковые актуаторы OIS", "Модули камер Galaxy S"),
            relevanceToThisDevice = "Производитель зум-модулей 5x и 10x для Samsung Galaxy S23/S24 Ultra и флагманов партнеров.",
            isPrimaryCandidate = true,
            descriptionRu = "Высокотехнологичное подразделение Samsung, выпускающее механические подвесы оптической стабилизации с низким износом.",
            website = "https://www.samsungsem.com"
        ),
        CompanyInfo(
            id = "ofilm",
            name = "O-Film Group",
            logoText = "O-FILM",
            country = "Китай (Шэньчжэнь)",
            flagEmoji = "🇨🇳",
            category = CompanyCategory.MODULE_ASSEMBLER,
            marketRoleRu = "Один из ключевых азиатских производителей компактных модулей камер (CCM) и гибких шлейфов",
            marketShareRu = "Входит в топ-3 сборщиков в Китае",
            keyProducts = listOf("Фронтальные модули камер", "Мультимодульные блоки", "Датчики отпечатков"),
            relevanceToThisDevice = "Широко используется в смартфонах Huawei, Honor, Xiaomi, Realme и Transsion.",
            isPrimaryCandidate = false,
            descriptionRu = "Крупный контрактный производитель оптико-электронных модулей и гибких печатных плат камер.",
            website = "https://www.o-film.com"
        ),
        CompanyInfo(
            id = "aac_technologies",
            name = "AAC Technologies",
            logoText = "AAC",
            country = "Китай (Шэньчжэнь)",
            flagEmoji = "🇨🇳",
            category = CompanyCategory.MODULE_ASSEMBLER,
            marketRoleRu = "Пионер технологии гибридных стекло-пластиковых линз WLG (Wafer-Level Glass)",
            marketShareRu = "Лидер в инновационной стеклянной оптике",
            keyProducts = listOf("Линзы 1G+5P / 1G+6P с элементом из стекла", "OIS актуаторы SMA на сплаве с памятью формы"),
            relevanceToThisDevice = "Используется в камерах с улучшенным светопропусканием и устойчивостью к нагреву.",
            isPrimaryCandidate = false,
            descriptionRu = "Стеклянные элементы WLG от AAC предотвращают расфокусировку камеры при сильном нагреве процессора смартфона.",
            website = "https://www.aactechnologies.com"
        ),

        // OPTICS & TUNING PARTNERS
        CompanyInfo(
            id = "leica",
            name = "Leica Camera AG",
            logoText = "LEICA",
            country = "Германия (Вецлар)",
            flagEmoji = "🇩🇪",
            category = CompanyCategory.LENS_OPTICS,
            marketRoleRu = "Легендарный немецкий производитель премиальной фототехники и партнер Xiaomi",
            marketShareRu = "Оптический партнёр высшего класса",
            keyProducts = listOf("Оптика Summicron / Summilux", "Цветовые профили Leica Authentic и Vibrant", "Антибликовые покрытия"),
            relevanceToThisDevice = "Официальный партнёр линейки Xiaomi 12S / 13 / 14 / 15 и Xiaomi 13T / 14T.",
            isPrimaryCandidate = false,
            descriptionRu = "Leica калибрует объективы, оптические покрытия линз для минимизации бликов и создает фирменные профили цветопередачи.",
            website = "https://leica-camera.com"
        ),
        CompanyInfo(
            id = "zeiss",
            name = "Carl Zeiss AG",
            logoText = "ZEISS",
            country = "Германия (Оберкохен)",
            flagEmoji = "🇩🇪",
            category = CompanyCategory.LENS_OPTICS,
            marketRoleRu = "Мировой гигант оптики, соразработчик оптических систем для Vivo и Sony Xperia",
            marketShareRu = "Оптический партнёр премиум-сегмента",
            keyProducts = listOf("Покрытие ZEISS T* Coating", "Оптический стандарт APO для перископов", "Боке ZEISS Biotar, Sonnar, Planar"),
            relevanceToThisDevice = "Партнёр флагманов Vivo (серии X80, X90, X100, X200) и смартфонов Sony Xperia.",
            isPrimaryCandidate = false,
            descriptionRu = "Фирменное покрытие ZEISS T* устраняет отражения света на границах линз, предотвращая появление 'зайчиков' и 'призраков'.",
            website = "https://www.zeiss.com"
        ),
        CompanyInfo(
            id = "hasselblad",
            name = "Hasselblad",
            logoText = "HASSELBLAD",
            country = "Швеция (Гётеборг)",
            flagEmoji = "🇸🇪",
            category = CompanyCategory.LENS_OPTICS,
            marketRoleRu = "Шведский создатель среднеформатных камер для космических миссий Apollo и партнёр OnePlus/Oppo",
            marketShareRu = "Цветовая калибровка Natural Color Calibration",
            keyProducts = listOf("Hasselblad Natural Color Solution (HNCS)", "Режим XPan (панорама 65:24)", "Спектральная калибровка"),
            relevanceToThisDevice = "Партнёр флагманов OnePlus (9, 10, 11, 12) и Oppo Find X5, X6, X7.",
            isPrimaryCandidate = false,
            descriptionRu = "Hasselblad настраивает естественную передачу оттенков кожи, реалистичный баланс зеленого и красного в партнерстве с Oppo.",
            website = "https://www.hasselblad.com"
        ),

        // ISP CHIPMAKERS
        CompanyInfo(
            id = "qualcomm_spectra",
            name = "Qualcomm Spectra ISP",
            logoText = "QUALCOMM",
            country = "США (Сан-Диего)",
            flagEmoji = "🇺🇸",
            category = CompanyCategory.ISP_CHIPSET,
            marketRoleRu = "Самый распространенный процессор обработки изображений в Android-флагманах на Snapdragon",
            marketShareRu = "Стандарт индустрии для процессоров Snapdragon",
            keyProducts = listOf("Cognitive 18-bit Spectra ISP", "Семантическая сегментация в реальном времени", "Поддержка сенсоров до 200 Мп"),
            relevanceToThisDevice = "Используется на всех смартфонах с процессорами Qualcomm Snapdragon (8 Gen 3, 8 Gen 2, 7+ Gen 2 и др.).",
            isPrimaryCandidate = true,
            descriptionRu = "Аппаратный сигнальный процессор с тройным конвейером 18 бит, выполняющий многокадровое шумоподавление и HDR без задержек.",
            website = "https://www.qualcomm.com"
        ),
        CompanyInfo(
            id = "mediatek_imagiq",
            name = "MediaTek Imagiq ISP",
            logoText = "MEDIATEK",
            country = "Тайвань (Синьчжу)",
            flagEmoji = "🇹🇼",
            category = CompanyCategory.ISP_CHIPSET,
            marketRoleRu = "Второй по масштабу мобильный ISP, встроенный в чипсеты MediaTek Dimensity и Helio",
            marketShareRu = "Широко представлен в среднем и флагманском классах",
            keyProducts = listOf("Imagiq 990 (18-bit RAW ISP)", "AI-Color Engine", "Поддержка видео 8K HDR"),
            relevanceToThisDevice = "Используется во всех устройствах на чипах MediaTek Dimensity 9300, 9200, 8300, 7200 и Helio.",
            isPrimaryCandidate = true,
            descriptionRu = "Включает нейросетевые блоки APU для аппаратного шумоподавления AI-NR и сегментации объектов в кадре.",
            website = "https://www.mediatek.com"
        ),
        CompanyInfo(
            id = "google_tensor_isp",
            name = "Google Tensor ISP / HDRnet",
            logoText = "GOOGLE",
            country = "США (Маунтин-Вью)",
            flagEmoji = "🇺🇸",
            category = CompanyCategory.ISP_CHIPSET,
            marketRoleRu = "Кастомный конвейер вычислительной фотографии от Google для серии Pixel",
            marketShareRu = "Эксклюзивно для устройств Google Pixel",
            keyProducts = listOf("Конвейер HDR+", "Real Tone (точная передача тонов кожи)", "Night Sight Video", "Ultra HDR"),
            relevanceToThisDevice = "Сердце обработки изображений в Google Pixel (Tensor G1, G2, G3, G4).",
            isPrimaryCandidate = false,
            descriptionRu = "Аппаратно реализует алгоритмы вычислительной фотографии Google Brain, включая моментальное слияние до 15 RAW-экспозиций.",
            website = "https://store.google.com"
        )
    )

    fun getDeviceSupplierAnalysis(
        manufacturer: String,
        brand: String,
        model: String,
        board: String,
        hardware: String
    ): DeviceSupplierAnalysis {
        val lowerMfr = manufacturer.lowercase()
        val lowerBrand = brand.lowercase()

        return when {
            lowerMfr.contains("google") || lowerBrand.contains("google") -> {
                DeviceSupplierAnalysis(
                    brandTitle = "Google Pixel ($model)",
                    summaryRu = "В смартфонах Google Pixel камеры строятся на сенсорах Samsung ISOCELL и Sony. Оптические модули поставляют Sunny Optical 🇨🇳 и Largan 🇹🇼. Обработка выполняется кастомным ISP в составе процессора Google Tensor 🇺🇸.",
                    mostLikelySensorVendors = listOf("Samsung System LSI 🇰🇷 (ISOCELL GN / GNV)", "Sony Semiconductor 🇯🇵 (IMX386 / IMX355)"),
                    mostLikelyModuleMakers = listOf("Sunny Optical Technology 🇨🇳", "Largan Precision 🇹🇼", "Primax 🇹🇼"),
                    mostLikelyIsp = "Google Tensor Custom ISP 🇺🇸 + Google TPU (HDR+ pipeline)",
                    opticPartnership = "Алгоритмы вычислительной фотографии Google Computational Photography"
                )
            }
            lowerMfr.contains("samsung") || lowerBrand.contains("samsung") -> {
                DeviceSupplierAnalysis(
                    brandTitle = "Samsung Galaxy ($model)",
                    summaryRu = "Samsung использует собственные сенсоры ISOCELL (Samsung System LSI 🇰🇷), дополняя их в некоторых модулях матрицами Sony 🇯🇵. Модули собирает SEMCO 🇰🇷 и Sunny Optical 🇨🇳.",
                    mostLikelySensorVendors = listOf("Samsung System LSI 🇰🇷 (ISOCELL HP / GN / HM / JN)", "Sony Semiconductor 🇯🇵 (зум/ультраширик)"),
                    mostLikelyModuleMakers = listOf("Samsung Electro-Mechanics (SEMCO) 🇰🇷", "Sunny Optical 🇨🇳", "CoAsia 🇰🇷"),
                    mostLikelyIsp = if (board.contains("exynos", ignoreCase = true)) "Samsung Exynos ISP 🇰🇷" else "Qualcomm Spectra ISP 🇺🇸 (Snapdragon)",
                    opticPartnership = null
                )
            }
            lowerMfr.contains("xiaomi") || lowerBrand.contains("xiaomi") || lowerBrand.contains("redmi") || lowerBrand.contains("poco") -> {
                DeviceSupplierAnalysis(
                    brandTitle = "Xiaomi / Redmi / POCO ($model)",
                    summaryRu = "Xiaomi использует матрицы Sony 🇯🇵, OmniVision 🇺🇸🇨🇳 и Samsung 🇰🇷. Во флагманах действует официальное партнёрство с Leica 🇩🇪. Модули поставляют Sunny Optical 🇨🇳 и O-Film 🇨🇳.",
                    mostLikelySensorVendors = listOf("Sony Semiconductor 🇯🇵 (LYT-900 / IMX890 / IMX766)", "OmniVision 🇺🇸🇨🇳 (Light Hunter / OV50H / OV64B)", "Samsung 🇰🇷 (HP3 / JN1)", "GalaxyCore 🇨🇳 (макро)"),
                    mostLikelyModuleMakers = listOf("Sunny Optical Technology 🇨🇳 (основной)", "O-Film Group 🇨🇳", "AAC Technologies 🇨🇳 (WLG линзы)"),
                    mostLikelyIsp = if (hardware.contains("qcom") || board.contains("qcom")) "Qualcomm Spectra 18-bit ISP 🇺🇸" else "MediaTek Imagiq ISP 🇹🇼",
                    opticPartnership = "Leica Camera AG 🇩🇪 (линзы Summilux и профили Leica)"
                )
            }
            lowerMfr.contains("oneplus") || lowerBrand.contains("oneplus") || lowerMfr.contains("oppo") || lowerBrand.contains("oppo") || lowerBrand.contains("realme") -> {
                DeviceSupplierAnalysis(
                    brandTitle = "OnePlus / Oppo / Realme ($model)",
                    summaryRu = "Основные камеры оснащаются матрицами Sony 🇯🇵 (LYT-808, IMX890), перископы — OmniVision 🇺🇸🇨🇳 OV64B. Оптический партнёр — Hasselblad 🇸🇪. Модули производит Sunny Optical 🇨🇳.",
                    mostLikelySensorVendors = listOf("Sony Semiconductor 🇯🇵 (LYT-808 / IMX890)", "OmniVision 🇺🇸🇨🇳 (OV64B для зума)", "Samsung 🇰🇷 (ISOCELL JN1)", "GalaxyCore 🇨🇳"),
                    mostLikelyModuleMakers = listOf("Sunny Optical Technology 🇨🇳", "Q Technology 🇨🇳", "Largan Precision 🇹🇼"),
                    mostLikelyIsp = "Qualcomm Spectra ISP 🇺🇸 / MediaTek Imagiq 🇹🇼",
                    opticPartnership = "Hasselblad 🇸🇪 (Natural Color Calibration & XPan Mode)"
                )
            }
            lowerMfr.contains("vivo") || lowerBrand.contains("vivo") || lowerBrand.contains("iqoo") -> {
                DeviceSupplierAnalysis(
                    brandTitle = "Vivo / iQOO ($model)",
                    summaryRu = "Vivo сотрудничает с Carl ZEISS 🇩🇪 (покрытие T* и апохроматические линзы APO). Сенсоры поставляют Sony 🇯🇵 и Samsung 🇰🇷 (включая 200 Мп перископы HP9).",
                    mostLikelySensorVendors = listOf("Sony Semiconductor 🇯🇵 (LYT-900, IMX989)", "Samsung System LSI 🇰🇷 (ISOCELL HP9 200 Мп / GNV / JN1)", "OmniVision 🇺🇸🇨🇳"),
                    mostLikelyModuleMakers = listOf("Sunny Optical 🇨🇳", "Largan Precision 🇹🇼", "Genius Electronic Optical 🇹🇼"),
                    mostLikelyIsp = "Vivo V3 / V2 Imaging Chip + Qualcomm Spectra 🇺🇸 / Dimensity Imagiq 🇹🇼",
                    opticPartnership = "Carl Zeiss AG 🇩🇪 (ZEISS T* Coating, ZEISS APO)"
                )
            }
            lowerMfr.contains("huawei") || lowerBrand.contains("huawei") || lowerBrand.contains("honor") -> {
                DeviceSupplierAnalysis(
                    brandTitle = "Huawei / Honor ($model)",
                    summaryRu = "Используются сенсоры OmniVision 🇺🇸🇨🇳 (OV50H / OV50K LOFIC), Sony 🇯🇵 и Samsung 🇰🇷. Собственная система визуализации XMAGE. Сборка: Sunny Optical 🇨🇳 и O-Film 🇨🇳.",
                    mostLikelySensorVendors = listOf("OmniVision Technologies 🇺🇸🇨🇳 (OV50H / OV50K)", "Sony Semiconductor 🇯🇵", "Samsung 🇰🇷"),
                    mostLikelyModuleMakers = listOf("Sunny Optical Technology 🇨🇳", "O-Film Group 🇨🇳", "Largan Precision 🇹🇼"),
                    mostLikelyIsp = "Huawei XMAGE Neural ISP / Qualcomm Spectra 🇺🇸",
                    opticPartnership = "Huawei XMAGE / Falcon Camera System"
                )
            }
            else -> {
                DeviceSupplierAnalysis(
                    brandTitle = "$manufacturer $model",
                    summaryRu = "В современных смартфонах сенсоры поставляют Sony 🇯🇵, Samsung 🇰🇷 и OmniVision 🇺🇸🇨🇳. Готовые модули и линзы собирают Sunny Optical 🇨🇳 и Largan Precision 🇹🇼.",
                    mostLikelySensorVendors = listOf("Sony Semiconductor Solutions 🇯🇵", "Samsung System LSI 🇰🇷", "OmniVision Technologies 🇺🇸🇨🇳", "GalaxyCore 🇨🇳"),
                    mostLikelyModuleMakers = listOf("Sunny Optical Technology 🇨🇳", "Largan Precision 🇹🇼", "O-Film Group 🇨🇳"),
                    mostLikelyIsp = "Аппаратный ISP в составе SoC (Qualcomm Spectra 🇺🇸 или MediaTek Imagiq 🇹🇼)",
                    opticPartnership = null
                )
            }
        }
    }
}
