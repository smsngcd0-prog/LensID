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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.model.SensorCatalogEntry
import com.example.ui.CameraUiState
import com.example.ui.components.StatusBadge
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.PurpleAccent
import com.example.ui.theme.TechSurfaceDark
import com.example.ui.theme.TechSurfaceVariantDark

@Composable
fun SensorCatalogScreen(
    state: CameraUiState,
    sensors: List<SensorCatalogEntry>,
    onSearchChange: (String) -> Unit,
    onVendorSelect: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val vendors = listOf("Sony", "Samsung", "OmniVision", "GalaxyCore", "SK Hynix")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column {
                Text(
                    text = "База сенсоров камер",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Энциклопедия мобильных CMOS-матриц от ведущих производителей",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // SEARCH
        item {
            OutlinedTextField(
                value = state.sensorSearch,
                onValueChange = onSearchChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("sensor_search_input"),
                placeholder = { Text("Поиск сенсора (например, IMX890, HP2, OV50H)...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = CyanAccent
                    )
                },
                trailingIcon = {
                    if (state.sensorSearch.isNotEmpty()) {
                        IconButton(onClick = { onSearchChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyanAccent,
                    unfocusedBorderColor = TechSurfaceVariantDark,
                    focusedContainerColor = TechSurfaceDark,
                    unfocusedContainerColor = TechSurfaceDark
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }

        // VENDOR FILTER CHIPS
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    FilterChip(
                        selected = state.selectedSensorVendor == null,
                        onClick = { onVendorSelect(null) },
                        label = { Text("Все вендоры (${sensors.size})") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CyanAccent,
                            selectedLabelColor = Color(0xFF00363D),
                            containerColor = TechSurfaceVariantDark.copy(alpha = 0.5f),
                            labelColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

                items(vendors) { vendor ->
                    FilterChip(
                        selected = state.selectedSensorVendor == vendor,
                        onClick = { onVendorSelect(vendor) },
                        label = { Text(vendor) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CyanAccent,
                            selectedLabelColor = Color(0xFF00363D),
                            containerColor = TechSurfaceVariantDark.copy(alpha = 0.5f),
                            labelColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        }

        // SENSORS LIST
        items(sensors, key = { it.vendor + "_" + it.modelName }) { sensor ->
            val vendorColor = when (sensor.vendor) {
                "Sony" -> CyanAccent
                "Samsung" -> EmeraldGreen
                "OmniVision" -> PurpleAccent
                else -> Color(0xFF14B8A6)
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .border(1.dp, vendorColor.copy(alpha = 0.25f), RoundedCornerShape(14.dp))
                    .testTag("sensor_card_${sensor.modelName}"),
                colors = CardDefaults.cardColors(containerColor = TechSurfaceDark),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = sensor.vendor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = vendorColor
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "(${sensor.releaseYear})",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                text = sensor.modelName,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        StatusBadge(
                            text = sensor.opticalFormat,
                            color = vendorColor
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Specs Grid
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(TechSurfaceVariantDark.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Разрешение", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${sensor.megapixels} Мп", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                        Column {
                            Text("Пиксель", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${sensor.pixelPitchMicrons} мкм", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = CyanAccent)
                        }
                        Column {
                            Text("Размер матрицы", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${sensor.sensorWidthMm}×${sensor.sensorHeightMm} мм", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Автофокус: ${sensor.autofocusTech}",
                        fontSize = 11.sp,
                        color = EmeraldGreen,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = sensor.keyFeaturesRu,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Smartphone,
                            contentDescription = "Phones",
                            tint = CyanAccent,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Смартфоны: ${sensor.typicalPhones}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}
