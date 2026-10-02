package com.example.ui

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.SurfaceTexture
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.camera.CameraHardwareScanner
import com.example.camera.CameraPreviewManager
import com.example.camera.LiveCameraTelemetry
import com.example.data.CompanyDatabase
import com.example.data.SensorDatabase
import com.example.localization.AppLanguage
import com.example.localization.AppStrings
import com.example.model.CameraFacing
import com.example.model.CameraItem
import com.example.model.CompanyCategory
import com.example.model.CompanyInfo
import com.example.model.DeviceInfo
import com.example.model.DeviceSupplierAnalysis
import com.example.model.SensorCatalogEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class CameraUiState(
    val isLoading: Boolean = true,
    val cameras: List<CameraItem> = emptyList(),
    val deviceInfo: DeviceInfo? = null,
    val supplierAnalysis: DeviceSupplierAnalysis? = null,
    val deviceHardwareAudit: com.example.model.DeviceHardwareAudit? = null,
    val modernDeviceSpecs: com.example.data.ModernDeviceSpecs = com.example.data.ModernDeviceSpecs(),
    val isOnlineSearchEnabled: Boolean = true,
    val selectedCamera: CameraItem? = null,
    val selectedTab: Int = 0, // 0: Cameras, 1: Hardware Specs, 2: Companies, 3: Live Tester, 4: Sensor DB, 5: Settings
    val companySearch: String = "",
    val selectedCategory: CompanyCategory? = null,
    val sensorSearch: String = "",
    val selectedSensorVendor: String? = null,
    val selectedTesterCameraId: String? = null,
    val isCameraPermissionGranted: Boolean = false,
    val appLanguage: AppLanguage = AppLanguage.EN,
    val showVibecodingWarning: Boolean = true
)

class CameraViewModel(application: Application) : AndroidViewModel(application) {

    private val scanner = CameraHardwareScanner(application)
    val previewManager = CameraPreviewManager(application)
    private val prefs = application.getSharedPreferences("camspec_pro_prefs", Context.MODE_PRIVATE)

    private val initialLanguage: AppLanguage = run {
        val saved = prefs.getString("selected_language", null)
        if (saved != null) {
            AppLanguage.fromCode(saved)
        } else {
            AppLanguage.fromSystemLocale(java.util.Locale.getDefault())
        }
    }

    private val initialOnlineSearchEnabled: Boolean = prefs.getBoolean("online_search_enabled", true)

    private val _uiState = MutableStateFlow(
        CameraUiState(
            appLanguage = initialLanguage,
            isOnlineSearchEnabled = initialOnlineSearchEnabled,
            showVibecodingWarning = true
        )
    )
    val uiState: StateFlow<CameraUiState> = _uiState.asStateFlow()

    val telemetry: StateFlow<LiveCameraTelemetry> = previewManager.telemetry

    private val throttlingTester = com.example.hardware.CpuThrottlingTester(application)
    val stressTestState: StateFlow<com.example.hardware.StressTestState> = throttlingTester.state

    fun startStressTest() {
        throttlingTester.startTest(viewModelScope)
    }

    fun stopStressTest() {
        throttlingTester.stopTest()
    }

    fun resetStressTest() {
        throttlingTester.resetTest()
    }

    init {
        loadHardwareInfo()
    }

    fun setLanguage(language: AppLanguage) {
        prefs.edit().putString("selected_language", language.code).apply()
        _uiState.value = _uiState.value.copy(appLanguage = language)
    }

    fun dismissVibecodingWarning() {
        _uiState.value = _uiState.value.copy(showVibecodingWarning = false)
    }

    fun toggleOnlineSearch(enabled: Boolean) {
        prefs.edit().putBoolean("online_search_enabled", enabled).apply()
        _uiState.value = _uiState.value.copy(isOnlineSearchEnabled = enabled)
        refreshModernSpecs()
    }

    fun refreshModernSpecs() {
        val dev = _uiState.value.deviceInfo ?: return
        val audit = _uiState.value.deviceHardwareAudit
        val isEnabled = _uiState.value.isOnlineSearchEnabled
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                modernDeviceSpecs = _uiState.value.modernDeviceSpecs.copy(isSearching = true)
            )
            val specs = com.example.data.ModernInfoRepository.fetchModernDeviceSpecs(
                context = getApplication(),
                isEnabled = isEnabled,
                manufacturer = dev.manufacturer,
                brand = dev.brand,
                model = dev.model,
                board = dev.board,
                hardware = dev.hardware,
                offlineSoc = audit?.cpu?.realSocName ?: dev.socModel
            )
            _uiState.value = _uiState.value.copy(modernDeviceSpecs = specs)
        }
    }

    fun loadHardwareInfo() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val cameras = withContext(Dispatchers.IO) {
                scanner.scanAllCameras()
            }

            val totalLogical = cameras.count { it.isLogical }
            val totalPhysical = cameras.count { !it.isLogical }

            val devInfo = scanner.getDeviceInfo(
                totalLogical = if (totalLogical > 0) totalLogical else cameras.size,
                totalPhysical = if (totalPhysical > 0) totalPhysical else cameras.size
            )

            val supplier = CompanyDatabase.getDeviceSupplierAnalysis(
                manufacturer = devInfo.manufacturer,
                brand = devInfo.brand,
                model = devInfo.model,
                board = devInfo.board,
                hardware = devInfo.hardware,
                physicalCameraCount = if (totalPhysical > 0) totalPhysical else cameras.size
            )

            val defaultTesterId = cameras.firstOrNull { it.facing == CameraFacing.BACK }?.id
                ?: cameras.firstOrNull()?.id

            val inspector = com.example.hardware.DeviceHardwareInspector(getApplication())
            val audit = withContext(Dispatchers.IO) {
                inspector.inspectAll(devInfo.socModel)
            }

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                cameras = cameras,
                deviceInfo = devInfo,
                supplierAnalysis = supplier,
                deviceHardwareAudit = audit,
                selectedTesterCameraId = defaultTesterId
            )

            // Trigger online specs lookup from the Modern Information Library
            refreshModernSpecs()
        }
    }

    fun setPermissionGranted(granted: Boolean) {
        _uiState.value = _uiState.value.copy(isCameraPermissionGranted = granted)
    }

    fun selectTab(tabIndex: Int) {
        _uiState.value = _uiState.value.copy(selectedTab = tabIndex)
        if (tabIndex != 3) {
            previewManager.stopPreview()
        }
    }

    fun selectCamera(camera: CameraItem?) {
        _uiState.value = _uiState.value.copy(selectedCamera = camera)
    }

    fun setTesterCameraId(id: String) {
        _uiState.value = _uiState.value.copy(selectedTesterCameraId = id)
    }

    fun startPreview(surfaceTexture: SurfaceTexture) {
        val id = _uiState.value.selectedTesterCameraId ?: return
        previewManager.startPreview(id, surfaceTexture)
    }

    fun stopPreview() {
        previewManager.stopPreview()
    }

    fun toggleTorch() {
        previewManager.toggleTorch()
    }

    fun setCompanySearch(query: String) {
        _uiState.value = _uiState.value.copy(companySearch = query)
    }

    fun setCategoryFilter(category: CompanyCategory?) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }

    fun setSensorSearch(query: String) {
        _uiState.value = _uiState.value.copy(sensorSearch = query)
    }

    fun setSensorVendorFilter(vendor: String?) {
        _uiState.value = _uiState.value.copy(selectedSensorVendor = vendor)
    }

    fun getFilteredCompanies(): List<CompanyInfo> {
        val state = _uiState.value
        return CompanyDatabase.ALL_COMPANIES.filter { company ->
            val matchesQuery = state.companySearch.isBlank() ||
                    company.name.contains(state.companySearch, ignoreCase = true) ||
                    company.getCountry(state.appLanguage).contains(state.companySearch, ignoreCase = true) ||
                    company.keyProducts.any { it.contains(state.companySearch, ignoreCase = true) } ||
                    company.getMarketRole(state.appLanguage).contains(state.companySearch, ignoreCase = true)

            val matchesCategory = state.selectedCategory == null || company.category == state.selectedCategory

            matchesQuery && matchesCategory
        }
    }

    fun getFilteredSensors(): List<SensorCatalogEntry> {
        val state = _uiState.value
        return SensorDatabase.CATALOG.filter { entry ->
            val matchesQuery = state.sensorSearch.isBlank() ||
                    entry.modelName.contains(state.sensorSearch, ignoreCase = true) ||
                    entry.vendor.contains(state.sensorSearch, ignoreCase = true) ||
                    entry.typicalPhones.contains(state.sensorSearch, ignoreCase = true) ||
                    entry.keyFeaturesRu.contains(state.sensorSearch, ignoreCase = true)

            val matchesVendor = state.selectedSensorVendor == null ||
                    entry.vendor.equals(state.selectedSensorVendor, ignoreCase = true)

            matchesQuery && matchesVendor
        }
    }

    fun generateTechnicalReport(lang: AppLanguage = _uiState.value.appLanguage): String {
        val state = _uiState.value
        val dev = state.deviceInfo
        val sb = StringBuilder()
        val isRu = lang == AppLanguage.RU
        val isUa = lang == AppLanguage.UA

        val title = if (isRu) "ОТЧЕТ АППАРАТНЫХ КАМЕР СМАРТФОНА"
        else if (isUa) "ЗВІТ АПАРАТНИХ КАМЕР СМАРТФОНА"
        else "HARDWARE CAMERA AUDIT REPORT"

        sb.appendLine("=========================================")
        sb.appendLine("    $title     ")
        sb.appendLine("=========================================")
        sb.appendLine()
        if (dev != null) {
            val devLbl = if (isRu) "Устройство:" else if (isUa) "Пристрій:" else "Device:"
            val boardLbl = if (isRu) "Кодовое имя / Плата:" else if (isUa) "Кодове ім'я / Плата:" else "Code / Board:"
            val socLbl = if (isRu) "Платформа / SoC:" else if (isUa) "Платформа / SoC:" else "Platform / SoC:"
            val osLbl = if (isRu) "Операционная система:" else if (isUa) "Операційна система:" else "Operating System:"
            val totalLbl = if (isRu) "Всего камер в системе:" else if (isUa) "Всього камер у системі:" else "Total Cameras in System:"

            sb.appendLine("$devLbl ${dev.fullBrandTitle} [${dev.model}]")
            sb.appendLine("$boardLbl ${dev.deviceCode}, ${dev.board}")
            sb.appendLine("$socLbl ${dev.socModel}")
            sb.appendLine("$osLbl ${dev.androidVersion}")
            sb.appendLine("$totalLbl ${state.cameras.size}")
            sb.appendLine()
        }

        val audit = state.deviceHardwareAudit
        val modern = state.modernDeviceSpecs
        if (audit != null) {
            val hwTitle = if (isRu) "--- АППАРАТНЫЙ АУДИТ (SoC, ПАМЯТЬ, БАТАРЕЯ, WINLATOR) ---"
            else if (isUa) "--- АПАРАТНИЙ АУДИТ (SoC, ПАМ'ЯТЬ, БАТАРЕЯ, WINLATOR) ---"
            else "--- HARDWARE AUDIT (SoC, STORAGE, BATTERY, WINLATOR) ---"
            sb.appendLine(hwTitle)

            if (modern.isOnlineSuccess && modern.onlineSocTitle != null) {
                sb.appendLine("SoC / CPU (ONLINE): ${modern.onlineSocTitle} [Offline: ${audit.cpu.realSocName}]")
                sb.appendLine("GPU (ONLINE): ${modern.onlineGpu ?: audit.cpu.gpuModel}")
                sb.appendLine("Specs (ONLINE): ${modern.onlineRamStorage ?: ""} • ${modern.onlineBattery ?: ""}")
                sb.appendLine("Cloud Source: ${modern.sourceProvider}")
            } else if (modern.searchFailed) {
                sb.appendLine("SoC / CPU: ${audit.cpu.realSocName} (${AppStrings.getNetworkSearchFailedNote(lang)})")
                sb.appendLine("GPU: ${audit.cpu.gpuModel}")
            } else {
                sb.appendLine("SoC / CPU: ${audit.cpu.realSocName} (${audit.cpu.processNodeNm})")
                sb.appendLine("GPU: ${audit.cpu.gpuModel}")
            }
            sb.appendLine("Architecture: ${audit.cpu.architecture} [${audit.cpu.coreConfiguration}]")
            sb.appendLine("Screen Display: ${audit.screen.resolutionLabel} (${audit.screen.currentWidth}×${audit.screen.currentHeight}) @ ${audit.screen.reportedRefreshRate.toInt()} Hz [${audit.screen.standardName}]")
            sb.appendLine("Screen Integrity: ${audit.screen.getIntegrityMessage(lang)}")
            sb.appendLine("Physical NAND Flash: ${audit.storage.physicalChipCapacityGb.toInt()} GB (${audit.storage.flashStorageType})")
            if (audit.storage.hasExternalSdCard && audit.storage.externalSdCardTotalGb != null) {
                sb.appendLine("External Storage: MicroSD / Flash Card ${audit.storage.externalSdCardTotalGb.toInt()} GB (${audit.storage.externalSdCardFreeGb ?: 0.0} GB free)")
            }
            sb.appendLine("Storage Integrity: ${audit.storage.getIntegrityMessage(lang)}")
            sb.appendLine("Battery Health: ${audit.battery.healthPercentage}% [${audit.battery.estimatedActualCapacityMah} mAh / ${audit.battery.designCapacityMah} mAh design]")
            sb.appendLine("RAM: ${audit.ram.physicalRamGb.toInt()} GB ${audit.ram.ramType} + ${audit.ram.virtualRamGb.toInt()} GB Virtual (ZRAM)")
            sb.appendLine("Winlator PC Emulation: ${audit.winlator.ratingStars} ${audit.winlator.getRatingLabel(lang)}")
            sb.appendLine("AnTuTu Benchmark v10: ${"%,d".format(audit.antutu.estimatedTotalScore)} (${audit.antutu.getTierLabel(lang)})")
            sb.appendLine()
        }

        val analysis = state.supplierAnalysis
        if (analysis != null) {
            val supTitle = if (isRu) "--- ПОСТАВЩИКИ И ПАРТНЕРЫ ДЛЯ ДАННОЙ МОДЕЛИ ---"
            else if (isUa) "--- ПОСТАЧАЛЬНИКИ ТА ПАРТНЕРИ ДЛЯ ЦІЄЇ МОДЕЛІ ---"
            else "--- SUPPLIERS & PARTNERS FOR THIS DEVICE ---"
            sb.appendLine(supTitle)
            sb.appendLine(analysis.summaryRu)
            sb.appendLine("Foundries: ${analysis.mostLikelySensorVendors.joinToString("; ")}")
            sb.appendLine("Module Assemblers: ${analysis.mostLikelyModuleMakers.joinToString("; ")}")
            sb.appendLine("ISP: ${analysis.mostLikelyIsp}")
            if (analysis.opticPartnership != null) {
                sb.appendLine("Optics: ${analysis.opticPartnership}")
            }
            sb.appendLine()
        }

        val camTitle = if (isRu) "--- СПИСОК ОБНАРУЖЕННЫХ КАМЕР ---"
        else if (isUa) "--- СПИСОК ВИЯВЛЕНИХ КАМЕР ---"
        else "--- DETECTED CAMERAS LIST ---"
        sb.appendLine(camTitle)

        state.cameras.forEachIndexed { index, cam ->
            sb.appendLine()
            val camNum = if (isRu) "Камера" else if (isUa) "Камера" else "Camera"
            sb.appendLine("[$camNum #${index + 1}: ID ${cam.id}] - ${cam.role.getTitle(lang)}")

            val facingStr = AppStrings.getFacingTitle(cam.facing, lang)
            val facingLbl = if (isRu) "Расположение:" else if (isUa) "Розташування:" else "Facing:"
            sb.appendLine("  $facingLbl $facingStr")

            val typeLbl = if (isRu) "Тип:" else if (isUa) "Тип:" else "Type:"
            val typeStr = if (cam.isLogical) {
                if (isRu) "Логическая мульти-камера" else if (isUa) "Логічна мульти-камера" else "Logical Multi-Camera"
            } else {
                if (isRu) "Физический сенсор" else if (isUa) "Фізичний сенсор" else "Physical Sensor"
            }
            sb.appendLine("  $typeLbl $typeStr")

            val makerLbl = if (isRu) "Производитель матрицы:" else if (isUa) "Виробник матриці:" else "Sensor Maker:"
            sb.appendLine("  $makerLbl ${cam.sensorVendorGuess.vendorName} (${cam.sensorVendorGuess.probableModels.joinToString(", ")})")
            sb.appendLine("  ${cam.sensorVendorGuess.getDetails(lang)}")

            val sourceLbl = if (isRu) "Источник детекции:" else if (isUa) "Джерело детекції:" else "Detection Source:"
            sb.appendLine("  $sourceLbl ${cam.getDetectionSource(lang)}")

            val resLbl = if (isRu) "Разрешение:" else if (isUa) "Роздільність:" else "Resolution:"
            sb.appendLine("  $resLbl ${cam.resolutionText} (${cam.megapixels} MP)")

            val sizeLbl = if (isRu) "Размер сенсора:" else if (isUa) "Розмір сенсора:" else "Physical Size:"
            sb.appendLine("  $sizeLbl ${"%.2f".format(cam.physicalWidthMm)} × ${"%.2f".format(cam.physicalHeightMm)} mm (${cam.opticalFormat}, ${"%.2f".format(cam.pixelPitchMicrons)} µm)")

            val focalLbl = if (isRu) "Фокусное расстояние:" else if (isUa) "Фокусна відстань:" else "Focal Length:"
            sb.appendLine("  $focalLbl ${cam.focalLengthsMm.joinToString(", ") { "${it} mm" }} / ${cam.apertures.joinToString(", ") { "f/$it" }}")

            val oisLbl = if (isRu) "Оптическая стабилизация (OIS):" else if (isUa) "Оптична стабілізація (OIS):" else "Optical Stabilization (OIS):"
            sb.appendLine("  $oisLbl ${if (cam.oisSupported) "YES" else "NO"}")

            val rawLbl = if (isRu) "Съемка в RAW:" else if (isUa) "Зйомка в RAW:" else "RAW Support:"
            sb.appendLine("  $rawLbl ${if (cam.rawSupported) "YES" else "NO"}")

            val halLbl = if (isRu) "Аппаратный уровень HAL:" else if (isUa) "Апаратний рівень HAL:" else "HAL Level:"
            sb.appendLine("  $halLbl ${cam.hardwareLevel}")
        }

        sb.appendLine()
        sb.appendLine("=========================================")
        sb.appendLine(if (isRu) "Отчёт сформирован приложением CamSpec Pro" else if (isUa) "Звіт сформовано додатком CamSpec Pro" else "Generated by CamSpec Pro")

        return sb.toString()
    }

    fun copyReportToClipboard() {
        val text = generateTechnicalReport()
        val clipboard = getApplication<Application>().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Отчет камер CamSpec Pro", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(getApplication(), "Отчет скопирован в буфер обмена", Toast.LENGTH_SHORT).show()
    }

    fun shareReport() {
        val text = generateTechnicalReport()
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Спецификация камер смартфона")
            putExtra(Intent.EXTRA_TEXT, text)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val chooser = Intent.createChooser(intent, "Поделиться отчетом камер")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        getApplication<Application>().startActivity(chooser)
    }

    override fun onCleared() {
        super.onCleared()
        previewManager.stopPreview()
    }
}
