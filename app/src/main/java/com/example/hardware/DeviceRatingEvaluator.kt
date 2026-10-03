package com.example.hardware

import com.example.localization.AppLanguage
import com.example.model.CameraItem
import com.example.model.DeviceHardwareAudit
import com.example.model.DeviceInfo
import kotlin.math.roundToInt

data class DualDeviceRating(
    // 1. Price-to-Performance (Value for Money)
    val valueForMoneyScore: Int, // 0 - 100
    val valueForMoneyScoreOut10: Double, // e.g. 9.2
    val marketSegmentRu: String,
    val marketSegmentUa: String,
    val marketSegmentEn: String,
    val valueVerdictRu: String,
    val valueVerdictUa: String,
    val valueVerdictEn: String,
    val valueHighlightsRu: List<String>,
    val valueHighlightsUa: List<String>,
    val valueHighlightsEn: List<String>,
    val valueTierBadge: String, // e.g. "ТОП ЗА СВОИ ДЕНЬГИ / BEST VALUE"

    // 2. Overall Hardware Rating (Absolute Score)
    val absoluteHardwareScore: Int, // 0 - 100
    val absoluteHardwareScoreOut10: Double, // e.g. 7.6
    val hardwareTier: String, // "S-Tier (Флагман)" / "A-Tier" / "B-Tier" / "C-Tier"
    val hardwareVerdictRu: String,
    val hardwareVerdictUa: String,
    val hardwareVerdictEn: String,

    // Component Sub-scores (0 - 100)
    val cpuScore: Int,
    val gpuScore: Int,
    val displayScore: Int,
    val cameraScore: Int,
    val ramStorageScore: Int,
    val batteryScore: Int
) {
    fun getMarketSegment(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> marketSegmentRu
        AppLanguage.UA -> marketSegmentUa
        else -> marketSegmentEn
    }

    fun getValueVerdict(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> valueVerdictRu
        AppLanguage.UA -> valueVerdictUa
        else -> valueVerdictEn
    }

    fun getValueHighlights(lang: AppLanguage): List<String> = when (lang) {
        AppLanguage.RU -> valueHighlightsRu
        AppLanguage.UA -> valueHighlightsUa
        else -> valueHighlightsEn
    }

    fun getHardwareVerdict(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> hardwareVerdictRu
        AppLanguage.UA -> hardwareVerdictUa
        else -> hardwareVerdictEn
    }
}

object DeviceRatingEvaluator {

    fun evaluate(
        audit: DeviceHardwareAudit,
        deviceInfo: DeviceInfo?,
        cameras: List<CameraItem>
    ): DualDeviceRating {
        val socName = audit.cpu.realSocName.lowercase()
        val antutuTotal = audit.antutu.estimatedTotalScore

        // 1. CPU Score (0 - 100)
        val antutuCpu = audit.antutu.cpuScore
        val cpuScore = when {
            antutuCpu >= 450_000 -> 98
            antutuCpu >= 380_000 -> 93
            antutuCpu >= 280_000 -> 87
            antutuCpu >= 200_000 -> 79
            antutuCpu >= 140_000 -> 70
            antutuCpu >= 90_000 -> 60
            antutuCpu >= 60_000 -> 52
            else -> 45
        }

        // 2. GPU Score (0 - 100)
        val antutuGpu = audit.antutu.gpuScore
        val gpuScore = when {
            antutuGpu >= 600_000 -> 99
            antutuGpu >= 450_000 -> 94
            antutuGpu >= 280_000 -> 86
            antutuGpu >= 180_000 -> 77
            antutuGpu >= 90_000 -> 65
            antutuGpu >= 45_000 -> 54
            antutuGpu >= 20_000 -> 45
            else -> 38
        }

        // 3. Display Score (0 - 100)
        val screen = audit.screen
        var dScore = 50
        val isAmoled = screen.matrixType.contains("OLED", ignoreCase = true) || screen.matrixType.contains("AMOLED", ignoreCase = true)
        if (isAmoled) {
            dScore += 25
            if (screen.matrixType.contains("LTPO", ignoreCase = true)) dScore += 10
        }
        val hz = screen.reportedRefreshRate.toInt()
        when {
            hz >= 144 -> dScore += 15
            hz >= 120 -> dScore += 12
            hz >= 90 -> dScore += 6
        }
        if (screen.hdrCapabilities.contains("HDR10", ignoreCase = true) || screen.hdrCapabilities.contains("Dolby", ignoreCase = true)) {
            dScore += 5
        }
        val displayScore = dScore.coerceIn(40, 100)

        // 4. Camera Score (0 - 100)
        val mainCamera = cameras.firstOrNull { it.facing == com.example.model.CameraFacing.BACK }
        var cScore = 50
        val maxMp = cameras.maxOfOrNull { it.megapixels } ?: 12.0
        val hasOis = cameras.any { it.oisSupported }
        val has4k = cameras.any { it.videoResolutions.any { v -> v.contains("4K") || v.contains("8K") } }

        when {
            maxMp >= 180.0 -> cScore += 26
            maxMp >= 100.0 -> cScore += 22
            maxMp >= 50.0 -> cScore += 18
            maxMp >= 48.0 -> cScore += 16
            maxMp >= 12.0 -> cScore += 10
        }
        if (mainCamera != null && mainCamera.diagonalMm >= 7.5f) {
            cScore += 12 // Large optical format (1/1.56" to 1.0")
        } else if (mainCamera != null && mainCamera.diagonalMm >= 6.0f) {
            cScore += 6
        }
        if (hasOis) cScore += 8
        if (has4k) cScore += 6
        if (cameras.size >= 3) cScore += 4
        val cameraScore = cScore.coerceIn(40, 100)

        // 5. RAM & Storage Score (0 - 100)
        var memScore = 50
        val ramGb = audit.ram.physicalRamGb
        when {
            ramGb >= 16.0 -> memScore += 30
            ramGb >= 12.0 -> memScore += 25
            ramGb >= 8.0 -> memScore += 20
            ramGb >= 6.0 -> memScore += 14
            ramGb >= 4.0 -> memScore += 8
        }
        val storageType = audit.storage.flashStorageType
        when {
            storageType.contains("UFS 4.0") -> memScore += 20
            storageType.contains("UFS 3.1") -> memScore += 16
            storageType.contains("UFS 2.2") -> memScore += 12
            else -> memScore += 5
        }
        val ramStorageScore = memScore.coerceIn(40, 100)

        // 6. Battery Score (0 - 100)
        var bScore = 60
        val cap = audit.battery.designCapacityMah
        when {
            cap >= 6000 -> bScore += 30
            cap >= 5000 -> bScore += 24
            cap >= 4500 -> bScore += 18
            cap >= 4000 -> bScore += 12
        }
        val health = audit.battery.healthPercentage
        if (health >= 95) bScore += 10 else if (health >= 85) bScore += 6
        val batteryScore = bScore.coerceIn(40, 100)

        // ABSOLUTE HARDWARE SCORE (0 - 100)
        // Weighted composite raw benchmark
        val rawAbsolute = (cpuScore * 0.25) +
                (gpuScore * 0.22) +
                (cameraScore * 0.20) +
                (displayScore * 0.15) +
                (ramStorageScore * 0.10) +
                (batteryScore * 0.08)

        val absoluteHardwareScore = rawAbsolute.roundToInt().coerceIn(30, 99)
        val absoluteHardwareScoreOut10 = ((absoluteHardwareScore / 10.0) * 10.0).roundToInt() / 10.0

        val (hardwareTier, hardVerdictRu, hardVerdictUa, hardVerdictEn) = when {
            absoluteHardwareScore >= 90 -> Tuple4(
                "S-Tier (Элитный флагман / Flagship Elite)",
                "Ультимативная производительность без компромиссов: топовый процессор, передовые камеры и дисплей флагманского уровня.",
                "Ультимативна продуктивність без компромісів: топовий процесор, передові камери та дисплей флагманського рівня.",
                "Ultimate uncompromised performance: top-tier SoC, state-of-the-art camera subsystem, and flagship display."
            )
            absoluteHardwareScore >= 78 -> Tuple4(
                "A-Tier (Высокая производительность / High-End)",
                "Мощная аппаратная база с высоким запасом на будущее: отличная отзывчивость в любых задачах и играх.",
                "Потужна апаратна база з високим запасом на майбутнє: відмінна чуйність у будь-яких завданнях та іграх.",
                "High-performance hardware platform with great longevity: outstanding responsiveness across heavy tasks and games."
            )
            absoluteHardwareScore >= 62 -> Tuple4(
                "B-Tier (Сбалансированное железо / Balanced Mid)",
                "Оптимальный баланс для повседневной работы: комфортная многозадачность, плавная система и надежная автономность.",
                "Оптимальний баланс для щоденної роботи: комфортна багатозадачність, плавна система та надійна автономність.",
                "Balanced daily driver platform: solid multitasking, fluid system responsiveness, and dependable battery life."
            )
            else -> Tuple4(
                "C-Tier (Базовый уровень / Entry-Level)",
                "Базовая аппаратная платформа, ориентированная на повседневные звонки, мессенджеры, веб-серфинг и видео.",
                "Базова апаратна платформа, орієнтована на щоденні дзвінки, месенджери, веб-серфінг та відео.",
                "Entry-level hardware platform focused on daily essentials, social media, messaging, and web browsing."
            )
        }

        // PRICE-TO-PERFORMANCE (VALUE FOR MONEY)
        // Identify market tier based on SoC & AnTuTu benchmark:
        val isFlagshipTier = antutuTotal >= 1_200_000 || socName.contains("gen 3") || socName.contains("gen 2") || socName.contains("9300") || socName.contains("9400") || socName.contains("2400")
        val isSubFlagshipTier = !isFlagshipTier && (antutuTotal >= 700_000 || socName.contains("8s") || socName.contains("8300") || socName.contains("7+"))
        val isMidRangeTier = !isFlagshipTier && !isSubFlagshipTier && (antutuTotal >= 380_000 || socName.contains("7s") || socName.contains("7200") || socName.contains("1480") || socName.contains("6 gen"))
        val isBudgetTier = !isFlagshipTier && !isSubFlagshipTier && !isMidRangeTier

        val (marketRu, marketUa, marketEn) = when {
            isFlagshipTier -> Triple("Премиум-флагман ($700 - $1400+)", "Преміум-флагман ($700 - $1400+)", "Top-Tier Flagship ($700 - $1400+)")
            isSubFlagshipTier -> Triple("Субфлагман ($350 - $600)", "Субфлагман ($350 - $600)", "Upper Mid / Sub-Flagship ($350 - $600)")
            isMidRangeTier -> Triple("Средний класс ($180 - $350)", "Середній клас ($180 - $350)", "Mid-Range Segment ($180 - $350)")
            else -> Triple("Бюджетный сегмент ($80 - $160)", "Бюджетний сегмент ($80 - $160)", "Budget / Entry Segment ($80 - $160)")
        }

        // Calculate Value Score: how feature-rich the device is relative to its price bracket!
        val valueScore: Int
        val valueBadge: String
        val highlightsRu = mutableListOf<String>()
        val highlightsUa = mutableListOf<String>()
        val highlightsEn = mutableListOf<String>()

        if (isBudgetTier) {
            var v = 82
            if (maxMp >= 50.0) {
                v += 6
                highlightsRu.add("50-Мп камера в ультрабюджетном классе")
                highlightsUa.add("50-Мп камера в ультрабюджетному класі")
                highlightsEn.add("50MP camera in an entry-level budget tier")
            }
            if (hz >= 90) {
                v += 5
                highlightsRu.add("Плавный дисплей $hz Гц")
                highlightsUa.add("Плавний дисплей $hz Гц")
                highlightsEn.add("Smooth $hz Hz display refresh rate")
            }
            if (cap >= 5000) {
                v += 4
                highlightsRu.add("Емкий аккумулятор $cap мАч")
                highlightsUa.add("Місткий акумулятор $cap мАг")
                highlightsEn.add("Large $cap mAh battery capacity")
            }
            if (ramGb >= 6.0) {
                v += 3
                highlightsRu.add("Большой объем оперативной памяти (${ramGb.toInt()} ГБ)")
                highlightsUa.add("Великий обсяг оперативної пам'яті (${ramGb.toInt()} ГБ)")
                highlightsEn.add("Generous RAM capacity (${ramGb.toInt()} GB)")
            }
            valueScore = v.coerceIn(80, 97)
            valueBadge = if (valueScore >= 92) "ТОП ЗА СВОИ ДЕНЬГИ 🔥" else "ОТЛИЧНАЯ ВЫГОДА 👍"
        } else if (isMidRangeTier) {
            var v = 80
            if (isAmoled && hz >= 120) {
                v += 6
                highlightsRu.add("120 Гц AMOLED с глубоким черным цветом")
                highlightsUa.add("120 Гц AMOLED із глибоким чорним кольором")
                highlightsEn.add("120 Hz AMOLED with true deep blacks")
            }
            if (hasOis || maxMp >= 100.0) {
                v += 5
                highlightsRu.add("Продвинутая стабилизация OIS / 100+ Мп")
                highlightsUa.add("Просунута стабілізація OIS / 100+ Мп")
                highlightsEn.add("Advanced OIS stabilization / 100+ MP sensor")
            }
            if (storageType.contains("UFS")) {
                v += 4
                highlightsRu.add("Быстрый накопитель $storageType")
                highlightsUa.add("Швидкий накопичувач $storageType")
                highlightsEn.add("Fast flash storage ($storageType)")
            }
            valueScore = v.coerceIn(78, 96)
            valueBadge = if (valueScore >= 90) "ЛУЧШИЙ В СРЕДНЕМ КЛАССЕ 🔥" else "ВЫСОКАЯ ВЫГОДА 👍"
        } else if (isSubFlagshipTier) {
            var v = 82
            if (antutuTotal >= 1_000_000) {
                v += 6
                highlightsRu.add("Флагманская мощь более 1 млн баллов AnTuTu")
                highlightsUa.add("Флагманська міць понад 1 млн балів AnTuTu")
                highlightsEn.add("Flagship-grade power surpassing 1M AnTuTu score")
            }
            highlightsRu.add("Субфлагманский чипсет с охлаждением")
            highlightsUa.add("Субфлагманський чипсет з охолодженням")
            highlightsEn.add("Sub-flagship SoC with active thermals")
            valueScore = v.coerceIn(78, 95)
            valueBadge = "УБИЙЦА ФЛАГМАНОВ ⚡"
        } else {
            // Flagship tier: supreme hardware, premium price
            valueScore = 84
            valueBadge = "ПРЕМИАЛЬНОЕ КАЧЕСТВО 💎"
            highlightsRu.add("Максимальные флагманские компоненты")
            highlightsUa.add("Максимальні флагманські компоненти")
            highlightsEn.add("Peak flagship components and build")
        }

        val valueScoreOut10 = ((valueScore / 10.0) * 10.0).roundToInt() / 10.0

        val valVerdictRu = when {
            valueScore >= 92 -> "Выдающееся соотношение цены и характеристик: аппарат предлагает возможности старших ценовых классов за существенно меньшие деньги."
            valueScore >= 84 -> "Отличное вложение средств: аппаратная начинка превосходит большинство прямых конкурентов в своем классе."
            else -> "Сбалансированное рыночное предложение с адекватным набором характеристик за свою стоимость."
        }

        val valVerdictUa = when {
            valueScore >= 92 -> "Видатне співвідношення ціни та характеристик: пристрій пропонує можливості старших цінових класів за значно менші кошти."
            valueScore >= 84 -> "Чудове вкладення коштів: апаратна начинка перевершує більшість прямих конкурентів у своєму класі."
            else -> "Збалансована ринкова пропозиція з адекватним набором характеристик за свою вартість."
        }

        val valVerdictEn = when {
            valueScore >= 92 -> "Outstanding price-to-performance ratio: delivers hardware features typically reserved for higher price tiers."
            valueScore >= 84 -> "Great value for money: hardware platform outperforms most direct segment competitors."
            else -> "Balanced market proposition with solid, reliable specs for its price bracket."
        }

        return DualDeviceRating(
            valueForMoneyScore = valueScore,
            valueForMoneyScoreOut10 = valueScoreOut10,
            marketSegmentRu = marketRu,
            marketSegmentUa = marketUa,
            marketSegmentEn = marketEn,
            valueVerdictRu = valVerdictRu,
            valueVerdictUa = valVerdictUa,
            valueVerdictEn = valVerdictEn,
            valueHighlightsRu = highlightsRu,
            valueHighlightsUa = highlightsUa,
            valueHighlightsEn = highlightsEn,
            valueTierBadge = valueBadge,
            absoluteHardwareScore = absoluteHardwareScore,
            absoluteHardwareScoreOut10 = absoluteHardwareScoreOut10,
            hardwareTier = hardwareTier,
            hardwareVerdictRu = hardVerdictRu,
            hardwareVerdictUa = hardVerdictUa,
            hardwareVerdictEn = hardVerdictEn,
            cpuScore = cpuScore,
            gpuScore = gpuScore,
            displayScore = displayScore,
            cameraScore = cameraScore,
            ramStorageScore = ramStorageScore,
            batteryScore = batteryScore
        )
    }

    private data class Tuple4(val a: String, val b: String, val c: String, val d: String)
}
