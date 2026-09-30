package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lens
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CameraFacing
import com.example.model.CameraItem
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.PurpleAccent
import com.example.ui.theme.TechSurfaceDark
import com.example.ui.theme.TechSurfaceVariantDark

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CameraCard(
    camera: CameraItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val roleColor = when (camera.facing) {
        CameraFacing.BACK -> CyanAccent
        CameraFacing.FRONT -> PurpleAccent
        CameraFacing.EXTERNAL -> EmeraldGreen
        CameraFacing.UNKNOWN -> AmberWarning
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, roleColor.copy(alpha = 0.25f), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag("camera_card_${camera.id}"),
        colors = CardDefaults.cardColors(containerColor = TechSurfaceDark),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Role, ID, Logical/Physical status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(roleColor.copy(alpha = 0.15f), RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (camera.facing == CameraFacing.FRONT) Icons.Default.CameraAlt else Icons.Default.Camera,
                            contentDescription = "Camera Icon",
                            tint = roleColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = camera.role.titleRu,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Камера ID: ${camera.id}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            if (camera.isLogical) {
                                Spacer(modifier = Modifier.width(6.dp))
                                StatusBadge(text = "МУЛЬТИ-МОДУЛЬ", color = CyanAccent)
                            } else {
                                Spacer(modifier = Modifier.width(6.dp))
                                StatusBadge(text = "ФИЗИЧЕСКИЙ СЕНСОР", color = EmeraldGreen)
                            }
                        }
                    }
                }

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Details",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Vendor & Sensor Guess Highlight Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TechSurfaceVariantDark.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                    .padding(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Memory,
                        contentDescription = "Sensor",
                        tint = roleColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Производитель: ${camera.sensorVendorGuess.vendorName}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (camera.sensorVendorGuess.probableModels.isNotEmpty()) {
                            Text(
                                text = "Модель матрицы: ${camera.sensorVendorGuess.probableModels.joinToString(", ")}",
                                fontSize = 11.sp,
                                color = CyanAccent,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Key Specs Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Разрешение",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${camera.megapixels} Мп",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = camera.resolutionText,
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Column {
                    Text(
                        text = "Формат матрицы",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = camera.opticalFormat,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = roleColor
                    )
                    Text(
                        text = "${"%.1f".format(camera.physicalWidthMm)}×${"%.1f".format(camera.physicalHeightMm)} мм",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Column {
                    Text(
                        text = "Пиксель",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${"%.2f".format(camera.pixelPitchMicrons)} мкм",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = camera.apertures.firstOrNull()?.let { "f/$it" } ?: "",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Feature Badges (OIS, RAW, 4K, PDAF, Flash)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (camera.oisSupported) {
                    StatusBadge(text = "OIS СТАБИЛИЗАЦИЯ", color = EmeraldGreen)
                }
                if (camera.rawSupported) {
                    StatusBadge(text = "RAW / DNG", color = CyanAccent)
                }
                if (camera.videoResolutions.any { it.contains("4K") }) {
                    StatusBadge(text = "4K ВИДЕО", color = PurpleAccent)
                }
                if (camera.flashSupported) {
                    StatusBadge(text = "ВСПЫШКА", color = AmberWarning)
                }
                if (camera.isMacroCapable) {
                    StatusBadge(text = "МАКРОФОКУС", color = Color(0xFF14B8A6))
                }
                StatusBadge(text = camera.hardwareLevel.substringBefore(" "), color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}
