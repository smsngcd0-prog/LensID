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
    val selectedCamera: CameraItem? = null,
    val selectedTab: Int = 0, // 0: Cameras, 1: Companies, 2: Live Tester, 3: Sensor DB, 4: Report
    val companySearch: String = "",
    val selectedCategory: CompanyCategory? = null,
    val sensorSearch: String = "",
    val selectedSensorVendor: String? = null,
    val selectedTesterCameraId: String? = null,
    val isCameraPermissionGranted: Boolean = false,
    val appLanguage: AppLanguage = AppLanguage.RU
)

class CameraViewModel(application: Application) : AndroidViewModel(application) {

    private val scanner = CameraHardwareScanner(application)
    val previewManager = CameraPreviewManager(application)

    private val _uiState = MutableStateFlow(CameraUiState())
    val uiState: StateFlow<CameraUiState> = _uiState.asStateFlow()

    val telemetry: StateFlow<LiveCameraTelemetry> = previewManager.telemetry

    init {
        loadHardwareInfo()
    }

    fun setLanguage(language: AppLanguage) {
        _uiState.value = _uiState.value.copy(appLanguage = language)
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

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                cameras = cameras,
                deviceInfo = devInfo,
                supplierAnalysis = supplier,
                selectedTesterCameraId = defaultTesterId
            )
        }
    }

    fun setPermissionGranted(granted: Boolean) {
        _uiState.value = _uiState.value.copy(isCameraPermissionGranted = granted)
    }

    fun selectTab(tabIndex: Int) {
        _uiState.value = _uiState.value.copy(selectedTab = tabIndex)
        if (tabIndex != 2) {
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
                    company.country.contains(state.companySearch, ignoreCase = true) ||
                    company.keyProducts.any { it.contains(state.companySearch, ignoreCase = true) } ||
                    company.marketRoleRu.contains(state.companySearch, ignoreCase = true)

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

            sb.appendLine("$devLbl ${dev.manufacturer} ${dev.model} (${dev.brand})")
            sb.appendLine("$boardLbl ${dev.deviceCode}, ${dev.board}")
            sb.appendLine("$socLbl ${dev.socModel}")
            sb.appendLine("$osLbl ${dev.androidVersion}")
            sb.appendLine("$totalLbl ${state.cameras.size}")
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

            val sourceLbl = if (isRu) "Источник детекции:" else if (isUa) "Джерело детекції:" else "Detection Source:"
            sb.appendLine("  $sourceLbl ${cam.detectionSource}")

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
