package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localization.AppLanguage
import com.example.localization.AppStrings
import com.example.ui.components.CameraDetailSheet
import com.example.ui.screens.CameraOverviewScreen
import com.example.ui.screens.CameraTesterScreen
import com.example.ui.screens.CompanyDirectoryScreen
import com.example.ui.screens.SensorCatalogScreen
import com.example.ui.screens.TechnicalReportScreen
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.TechNavyDark
import com.example.ui.theme.TechSurfaceDark
import com.example.ui.theme.TechSurfaceVariantDark
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(
    viewModel: CameraViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var showLanguageMenu by remember { mutableStateOf(false) }

    // Back handling
    BackHandler(enabled = state.selectedCamera != null || state.selectedTab != 0) {
        if (state.selectedCamera != null) {
            coroutineScope.launch {
                sheetState.hide()
                viewModel.selectCamera(null)
            }
        } else if (state.selectedTab != 0) {
            viewModel.selectTab(0)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = AppStrings.getHeaderTitle(state.selectedTab, state.appLanguage),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                actions = {
                    Box {
                        Button(
                            onClick = { showLanguageMenu = true },
                            colors = ButtonDefaults.buttonColors(containerColor = TechSurfaceVariantDark.copy(alpha = 0.7f)),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .testTag("language_switch_button")
                        ) {
                            Text(
                                text = "${state.appLanguage.flag} ${state.appLanguage.code.uppercase()}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        DropdownMenu(
                            expanded = showLanguageMenu,
                            onDismissRequest = { showLanguageMenu = false },
                            modifier = Modifier.background(TechSurfaceDark)
                        ) {
                            AppLanguage.values().forEach { lang ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = "${lang.flag} ${lang.title}",
                                            color = if (state.appLanguage == lang) CyanAccent else Color.White,
                                            fontWeight = if (state.appLanguage == lang) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    onClick = {
                                        viewModel.setLanguage(lang)
                                        showLanguageMenu = false
                                    }
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TechNavyDark,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = TechNavyDark,
                modifier = Modifier
                    .border(1.dp, TechSurfaceVariantDark.copy(alpha = 0.4f))
                    .testTag("bottom_nav_bar")
            ) {
                NavigationBarItem(
                    selected = state.selectedTab == 0,
                    onClick = { viewModel.selectTab(0) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Cameras",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = { Text(AppStrings.getTabCameras(state.appLanguage), fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF00363D),
                        selectedTextColor = CyanAccent,
                        indicatorColor = CyanAccent,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_tab_cameras")
                )

                NavigationBarItem(
                    selected = state.selectedTab == 1,
                    onClick = { viewModel.selectTab(1) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Business,
                            contentDescription = "Companies",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = { Text(AppStrings.getTabCompanies(state.appLanguage), fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF00363D),
                        selectedTextColor = CyanAccent,
                        indicatorColor = CyanAccent,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_tab_companies")
                )

                NavigationBarItem(
                    selected = state.selectedTab == 2,
                    onClick = { viewModel.selectTab(2) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Videocam,
                            contentDescription = "Tester",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = { Text(AppStrings.getTabTester(state.appLanguage), fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF00363D),
                        selectedTextColor = CyanAccent,
                        indicatorColor = CyanAccent,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_tab_tester")
                )

                NavigationBarItem(
                    selected = state.selectedTab == 3,
                    onClick = { viewModel.selectTab(3) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Memory,
                            contentDescription = "Sensors",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = { Text(AppStrings.getTabSensors(state.appLanguage), fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF00363D),
                        selectedTextColor = CyanAccent,
                        indicatorColor = CyanAccent,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_tab_sensors")
                )

                NavigationBarItem(
                    selected = state.selectedTab == 4,
                    onClick = { viewModel.selectTab(4) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = "Report",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = { Text(AppStrings.getTabReport(state.appLanguage), fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF00363D),
                        selectedTextColor = CyanAccent,
                        indicatorColor = CyanAccent,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_tab_report")
                )
            }
        },
        containerColor = TechNavyDark,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (state.selectedTab) {
                0 -> CameraOverviewScreen(
                    state = state,
                    onCameraClick = { cam ->
                        viewModel.selectCamera(cam)
                    },
                    onGoToCompaniesClick = {
                        viewModel.selectTab(1)
                    },
                    onRefresh = {
                        viewModel.loadHardwareInfo()
                    }
                )

                1 -> CompanyDirectoryScreen(
                    state = state,
                    companies = viewModel.getFilteredCompanies(),
                    onSearchChange = { viewModel.setCompanySearch(it) },
                    onCategorySelect = { viewModel.setCategoryFilter(it) }
                )

                2 -> CameraTesterScreen(
                    state = state,
                    viewModel = viewModel
                )

                3 -> SensorCatalogScreen(
                    state = state,
                    sensors = viewModel.getFilteredSensors(),
                    onSearchChange = { viewModel.setSensorSearch(it) },
                    onVendorSelect = { viewModel.setSensorVendorFilter(it) }
                )

                4 -> TechnicalReportScreen(
                    viewModel = viewModel
                )
            }

            // MODAL BOTTOM SHEET FOR CAMERA DETAILS
            if (state.selectedCamera != null) {
                CameraDetailSheet(
                    camera = state.selectedCamera!!,
                    sheetState = sheetState,
                    onDismiss = {
                        coroutineScope.launch {
                            sheetState.hide()
                            viewModel.selectCamera(null)
                        }
                    }
                )
            }
        }
    }
}
