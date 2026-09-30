package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localization.AppLanguage
import com.example.localization.AppStrings
import com.example.model.CameraItem
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.TechSurfaceDark
import com.example.ui.theme.TechSurfaceVariantDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraDetailSheet(
    camera: CameraItem,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    lang: AppLanguage = AppLanguage.RU
) {
    val isRu = lang == AppLanguage.RU
    val isUa = lang == AppLanguage.UA

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = TechSurfaceDark,
        modifier = Modifier.testTag("camera_detail_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = camera.role.getTitle(lang),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    val modType = if (camera.isLogical) {
                        if (isRu) "Мульти-модуль" else if (isUa) "Мульти-модуль" else "Multi-Camera"
                    } else {
                        if (isRu) "Физический сенсор" else if (isUa) "Фізичний сенсор" else "Physical Sensor"
                    }
                    Text(
                        text = "Camera ID ${camera.id} • $modType",
                        fontSize = 13.sp,
                        color = CyanAccent
                    )
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.testTag("close_sheet_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // SENSOR MANUFACTURER BOX
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = TechSurfaceVariantDark.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Memory,
                            contentDescription = "Sensor Vendor",
                            tint = CyanAccent,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        val headerText = if (isRu) "Производитель и модель матрицы" else if (isUa) "Виробник та модель матриці" else "Sensor Vendor & Model"
                        Text(
                            text = headerText,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    val vendorLabel = if (isRu) "Компания:" else if (isUa) "Компанія:" else "Foundry:"
                    Text(
                        text = "$vendorLabel ${camera.sensorVendorGuess.vendorName}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = CyanAccent
                    )

                    if (camera.sensorVendorGuess.probableModels.isNotEmpty()) {
                        val modelLabel = if (isRu) "Вероятная модель:" else if (isUa) "Ймовірна модель:" else "Identified Model:"
                        Text(
                            text = "$modelLabel ${camera.sensorVendorGuess.probableModels.joinToString(", ")}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    StatusBadge(
                        text = camera.sensorVendorGuess.getConfidence(lang),
                        color = EmeraldGreen
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = camera.sensorVendorGuess.getDetails(lang),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ALL PARAMETERS SECTION
            Text(
                text = AppStrings.getAllSpecsTitle(lang),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Technical Grid (Dynamically localized)
            val specsMap = camera.getTechnicalSummary(lang)
            specsMap.forEach { (key, value) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = key,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1.2f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = value,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1.4f)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(TechSurfaceVariantDark.copy(alpha = 0.5f))
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dismiss_detail_button"),
                colors = ButtonDefaults.buttonColors(containerColor = CyanAccent),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = AppStrings.getCloseSpec(lang),
                    color = Color(0xFF00363D),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
