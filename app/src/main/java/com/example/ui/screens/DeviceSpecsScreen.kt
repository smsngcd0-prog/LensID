package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.SdStorage
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localization.AppLanguage
import com.example.model.DeviceHardwareAudit
import com.example.ui.CameraUiState
import com.example.ui.components.StatusBadge
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.PurpleAccent
import com.example.ui.theme.TechNavyDark
import com.example.ui.theme.TechSurfaceDark
import com.example.ui.theme.TechSurfaceVariantDark

@Composable
fun DeviceSpecsScreen(
    state: CameraUiState,
    audit: DeviceHardwareAudit?,
    modifier: Modifier = Modifier
) {
    val lang = state.appLanguage
    val isRu = lang == AppLanguage.RU
    val isUa = lang == AppLanguage.UA

    if (audit == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = CyanAccent)
                Spacer(modifier = Modifier.height(14.dp))
                val loadText = if (isRu) "Анализ аппаратных параметров SoC, памяти и батареи..."
                else if (isUa) "Аналіз апаратних параметрів SoC, пам'яті та батареї..."
                else "Auditing hardware SoC, storage, battery, and Winlator..."
                Text(
                    text = loadText,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // HERO HEADER: Device Marketing Name & AnTuTu Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF0F172A), Color(0xFF1E293B), Color(0xFF0F2027))
                        )
                    )
                    .border(1.dp, CyanAccent.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                    .padding(18.dp)
                    .testTag("specs_hero_banner")
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            val headerLabel = if (isRu) "Аппаратный аудит смартфона"
                            else if (isUa) "Апаратний аудит смартфона"
                            else "Hardware Device Audit"
                            Text(
                                text = headerLabel,
                                fontSize = 11.sp,
                                color = CyanAccent,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = state.deviceInfo?.fullBrandTitle ?: "Смартфон",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                            Text(
                                text = "${state.deviceInfo?.model ?: ""} • ${audit.cpu.architecture}",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }

                        // AnTuTu Total Score pill
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Brush.linearGradient(listOf(Color(0xFFDC2626), Color(0xFFEA580C))))
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "AnTuTu v10",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                                Text(
                                    text = "${"%,d".format(audit.antutu.estimatedTotalScore)}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

        // 1. REAL CPU & SOC CARD
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, CyanAccent.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                    .testTag("specs_cpu_card"),
                colors = CardDefaults.cardColors(containerColor = TechSurfaceDark),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.DeveloperBoard,
                                contentDescription = "CPU",
                                tint = CyanAccent,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            val cpuTitle = if (isRu) "Реальный Процессор (SoC)"
                            else if (isUa) "Реальний Процесор (SoC)"
                            else "Real Processor (SoC)"
                            Text(
                                text = cpuTitle,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        StatusBadge(
                            text = audit.cpu.vendor,
                            color = CyanAccent
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = audit.cpu.realSocName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = CyanAccent
                    )

                    Text(
                        text = "Техпроцесс: ${audit.cpu.processNodeNm}",
                        fontSize = 12.sp,
                        color = EmeraldGreen,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Cores & GPU grid
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(TechSurfaceVariantDark.copy(alpha = 0.45f), RoundedCornerShape(10.dp))
                            .padding(12.dp)
                    ) {
                        val gpuLabel = if (isRu) "Графический ускоритель (GPU):"
                        else if (isUa) "Графічний прискорювач (GPU):"
                        else "Graphics Processor (GPU):"
                        Text(
                            text = gpuLabel,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PurpleAccent
                        )
                        Text(
                            text = audit.cpu.gpuModel,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        val coresLabel = if (isRu) "Кластеры и ядра:"
                        else if (isUa) "Кластери та ядра:"
                        else "Cores & Clusters:"
                        Text(
                            text = "$coresLabel (${audit.cpu.coreCount} Cores)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = audit.cpu.coreConfiguration,
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }

        // 2. REAL STORAGE & ANTI-SPOOFING CARD
        item {
            val isSpoofed = audit.storage.isSpoofed
            val storageBorderColor = if (isSpoofed) Color(0xFFEF4444) else EmeraldGreen

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, storageBorderColor.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    .testTag("specs_storage_card"),
                colors = CardDefaults.cardColors(containerColor = TechSurfaceDark),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.SdStorage,
                                contentDescription = "Storage",
                                tint = storageBorderColor,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            val storageTitle = if (isRu) "Реальная память (ПЗУ / Flash)"
                            else if (isUa) "Реальна пам'ять (ПЗП / Flash)"
                            else "Physical Storage (NAND Flash)"
                            Text(
                                text = storageTitle,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        StatusBadge(
                            text = audit.storage.flashStorageType,
                            color = storageBorderColor
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            val physLabel = if (isRu) "Физический чип NAND"
                            else if (isUa) "Фізичний чип NAND"
                            else "Silicon NAND Chip"
                            Text(physLabel, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                text = "${audit.storage.physicalChipCapacityGb.toInt()} ГБ",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = storageBorderColor
                            )
                        }

                        Column {
                            val partLabel = if (isRu) "Раздел Data (ОС)"
                            else if (isUa) "Розділ Data (ОС)"
                            else "Android Data Partition"
                            Text(partLabel, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                text = "${audit.storage.reportedTotalStorageGb.toInt()} ГБ",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Column {
                            val freeLabel = if (isRu) "Свободно"
                            else if (isUa) "Вільно"
                            else "Available"
                            Text(freeLabel, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                text = "${audit.storage.freeStorageGb} ГБ",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = CyanAccent
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Integrity verification box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                if (isSpoofed) Color(0xFF7F1D1D).copy(alpha = 0.35f)
                                else EmeraldGreen.copy(alpha = 0.15f)
                            )
                            .border(
                                1.dp,
                                if (isSpoofed) Color(0xFFEF4444) else EmeraldGreen.copy(alpha = 0.5f),
                                RoundedCornerShape(10.dp)
                            )
                            .padding(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isSpoofed) Icons.Default.Warning else Icons.Default.CheckCircle,
                                contentDescription = "Status",
                                tint = if (isSpoofed) Color(0xFFEF4444) else EmeraldGreen,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = audit.storage.getIntegrityMessage(lang),
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        // 3. BATTERY HEALTH & REAL MAH CARD
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, AmberWarning.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    .testTag("specs_battery_card"),
                colors = CardDefaults.cardColors(containerColor = TechSurfaceDark),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.BatteryChargingFull,
                                contentDescription = "Battery",
                                tint = AmberWarning,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            val batTitle = if (isRu) "Износ и здоровье батареи"
                            else if (isUa) "Знос та здоров'я батареї"
                            else "Battery Wear & Health"
                            Text(
                                text = batTitle,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        StatusBadge(
                            text = "${audit.battery.healthPercentage}% HEALTH",
                            color = if (audit.battery.healthPercentage >= 85) EmeraldGreen else AmberWarning
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Health Progress Bar
                    LinearProgressIndicator(
                        progress = { audit.battery.healthPercentage / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = if (audit.battery.healthPercentage >= 85) EmeraldGreen else AmberWarning,
                        trackColor = TechSurfaceVariantDark
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            val curLabel = if (isRu) "Фактическая ёмкость"
                            else if (isUa) "Фактична ємність"
                            else "Hold Capacity"
                            Text(curLabel, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                text = "${audit.battery.estimatedActualCapacityMah} мАч",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = AmberWarning
                            )
                        }

                        Column {
                            val desLabel = if (isRu) "Заводская ёмкость"
                            else if (isUa) "Заводська ємність"
                            else "Design Capacity"
                            Text(desLabel, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                text = "${audit.battery.designCapacityMah} мАч",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Column {
                            val chargeLabel = if (isRu) "Текущий заряд"
                            else if (isUa) "Поточний заряд"
                            else "Current Charge"
                            Text(chargeLabel, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                text = "${audit.battery.currentLevelPercent}%",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = CyanAccent
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Wear Loss & Health clarification banner
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(TechSurfaceVariantDark.copy(alpha = 0.5f))
                            .border(1.dp, AmberWarning.copy(alpha = 0.35f), RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                val wearLabel = if (isRu) "Износ (потеря ёмкости):"
                                else if (isUa) "Знос (втрата ємності):"
                                else "Wear Loss:"
                                Text(
                                    text = wearLabel,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFF87171)
                                )
                                Text(
                                    text = "-${audit.battery.wearLossMah} мАч (-${audit.battery.wearPercentage}%)",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFFF87171)
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                val healthRemLabel = if (isRu) "Остаток ресурса батареи:"
                                else if (isUa) "Залишок ресурсу батареї:"
                                else "Remaining Lifespan:"
                                Text(
                                    text = healthRemLabel,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "${audit.battery.healthPercentage}% (из 100%)",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (audit.battery.healthPercentage >= 85) EmeraldGreen else AmberWarning
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Battery details line
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(TechSurfaceVariantDark.copy(alpha = 0.45f), RoundedCornerShape(10.dp))
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val cycLabel = if (isRu) "Циклы" else if (isUa) "Цикли" else "Cycles"
                        Text(
                            text = "$cycLabel: ${audit.battery.cycleCount ?: "N/A"}",
                            fontSize = 11.sp,
                            color = Color.White
                        )
                        Text(
                            text = "${audit.battery.temperatureC}°C",
                            fontSize = 11.sp,
                            color = CyanAccent,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${audit.battery.voltageMv} mV",
                            fontSize = 11.sp,
                            color = Color.White
                        )
                        Text(
                            text = audit.battery.technology,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = audit.battery.getHealthSummary(lang),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // 4. REAL RAM & VIRTUAL RAM (RAM PLUS) CARD
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, PurpleAccent.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    .testTag("specs_ram_card"),
                colors = CardDefaults.cardColors(containerColor = TechSurfaceDark),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Memory,
                                contentDescription = "RAM",
                                tint = PurpleAccent,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            val ramTitle = if (isRu) "Оперативная память (ОЗУ)"
                            else if (isUa) "Оперативна пам'ять (ОЗП)"
                            else "System Memory (RAM)"
                            Text(
                                text = ramTitle,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        StatusBadge(
                            text = "${audit.ram.totalEffectiveRamGb.toInt()} GB TOTAL",
                            color = PurpleAccent
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            val physRam = if (isRu) "Физическая LPDDR"
                            else if (isUa) "Фізична LPDDR"
                            else "Physical LPDDR"
                            Text(physRam, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                text = "${audit.ram.physicalRamGb.toInt()} ГБ",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = PurpleAccent
                            )
                            Text(audit.ram.ramType, fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
                        }

                        Column {
                            val virtRam = if (isRu) "Виртуальная (ZRAM / RAM+)"
                            else if (isUa) "Віртуальна (ZRAM / RAM+)"
                            else "Virtual RAM (ZRAM)"
                            Text(virtRam, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                text = "+${audit.ram.virtualRamGb.toInt()} ГБ",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = CyanAccent
                            )
                            val fromRom = if (isRu) "выделено из ПЗУ" else if (isUa) "виділено з ПЗП" else "allocated from flash"
                            Text(fromRom, fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
                        }

                        Column {
                            val availRam = if (isRu) "Свободно"
                            else if (isUa) "Вільно"
                            else "Available"
                            Text(availRam, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                text = "${audit.ram.availableRamGb} ГБ",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldGreen
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // RAM Integrity / Anti-spoofing box
                    val isRamSpoofed = audit.ram.isRamSpoofed
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                if (isRamSpoofed) Color(0xFF7F1D1D).copy(alpha = 0.35f)
                                else PurpleAccent.copy(alpha = 0.15f)
                            )
                            .border(
                                1.dp,
                                if (isRamSpoofed) Color(0xFFEF4444) else PurpleAccent.copy(alpha = 0.5f),
                                RoundedCornerShape(10.dp)
                            )
                            .padding(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isRamSpoofed) Icons.Default.Warning else Icons.Default.CheckCircle,
                                contentDescription = "RAM Status",
                                tint = if (isRamSpoofed) Color(0xFFEF4444) else EmeraldGreen,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = audit.ram.getRamIntegrityMessage(lang),
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        // 5. WINLATOR & WINDOWS PC EMULATION COMPATIBILITY
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, CyanAccent.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    .testTag("specs_winlator_card"),
                colors = CardDefaults.cardColors(containerColor = TechSurfaceDark),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Gamepad,
                                contentDescription = "Winlator",
                                tint = CyanAccent,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Winlator / Windows PC",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Text(
                            text = audit.winlator.ratingStars,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = audit.winlator.getRatingLabel(lang),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = CyanAccent
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = audit.winlator.getExplanation(lang),
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Winlator badges
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        StatusBadge(
                            text = if (audit.winlator.turnipDriverSupported) "MESA TURNIP: YES" else "TURNIP: NO",
                            color = if (audit.winlator.turnipDriverSupported) EmeraldGreen else AmberWarning
                        )
                        StatusBadge(
                            text = if (audit.winlator.box64Supported) "BOX64 64-BIT" else "BOX86",
                            color = CyanAccent
                        )
                        StatusBadge(
                            text = audit.winlator.recommendedDriver,
                            color = PurpleAccent
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Playable games section
                    val gamesHeader = if (isRu) "Примеры поддерживаемых ПК-игр:"
                    else if (isUa) "Приклади підтримуваних ПК-ігор:"
                    else "Verified Playable PC Titles:"
                    Text(
                        text = gamesHeader,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldGreen
                    )
                    Text(
                        text = audit.winlator.getPlayableGames(lang),
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }
        }

        // 6. ANTUTU BENCHMARK SCORE BREAKDOWN CARD
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Color(0xFFEA580C).copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .testTag("specs_antutu_card"),
                colors = CardDefaults.cardColors(containerColor = TechSurfaceDark),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Speed,
                                contentDescription = "AnTuTu",
                                tint = Color(0xFFEA580C),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "AnTuTu Benchmark v10",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Text(
                            text = "${"%,d".format(audit.antutu.estimatedTotalScore)}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFFEA580C)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = audit.antutu.getTierLabel(lang),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AmberWarning
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // AnTuTu Breakdown: CPU, GPU, MEM, UX
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("CPU", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${"%,d".format(audit.antutu.cpuScore)}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = CyanAccent)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("GPU", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${"%,d".format(audit.antutu.gpuScore)}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = EmeraldGreen)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("MEM", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${"%,d".format(audit.antutu.memScore)}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PurpleAccent)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("UX", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${"%,d".format(audit.antutu.uxScore)}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AmberWarning)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = audit.antutu.getComparisonNote(lang),
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }
        }
    }
}
