package com.example.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SdStorage
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hardware.StressTestState
import com.example.localization.AppLanguage
import com.example.localization.AppStrings
import com.example.model.DeviceHardwareAudit
import com.example.model.ScreenAuditStatus
import com.example.ui.CameraUiState
import com.example.ui.CameraViewModel
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
    viewModel: CameraViewModel? = null,
    modifier: Modifier = Modifier
) {
    val lang = state.appLanguage
    val isRu = lang == AppLanguage.RU
    val isUa = lang == AppLanguage.UA
    val stressState = viewModel?.stressTestState?.collectAsStateWithLifecycle()?.value ?: StressTestState()

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
                            val headerLabel = when (lang) {
                                AppLanguage.RU -> "Аппаратный аудит смартфона"
                                AppLanguage.UA -> "Апаратний аудит смартфона"
                                AppLanguage.ES -> "Auditoría de hardware del dispositivo"
                                AppLanguage.PT, AppLanguage.PT_BR -> "Auditoria de hardware do dispositivo"
                                AppLanguage.FR -> "Audit matériel du smartphone"
                                AppLanguage.IT -> "Audit hardware dello smartphone"
                                AppLanguage.DE -> "Hardware-Audit des Smartphones"
                                else -> "Hardware Device Audit"
                            }
                            Text(
                                text = headerLabel,
                                fontSize = 11.sp,
                                color = CyanAccent,
                                fontWeight = FontWeight.Bold
                            )
                            val modern = state.modernDeviceSpecs
                            val deviceTitle = if (modern.isOnlineSuccess && modern.onlineMarketingModel != null) {
                                modern.onlineMarketingModel
                            } else {
                                state.deviceInfo?.fullBrandTitle ?: "Смартфон"
                            }
                            Text(
                                text = deviceTitle,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )

                            if (modern.isOnlineSuccess && modern.onlineMarketingModel != null && state.deviceInfo != null) {
                                Text(
                                    text = "(${AppStrings.getOfflineLabel(lang)}: ${state.deviceInfo.fullBrandTitle})",
                                    fontSize = 11.sp,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                            } else if (modern.searchFailed) {
                                Text(
                                    text = "(${AppStrings.getNetworkSearchFailedNote(lang)})",
                                    fontSize = 11.sp,
                                    color = AmberWarning
                                )
                            } else if (!modern.isEnabled) {
                                Text(
                                    text = "(${AppStrings.getOnlineSearchDisabledNote(lang)})",
                                    fontSize = 11.sp,
                                    color = Color.White.copy(alpha = 0.6f)
                                )
                            } else {
                                Text(
                                    text = "${state.deviceInfo?.model ?: ""} • ${audit.cpu.architecture}",
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                            }
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

        // SCREEN & DISPLAY AUDIT CARD (Hz, Resolution, K-Rating, Discrepancy & Spoofing Check)
        item {
            val screen = audit.screen
            val screenBorderColor = when (screen.statusType) {
                ScreenAuditStatus.OK -> CyanAccent
                ScreenAuditStatus.SCALED_NORMAL -> AmberWarning
                ScreenAuditStatus.SPOOFED_ALERT -> Color(0xFFEF4444)
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, screenBorderColor.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    .testTag("specs_screen_card"),
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
                                imageVector = Icons.Default.Tv,
                                contentDescription = "Screen",
                                tint = screenBorderColor,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = AppStrings.getScreenSectionTitle(lang),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        StatusBadge(
                            text = "${screen.reportedRefreshRate.toInt()} ГЦ • ${screen.resolutionLabel}",
                            color = screenBorderColor
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Key Screen Metrics Grid: Hz, Resolution, K-Factor
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = AppStrings.getScreenRefreshRateLabel(lang),
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "${screen.reportedRefreshRate.toInt()} Гц",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = screenBorderColor
                            )
                        }

                        Column {
                            Text(
                                text = AppStrings.getScreenResolutionLabel(lang),
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "${screen.currentWidth}×${screen.currentHeight}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Column {
                            Text(
                                text = AppStrings.getScreenKRatingLabel(lang),
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = screen.resolutionLabel,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = PurpleAccent
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Стандарт: ${screen.standardName} • Плотность: ${screen.densityDpi} DPI",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.75f)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Verification & Discrepancy Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                when (screen.statusType) {
                                    ScreenAuditStatus.OK -> EmeraldGreen.copy(alpha = 0.15f)
                                    ScreenAuditStatus.SCALED_NORMAL -> AmberWarning.copy(alpha = 0.15f)
                                    ScreenAuditStatus.SPOOFED_ALERT -> Color(0xFF7F1D1D).copy(alpha = 0.35f)
                                }
                            )
                            .border(
                                1.dp,
                                when (screen.statusType) {
                                    ScreenAuditStatus.OK -> EmeraldGreen.copy(alpha = 0.5f)
                                    ScreenAuditStatus.SCALED_NORMAL -> AmberWarning.copy(alpha = 0.5f)
                                    ScreenAuditStatus.SPOOFED_ALERT -> Color(0xFFEF4444)
                                },
                                RoundedCornerShape(10.dp)
                            )
                            .padding(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = when (screen.statusType) {
                                    ScreenAuditStatus.OK -> Icons.Default.CheckCircle
                                    ScreenAuditStatus.SCALED_NORMAL -> Icons.Default.Warning
                                    ScreenAuditStatus.SPOOFED_ALERT -> Icons.Default.Warning
                                },
                                contentDescription = "Screen Status",
                                tint = when (screen.statusType) {
                                    ScreenAuditStatus.OK -> EmeraldGreen
                                    ScreenAuditStatus.SCALED_NORMAL -> AmberWarning
                                    ScreenAuditStatus.SPOOFED_ALERT -> Color(0xFFEF4444)
                                },
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = screen.getIntegrityMessage(lang),
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                color = Color.White
                            )
                        }
                    }

                    if (screen.supportedModes.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Поддерживаемые режимы матрицы: ${screen.supportedModes.take(4).joinToString(", ")}",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
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
                            val cpuTitle = when (lang) {
                                AppLanguage.RU -> "Реальный Процессор (SoC)"
                                AppLanguage.UA -> "Реальний Процесор (SoC)"
                                AppLanguage.ES -> "Procesador real (SoC)"
                                AppLanguage.PT, AppLanguage.PT_BR -> "Processador real (SoC)"
                                AppLanguage.FR -> "Processeur réel (SoC)"
                                AppLanguage.IT -> "Processore reale (SoC)"
                                AppLanguage.DE -> "Echter Prozessor (SoC)"
                                else -> "Real Processor (SoC)"
                            }
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

                    val modern = state.modernDeviceSpecs
                    if (modern.isOnlineSuccess && modern.onlineSocTitle != null) {
                        Text(
                            text = modern.onlineSocTitle,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = CyanAccent
                        )
                        Text(
                            text = "(${AppStrings.getOfflineLabel(lang)}: ${audit.cpu.realSocName})",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.75f),
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        StatusBadge(
                            text = "🌐 ${AppStrings.getOnlineBadge(lang)}: ${modern.sourceProvider}",
                            color = EmeraldGreen
                        )
                    } else if (modern.searchFailed) {
                        Text(
                            text = audit.cpu.realSocName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = CyanAccent
                        )
                        Text(
                            text = "(${AppStrings.getNetworkSearchFailedNote(lang)})",
                            fontSize = 12.sp,
                            color = AmberWarning,
                            fontWeight = FontWeight.Normal
                        )
                    } else if (!modern.isEnabled) {
                        Text(
                            text = audit.cpu.realSocName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = CyanAccent
                        )
                        Text(
                            text = "(${AppStrings.getOnlineSearchDisabledNote(lang)})",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        Text(
                            text = audit.cpu.realSocName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = CyanAccent
                        )
                    }

                    val processLabel = when (lang) {
                        AppLanguage.RU -> "Техпроцесс:"
                        AppLanguage.UA -> "Техпроцес:"
                        AppLanguage.ES -> "Proceso:"
                        AppLanguage.PT, AppLanguage.PT_BR -> "Litografia:"
                        AppLanguage.FR -> "Finesse de gravure:"
                        AppLanguage.IT -> "Processo produttivo:"
                        AppLanguage.DE -> "Fertigungsprozess:"
                        else -> "Fabrication Node:"
                    }
                    Text(
                        text = "$processLabel ${audit.cpu.processNodeNm}",
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

                    if (audit.storage.hasExternalSdCard && audit.storage.externalSdCardTotalGb != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(TechSurfaceVariantDark.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = AppStrings.getExternalSdCardLabel(lang),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CyanAccent
                                )
                                Text(
                                    text = "${audit.storage.externalSdCardTotalGb.toInt()} ГБ MicroSD / Flash (Свободно: ${audit.storage.externalSdCardFreeGb ?: 0.0} ГБ)",
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }
                            StatusBadge(
                                text = "MicroSD Flash",
                                color = CyanAccent
                            )
                        }
                    }
                }
            }
        }

        // 3. CPU 2-MINUTE THROTTLING STRESS TEST CARD (Requirement 2)
        item {
            val isRunning = stressState.isRunning
            val isFinished = stressState.isFinished
            val stressBorderColor = when {
                isRunning -> AmberWarning
                isFinished && stressState.verdictScore >= 85 -> EmeraldGreen
                isFinished -> Color(0xFFEF4444)
                else -> CyanAccent
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, stressBorderColor.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .testTag("specs_stress_test_card"),
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
                                contentDescription = "Stress Test",
                                tint = stressBorderColor,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = AppStrings.getStressTestTitle(lang),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        val timeSec = stressState.elapsedSeconds
                        val timeStr = String.format(java.util.Locale.US, "%02d:%02d", timeSec / 60, timeSec % 60)
                        StatusBadge(
                            text = if (isRunning) "ТЕСТ: $timeStr / 02:00" else if (isFinished) "УСТОЙЧИВОСТЬ ${stressState.verdictScore}%" else "ГОТОВ",
                            color = stressBorderColor
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = AppStrings.getStressTestSubtitle(lang),
                        fontSize = 11.sp,
                        lineHeight = 15.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Progress Bar for 120 Seconds
                    LinearProgressIndicator(
                        progress = { (stressState.elapsedSeconds / 120f).coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = stressBorderColor,
                        trackColor = TechSurfaceVariantDark
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Live Metrics 4-Box Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(AppStrings.getCurrentGipsLabel(lang), fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                text = "${stressState.currentGips}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(AppStrings.getPeakGipsLabel(lang), fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                text = "${stressState.peakGips}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = CyanAccent
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(AppStrings.getThrottlingLabel(lang), fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            val tColor = when {
                                stressState.currentThrottlePercent >= 90 -> EmeraldGreen
                                stressState.currentThrottlePercent >= 78 -> AmberWarning
                                else -> Color(0xFFEF4444)
                            }
                            Text(
                                text = "${stressState.currentThrottlePercent}%",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = tColor
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(AppStrings.getTemperatureLabel(lang), fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                text = "${stressState.currentTempC}°C",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (stressState.currentTempC > 42f) Color(0xFFEF4444) else AmberWarning
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Dynamic 120-Second Canvas Graph
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF070E1A))
                            .border(1.dp, TechSurfaceVariantDark.copy(alpha = 0.6f), RoundedCornerShape(10.dp))
                            .padding(8.dp)
                    ) {
                        val points = stressState.points
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height

                            // Draw reference guide lines (100%, 80%, 60%)
                            val line100 = h * 0.05f
                            val line80 = h * 0.35f
                            val line60 = h * 0.65f

                            drawLine(Color.White.copy(alpha = 0.1f), Offset(0f, line100), Offset(w, line100), strokeWidth = 1.dp.toPx())
                            drawLine(Color.White.copy(alpha = 0.1f), Offset(0f, line80), Offset(w, line80), strokeWidth = 1.dp.toPx())
                            drawLine(Color.White.copy(alpha = 0.1f), Offset(0f, line60), Offset(w, line60), strokeWidth = 1.dp.toPx())

                            if (points.isNotEmpty()) {
                                val path = Path()
                                points.forEachIndexed { index, pt ->
                                    val x = (pt.second / 120f) * w
                                    // Y maps 0-100% throttle to height (100% -> top, 0% -> bottom)
                                    val y = h - ((pt.throttlePercent / 100f) * (h - 10.dp.toPx())) - 5.dp.toPx()
                                    if (index == 0) {
                                        path.moveTo(x, y)
                                    } else {
                                        path.lineTo(x, y)
                                    }
                                }

                                val strokeColor = when {
                                    stressState.currentThrottlePercent >= 90 -> Color(0xFF10B981)
                                    stressState.currentThrottlePercent >= 78 -> Color(0xFFF59E0B)
                                    else -> Color(0xFFEF4444)
                                }

                                drawPath(
                                    path = path,
                                    color = strokeColor,
                                    style = Stroke(width = 2.5.dp.toPx())
                                )

                                // Current head circle
                                val lastPt = points.last()
                                val lastX = (lastPt.second / 120f) * w
                                val lastY = h - ((lastPt.throttlePercent / 100f) * (h - 10.dp.toPx())) - 5.dp.toPx()
                                drawCircle(
                                    color = Color.White,
                                    radius = 4.dp.toPx(),
                                    center = Offset(lastX, lastY)
                                )
                                drawCircle(
                                    color = strokeColor,
                                    radius = 2.5.dp.toPx(),
                                    center = Offset(lastX, lastY)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Buttons row: Start / Stop / Reset
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (!isRunning) {
                            Button(
                                onClick = { viewModel?.startStressTest() },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("start_stress_test_button"),
                                colors = ButtonDefaults.buttonColors(containerColor = CyanAccent)
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Start", tint = Color(0xFF00363D))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = AppStrings.getStartStressTest(lang),
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF00363D),
                                    fontSize = 12.sp
                                )
                            }
                        } else {
                            Button(
                                onClick = { viewModel?.stopStressTest() },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("stop_stress_test_button"),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))
                            ) {
                                Icon(Icons.Default.Stop, contentDescription = "Stop", tint = Color.White)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = AppStrings.getStopStressTest(lang),
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        OutlinedButton(
                            onClick = { viewModel?.resetStressTest() },
                            modifier = Modifier.testTag("reset_stress_test_button")
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = "Reset", tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = AppStrings.getResetStressTest(lang),
                                fontSize = 11.sp,
                                color = Color.White
                            )
                        }
                    }

                    // Verdict Box (shows when test is completed or stopped)
                    if (isFinished && stressState.verdictRu.isNotBlank()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    if (stressState.verdictScore >= 85) EmeraldGreen.copy(alpha = 0.15f)
                                    else if (stressState.verdictScore >= 75) AmberWarning.copy(alpha = 0.15f)
                                    else Color(0xFF7F1D1D).copy(alpha = 0.35f)
                                )
                                .border(
                                    1.dp,
                                    if (stressState.verdictScore >= 85) EmeraldGreen.copy(alpha = 0.5f)
                                    else if (stressState.verdictScore >= 75) AmberWarning.copy(alpha = 0.5f)
                                    else Color(0xFFEF4444),
                                    RoundedCornerShape(10.dp)
                                )
                                .padding(12.dp)
                        ) {
                            val verdictText = when (lang) {
                                AppLanguage.RU -> stressState.verdictRu
                                AppLanguage.UA -> stressState.verdictUa
                                else -> stressState.verdictEn
                            }
                            Text(
                                text = verdictText,
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
