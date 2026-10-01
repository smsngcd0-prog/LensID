package com.example.model

import com.example.localization.AppLanguage

data class CpuAudit(
    val realSocName: String,
    val vendor: String,
    val architecture: String,
    val coreCount: Int,
    val coreConfiguration: String,
    val gpuModel: String,
    val processNodeNm: String,
    val is64Bit: Boolean,
    val abiList: List<String>
)

data class StorageAudit(
    val physicalChipCapacityGb: Double,
    val reportedTotalStorageGb: Double,
    val freeStorageGb: Double,
    val flashStorageType: String,
    val isSpoofed: Boolean,
    val integrityMessageRu: String,
    val integrityMessageUa: String,
    val integrityMessageEn: String
) {
    fun getIntegrityMessage(lang: AppLanguage): String {
        val phys = physicalChipCapacityGb.toInt()
        val rep = reportedTotalStorageGb.toInt()
        return if (isSpoofed) {
            when (lang) {
                AppLanguage.RU -> "ВНИМАНИЕ! ОБНАРУЖЕНА ПОДДЕЛКА ПАМЯТИ! В прошивке заявлено $rep ГБ, но физический кремниевый чип всего $phys ГБ! Запись свыше $phys ГБ повредит файлы."
                AppLanguage.UA -> "УВАГА! ВИЯВЛЕНО ПІДРОБКУ ПАМ'ЯТІ! У прошивці заявлено $rep ГБ, але фізичний кремнієвий чип лише $phys ГБ! Запис понад $phys ГБ пошкодить файли."
                AppLanguage.ES -> "¡ATENCIÓN! ¡FALSIFICACIÓN DE MEMORIA DETECTADA! El firmware indica $rep GB, pero el chip de silicio físico es de solo $phys GB."
                AppLanguage.PT, AppLanguage.PT_BR -> "ATENÇÃO! MEMÓRIA FALSIFICADA DETECTADA! O sistema alega $rep GB, mas o chip físico de silício tem apenas $phys GB."
                AppLanguage.FR -> "ATTENTION! MÉMOIRE FALSIFIÉE DÉTECTÉE! Le système affiche $rep Go, mais la puce de silicium physique ne fait que $phys Go."
                AppLanguage.IT -> "ATTENZIONE! MEMORIA CONTRAFFATTA RILEVATA! Il sistema dichiara $rep GB, ma il chip fisico è di soli $phys GB."
                AppLanguage.DE -> "ACHTUNG! MANIPULIERTER SPEICHER ERKANNT! Das System meldet $rep GB, der physische Flash-Chip hat jedoch nur $phys GB."
                else -> integrityMessageEn
            }
        } else {
            when (lang) {
                AppLanguage.RU -> integrityMessageRu
                AppLanguage.UA -> integrityMessageUa
                AppLanguage.ES -> "Chip físico $flashStorageType auténtico de $phys GB. Partición de datos: $rep GB ($freeStorageGb GB libres). Sin falsificación."
                AppLanguage.PT, AppLanguage.PT_BR -> "Chip físico $flashStorageType autêntico de $phys GB. Partição de dados: $rep GB ($freeStorageGb GB livres). Sem falsificação."
                AppLanguage.FR -> "Puce physique $flashStorageType authentique de $phys Go. Partition de données: $rep Go ($freeStorageGb Go libres). Aucune contrefaçon."
                AppLanguage.IT -> "Chip fisico $flashStorageType autentico da $phys GB. Partizione dati: $rep GB ($freeStorageGb GB liberi). Nessuna contraffazione."
                AppLanguage.DE -> "Authentischer physischer $flashStorageType-Chip mit $phys GB. Datenpartition: $rep GB ($freeStorageGb GB frei). Keine Manipulation."
                else -> integrityMessageEn
            }
        }
    }
}

data class BatteryAudit(
    val designCapacityMah: Int,
    val estimatedActualCapacityMah: Int,
    val healthPercentage: Int,
    val currentLevelPercent: Int,
    val cycleCount: Int?,
    val temperatureC: Float,
    val voltageMv: Int,
    val technology: String,
    val isCharging: Boolean,
    val healthSummaryRu: String,
    val healthSummaryUa: String,
    val healthSummaryEn: String
) {
    val wearPercentage: Int
        get() = (100 - healthPercentage).coerceAtLeast(0)

    val wearLossMah: Int
        get() = (designCapacityMah - estimatedActualCapacityMah).coerceAtLeast(0)

    fun getHealthSummary(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> healthSummaryRu
        AppLanguage.UA -> healthSummaryUa
        AppLanguage.ES -> "Salud de la batería: $healthPercentage%. Capacidad actual: $estimatedActualCapacityMah mAh de los $designCapacityMah mAh de fábrica (Degradación: -$wearLossMah mAh / -$wearPercentage%)${if (cycleCount != null) " [$cycleCount ciclos]" else ""}."
        AppLanguage.PT, AppLanguage.PT_BR -> "Saúde da bateria: $healthPercentage%. Capacidade retida: $estimatedActualCapacityMah mAh de $designCapacityMah mAh de fábrica (Desgaste: -$wearLossMah mAh / -$wearPercentage%)${if (cycleCount != null) " [$cycleCount ciclos]" else ""}."
        AppLanguage.FR -> "État de la batterie: $healthPercentage%. Capacité résiduelle: $estimatedActualCapacityMah mAh sur $designCapacityMah mAh d'origine (Usure: -$wearLossMah mAh / -$wearPercentage%)${if (cycleCount != null) " [$cycleCount cycles]" else ""}."
        AppLanguage.IT -> "Stato della batteria: $healthPercentage%. Capacità attuale: $estimatedActualCapacityMah mAh su $designCapacityMah mAh di fabbrica (Usura: -$wearLossMah mAh / -$wearPercentage%)${if (cycleCount != null) " [$cycleCount cicli]" else ""}."
        AppLanguage.DE -> "Akkugesundheit: $healthPercentage%. Tatsächliche Kapazität: $estimatedActualCapacityMah mAh von $designCapacityMah mAh Werksangabe (Verschleiß: -$wearLossMah mAh / -$wearPercentage%)${if (cycleCount != null) " [$cycleCount Zyklen]" else ""}."
        else -> healthSummaryEn
    }
}

data class RamAudit(
    val physicalRamGb: Double,
    val virtualRamGb: Double,
    val totalEffectiveRamGb: Double,
    val usedRamGb: Double,
    val availableRamGb: Double,
    val ramType: String,
    val isVirtualRamActive: Boolean,
    val isRamSpoofed: Boolean,
    val claimedConfiguration: String,
    val ramIntegrityMessageRu: String,
    val ramIntegrityMessageUa: String,
    val ramIntegrityMessageEn: String
) {
    fun getRamIntegrityMessage(lang: AppLanguage): String {
        val phys = physicalRamGb.toInt()
        val virt = virtualRamGb.toInt()
        return if (isRamSpoofed) {
            when (lang) {
                AppLanguage.RU -> ramIntegrityMessageRu
                AppLanguage.UA -> ramIntegrityMessageUa
                AppLanguage.ES -> "¡ATENCIÓN! ¡RAM FALSA DETECTADA (SPOOFING)! El sistema afirma tener más memoria, pero el silicio físico LPDDR real es de solo $phys GB (+ $virt GB virtuales)."
                AppLanguage.PT, AppLanguage.PT_BR -> "ATENÇÃO! MEMÓRIA RAM FALSIFICADA DETECTADA! O sistema alega mais memória, mas o módulo físico real LPDDR é de apenas $phys GB (+ $virt GB virtuais)."
                AppLanguage.FR -> "ATTENTION! RAM FALSIFIÉE DÉTECTÉE! Le système affiche une capacité supérieure, mais le module physique LPDDR ne fait que $phys Go (+ $virt Go virtuels)."
                AppLanguage.IT -> "ATTENZIONE! RAM CONTRAFFATTA RILEVATA! Il sistema dichiara una memoria maggiore, ma il silicio LPDDR fisico effettivo è di soli $phys GB (+ $virt GB virtuali)."
                AppLanguage.DE -> "ACHTUNG! MANIPULIERTER ARBEITSSPEICHER (RAM)! Das System gibt mehr Speicher an, das echte physische LPDDR-Modul hat jedoch nur $phys GB (+ $virt GB virtuell)."
                else -> ramIntegrityMessageEn
            }
        } else if (isVirtualRamActive) {
            when (lang) {
                AppLanguage.RU -> ramIntegrityMessageRu
                AppLanguage.UA -> ramIntegrityMessageUa
                AppLanguage.ES -> "Configuración honesta: $phys GB LPDDR física + $virt GB memoria virtual (ZRAM / RAM Plus) asignada del almacenamiento ($phys+$virt GB)."
                AppLanguage.PT, AppLanguage.PT_BR -> "Configuração autêntica: $phys GB LPDDR física + $virt GB memória virtual (ZRAM / RAM Plus) alocada do armazenamento ($phys+$virt GB)."
                AppLanguage.FR -> "Configuration authentique: $phys Go LPDDR physiques + $virt Go de RAM virtuelle (ZRAM / RAM Plus) alloués du stockage ($phys+$virt Go)."
                AppLanguage.IT -> "Configurazione autentica: $phys GB LPDDR fisici + $virt GB memoria virtuale (ZRAM / RAM Plus) allocati dallo storage ($phys+$virt GB)."
                AppLanguage.DE -> "Echte Speicherkonfiguration: $phys GB physisches LPDDR + $virt GB virtueller RAM (ZRAM / RAM Plus) aus dem Speicher ($phys+$virt GB)."
                else -> ramIntegrityMessageEn
            }
        } else {
            when (lang) {
                AppLanguage.RU -> ramIntegrityMessageRu
                AppLanguage.UA -> ramIntegrityMessageUa
                AppLanguage.ES -> "Memoria física de hardware auténtica: $phys GB LPDDR. Memoria virtual ZRAM desactivada. Sin falsificación."
                AppLanguage.PT, AppLanguage.PT_BR -> "Memória física de hardware autêntica: $phys GB LPDDR. Memória virtual ZRAM desativada. Sem falsificação."
                AppLanguage.FR -> "Mémoire matérielle physique authentique: $phys Go LPDDR. Mémoire virtuelle ZRAM désactivée."
                AppLanguage.IT -> "Memoria fisica autentica: $phys GB LPDDR. Memoria virtuale ZRAM disabilitata."
                AppLanguage.DE -> "Echter physischer Arbeitsspeicher: $phys GB LPDDR. Virtuelle ZRAM-Auslagerung ist deaktiviert."
                else -> ramIntegrityMessageEn
            }
        }
    }
}

data class WinlatorAudit(
    val ratingStars: String,
    val ratingLabelRu: String,
    val ratingLabelUa: String,
    val ratingLabelEn: String,
    val turnipDriverSupported: Boolean,
    val box64Supported: Boolean,
    val recommendedDriver: String,
    val dxvkSupported: Boolean,
    val explanationRu: String,
    val explanationUa: String,
    val explanationEn: String,
    val playableGamesRu: String,
    val playableGamesUa: String,
    val playableGamesEn: String
) {
    fun getRatingLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> ratingLabelRu
        AppLanguage.UA -> ratingLabelUa
        AppLanguage.ES -> if (turnipDriverSupported) "Excelente para emulación de PC (Adreno Turnip)" else "Emulación básica de PC"
        AppLanguage.PT, AppLanguage.PT_BR -> if (turnipDriverSupported) "Excelente para emulação de PC (Adreno Turnip)" else "Emulação básica de PC"
        AppLanguage.FR -> if (turnipDriverSupported) "Excellente compatibilité émulation PC (Adreno Turnip)" else "Émulation PC basique"
        AppLanguage.IT -> if (turnipDriverSupported) "Eccellente per emulazione PC (Adreno Turnip)" else "Emulazione PC di base"
        AppLanguage.DE -> if (turnipDriverSupported) "Hervorragend für PC-Emulation (Adreno Turnip)" else "Grundlegende PC-Emulation"
        else -> ratingLabelEn
    }

    fun getExplanation(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> explanationRu
        AppLanguage.UA -> explanationUa
        AppLanguage.ES -> if (turnipDriverSupported) "Soporta controladores Turnip y traducción Box64 con aceleración Direct3D / Vulkan nativa." else "Controladores de gráficos propietarios sin soporte Turnip abierto. Rendimiento moderado."
        AppLanguage.PT, AppLanguage.PT_BR -> if (turnipDriverSupported) "Suporta drivers Turnip e tradução Box64 com aceleração nativa Direct3D / Vulkan." else "Drivers proprietários sem suporte Turnip. Desempenho moderado."
        AppLanguage.FR -> if (turnipDriverSupported) "Prend en charge les pilotes Turnip et la traduction Box64 avec accélération Vulkan native." else "Pilotes propriétaires sans support Turnip libre. Performances modérées."
        AppLanguage.IT -> if (turnipDriverSupported) "Supporta driver Turnip e traduzione Box64 con accelerazione Vulkan nativa." else "Driver proprietari senza supporto Turnip. Prestazioni moderate."
        AppLanguage.DE -> if (turnipDriverSupported) "Unterstützt Turnip-Treiber und Box64-Übersetzung mit nativer Vulkan-Beschleunigung." else "Proprietäre Grafiktreiber ohne Turnip-Unterstützung. Mäßige Leistung."
        else -> explanationEn
    }

    fun getPlayableGames(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> playableGamesRu
        AppLanguage.UA -> playableGamesUa
        AppLanguage.ES -> if (turnipDriverSupported) "GTA V, Skyrim, Fallout 4, The Witcher 3, Far Cry 3 (30-60 FPS)" else "Juegos clásicos 2D/3D (Need for Speed MW, Half-Life 2, Oblivion)"
        AppLanguage.PT, AppLanguage.PT_BR -> if (turnipDriverSupported) "GTA V, Skyrim, Fallout 4, The Witcher 3, Far Cry 3 (30-60 FPS)" else "Jogos clássicos 2D/3D (Need for Speed MW, Half-Life 2, Oblivion)"
        AppLanguage.FR -> if (turnipDriverSupported) "GTA V, Skyrim, Fallout 4, The Witcher 3, Far Cry 3 (30-60 FPS)" else "Jeux classiques 2D/3D (Need for Speed MW, Half-Life 2, Oblivion)"
        AppLanguage.IT -> if (turnipDriverSupported) "GTA V, Skyrim, Fallout 4, The Witcher 3, Far Cry 3 (30-60 FPS)" else "Giochi classici 2D/3D (Need for Speed MW, Half-Life 2, Oblivion)"
        AppLanguage.DE -> if (turnipDriverSupported) "GTA V, Skyrim, Fallout 4, The Witcher 3, Far Cry 3 (30-60 FPS)" else "Klassische 2D/3D-Spiele (Need for Speed MW, Half-Life 2, Oblivion)"
        else -> playableGamesEn
    }
}

data class AntutuAudit(
    val estimatedTotalScore: Int,
    val cpuScore: Int,
    val gpuScore: Int,
    val memScore: Int,
    val uxScore: Int,
    val tierLabelRu: String,
    val tierLabelUa: String,
    val tierLabelEn: String,
    val comparisonNoteRu: String,
    val comparisonNoteUa: String,
    val comparisonNoteEn: String
) {
    fun getTierLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> tierLabelRu
        AppLanguage.UA -> tierLabelUa
        AppLanguage.ES -> when {
            estimatedTotalScore >= 1_500_000 -> "Nivel Flagship Ultra (Máximo rendimiento)"
            estimatedTotalScore >= 900_000 -> "Nivel Sub-Flagship / Gama alta"
            estimatedTotalScore >= 500_000 -> "Nivel Gama media sólida"
            else -> "Nivel Entrada / Básico"
        }
        AppLanguage.PT, AppLanguage.PT_BR -> when {
            estimatedTotalScore >= 1_500_000 -> "Nível Flagship Ultra (Desempenho máximo)"
            estimatedTotalScore >= 900_000 -> "Nível Sub-Flagship / Alto desempenho"
            estimatedTotalScore >= 500_000 -> "Nível Intermediário sólido"
            else -> "Nível de Entrada / Básico"
        }
        AppLanguage.FR -> when {
            estimatedTotalScore >= 1_500_000 -> "Niveau Flagship Ultra (Performance maximale)"
            estimatedTotalScore >= 900_000 -> "Niveau Haut de gamme / Sub-Flagship"
            estimatedTotalScore >= 500_000 -> "Niveau Milieu de gamme équilibré"
            else -> "Niveau Entrée de gamme"
        }
        AppLanguage.IT -> when {
            estimatedTotalScore >= 1_500_000 -> "Livello Flagship Ultra (Massime prestazioni)"
            estimatedTotalScore >= 900_000 -> "Livello Fascia alta / Sub-Flagship"
            estimatedTotalScore >= 500_000 -> "Livello Medio di gamma"
            else -> "Livello Base / Entry-level"
        }
        AppLanguage.DE -> when {
            estimatedTotalScore >= 1_500_000 -> "Flaggschiff-Klasse Ultra (Höchstleistung)"
            estimatedTotalScore >= 900_000 -> "Oberklasse / Sub-Flaggschiff"
            estimatedTotalScore >= 500_000 -> "Solide Mittelklasse"
            else -> "Einsteigerklasse"
        }
        else -> tierLabelEn
    }

    fun getComparisonNote(lang: AppLanguage): String = when (lang) {
        AppLanguage.RU -> comparisonNoteRu
        AppLanguage.UA -> comparisonNoteUa
        AppLanguage.ES -> "Puntuación estimada en benchmark AnTuTu v10 basada en la arquitectura del SoC."
        AppLanguage.PT, AppLanguage.PT_BR -> "Pontuação estimada no benchmark AnTuTu v10 baseada na arquitetura do SoC."
        AppLanguage.FR -> "Score estimé au benchmark AnTuTu v10 basé sur l'architecture du SoC."
        AppLanguage.IT -> "Punteggio stimato benchmark AnTuTu v10 basato sull'architettura del SoC."
        AppLanguage.DE -> "Geschätzte AnTuTu v10 Benchmark-Punktzahl basierend auf der SoC-Architektur."
        else -> comparisonNoteEn
    }
}

data class DeviceHardwareAudit(
    val cpu: CpuAudit,
    val storage: StorageAudit,
    val battery: BatteryAudit,
    val ram: RamAudit,
    val winlator: WinlatorAudit,
    val antutu: AntutuAudit
)
