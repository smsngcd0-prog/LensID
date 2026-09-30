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
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CameraFacing
import com.example.model.CameraItem
import com.example.ui.CameraUiState
import com.example.ui.components.CameraCard
import com.example.ui.components.StatusBadge
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.PurpleAccent
import com.example.ui.theme.TechNavyDark
import com.example.ui.theme.TechSurfaceDark
import com.example.ui.theme.TechSurfaceVariantDark

enum class CameraFilter {
    ALL,
    BACK,
    FRONT,
    PHYSICAL;

    fun getTitle(lang: com.example.localization.AppLanguage): String =
        com.example.localization.AppStrings.getFilterTitle(name, lang)
}

@Composable
fun CameraOverviewScreen(
    state: CameraUiState,
    onCameraClick: (CameraItem) -> Unit,
    onGoToCompaniesClick: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf(CameraFilter.ALL) }

    val filteredCameras = remember(state.cameras, selectedFilter) {
        when (selectedFilter) {
            CameraFilter.ALL -> state.cameras
            CameraFilter.BACK -> state.cameras.filter { it.facing == CameraFacing.BACK }
            CameraFilter.FRONT -> state.cameras.filter { it.facing == CameraFacing.FRONT }
            CameraFilter.PHYSICAL -> state.cameras.filter { !it.isLogical }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = CyanAccent)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Сканирование аппаратных сенсоров...",
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
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // HERO BANNER: Device & Camera count summary
        item {
            val dev = state.deviceInfo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364))
                        )
                    )
                    .border(1.dp, CyanAccent.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                    .padding(18.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Smartphone,
                                contentDescription = "Phone",
                                tint = CyanAccent,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${dev?.manufacturer ?: "Смартфон"} ${dev?.model ?: ""}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        IconButton(
                            onClick = onRefresh,
                            modifier = Modifier.testTag("refresh_cameras_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh",
                                tint = CyanAccent
                            )
                        }
                    }

                    Text(
                        text = "Платформа: ${dev?.socModel ?: "Android"} • ${dev?.androidVersion ?: ""}",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = com.example.localization.AppStrings.getTotalModules(state.appLanguage),
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                            Text(
                                text = "${state.cameras.size}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = CyanAccent
                            )
                        }

                        Column {
                            Text(
                                text = com.example.localization.AppStrings.getBackCameras(state.appLanguage),
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                            Text(
                                text = "${state.cameras.count { it.facing == CameraFacing.BACK }}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = EmeraldGreen
                            )
                        }

                        Column {
                            Text(
                                text = com.example.localization.AppStrings.getFrontCameras(state.appLanguage),
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                            Text(
                                text = "${state.cameras.count { it.facing == CameraFacing.FRONT }}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = PurpleAccent
                            )
                        }
                    }
                }
            }
        }

        // SUPPLIER QUICK INSIGHT CARD
        item {
            val supplier = state.supplierAnalysis
            if (supplier != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, EmeraldGreen.copy(alpha = 0.3f), RoundedCornerShape(14.dp)),
                    colors = CardDefaults.cardColors(containerColor = TechSurfaceDark),
                    shape = RoundedCornerShape(14.dp),
                    onClick = onGoToCompaniesClick
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(EmeraldGreen.copy(alpha = 0.15f), RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Business,
                                contentDescription = "Suppliers",
                                tint = EmeraldGreen,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = com.example.localization.AppStrings.getSuppliersForYourPhone(state.appLanguage),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Сенсоры: ${supplier.mostLikelySensorVendors.firstOrNull() ?: "Sony / Samsung"}",
                                fontSize = 12.sp,
                                color = EmeraldGreen
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Go",
                            tint = EmeraldGreen
                        )
                    }
                }
            }
        }

        // FILTER CHIPS
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(CameraFilter.values()) { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter.getTitle(state.appLanguage)) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CyanAccent,
                            selectedLabelColor = Color(0xFF00363D),
                            containerColor = TechSurfaceVariantDark.copy(alpha = 0.4f),
                            labelColor = MaterialTheme.colorScheme.onSurface
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = TechSurfaceVariantDark,
                            selectedBorderColor = CyanAccent,
                            enabled = true,
                            selected = selectedFilter == filter
                        ),
                        modifier = Modifier.testTag("filter_chip_${filter.name}")
                    )
                }
            }
        }

        // CAMERAS LIST
        if (filteredCameras.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val emptyText = if (state.appLanguage == com.example.localization.AppLanguage.RU) {
                        "Камеры по данному фильтру не найдены"
                    } else if (state.appLanguage == com.example.localization.AppLanguage.UA) {
                        "Камери за цим фільтром не знайдено"
                    } else {
                        "No cameras found for this filter"
                    }
                    Text(
                        text = emptyText,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(filteredCameras, key = { it.id + "_" + it.isLogical }) { camera ->
                CameraCard(
                    camera = camera,
                    onClick = { onCameraClick(camera) },
                    lang = state.appLanguage
                )
            }
        }
    }
}
