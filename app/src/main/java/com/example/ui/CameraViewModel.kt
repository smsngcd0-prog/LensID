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
                hardware = devInfo.hardware
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

    fun generateTechnicalReport(): String {
        val state = _uiState.value
        val dev = state.deviceInfo
        val sb = StringBuilder()

        sb.appendLine("=========================================")
        sb.appendLine("    ОТЧЕТ АППАРАТНЫХ КАМЕР СМАРТФОНА     ")
        sb.appendLine("=========================================")
        sb.appendLine()
        if (dev != null) {
            sb.appendLine("Устройство: ${dev.manufacturer} ${dev.model} (${dev.brand})")
            sb.appendLine("Кодовое имя: ${dev.deviceCode}, Плата: ${dev.board}")
            sb.appendLine("Платформа / SoC: ${dev.socModel}")
            sb.appendLine("Операционная система: ${dev.androidVersion}")
            sb.appendLine("Всего камер в системе: ${state.cameras.size}")
            sb.appendLine()
        }

        val analysis = state.supplierAnalysis
        if (analysis != null) {
            sb.appendLine("--- ПОСТАВЩИКИ И ПАРТНЕРЫ ДЛЯ ДАННОЙ МОДЕЛИ ---")
            sb.appendLine("Анализ: ${analysis.summaryRu}")
            sb.appendLine("Основные поставщики сенсоров: ${analysis.mostLikelySensorVendors.joinToString("; ")}")
            sb.appendLine("Сборщики модулей: ${analysis.mostLikelyModuleMakers.joinToString("; ")}")
            sb.appendLine("Процессор ISP: ${analysis.mostLikelyIsp}")
            if (analysis.opticPartnership != null) {
                sb.appendLine("Оптическое партнёрство: ${analysis.opticPartnership}")
            }
            sb.appendLine()
        }

        sb.appendLine("--- СПИСОК ОБНАРУЖЕННЫХ КАМЕР ---")
        state.cameras.forEachIndexed { index, cam ->
            sb.appendLine()
            sb.appendLine("[Камера #${index + 1}: ID ${cam.id}] - ${cam.role.titleRu}")
            sb.appendLine("  Расположение: ${when(cam.facing) {
                CameraFacing.BACK -> "Задняя (Основной блок)"
                CameraFacing.FRONT -> "Фронтальная (Экран)"
                CameraFacing.EXTERNAL -> "Внешняя (USB/UVC)"
                CameraFacing.UNKNOWN -> "Неизвестно"
            }}")
            sb.appendLine("  Тип: ${if (cam.isLogical) "Логическая мульти-камера" else "Физический сенсор"}")
            sb.appendLine("  Производитель сенсора: ${cam.sensorVendorGuess.vendorName} (${cam.sensorVendorGuess.probableModels.joinToString(", ")})")
            sb.appendLine("  Обоснование: ${cam.sensorVendorGuess.details}")
            sb.appendLine("  Разрешение: ${cam.resolutionText} (${cam.megapixels} Мп)")
            sb.appendLine("  Размер матрицы: ${"%.2f".format(cam.physicalWidthMm)} × ${"%.2f".format(cam.physicalHeightMm)} мм (Диагональ: ${"%.2f".format(cam.diagonalMm)} мм, ${cam.opticalFormat})")
            sb.appendLine("  Размер пикселя: ${"%.2f".format(cam.pixelPitchMicrons)} мкм (µm)")
            sb.appendLine("  Фокусное расстояние: ${cam.focalLengthsMm.joinToString(", ") { "${it} мм" }}${if (cam.focalLength35mmEq != null) " (~${cam.focalLength35mmEq} мм в экв. 35мм)" else ""}")
            sb.appendLine("  Диафрагма: ${cam.apertures.joinToString(", ") { "f/$it" }}")
            sb.appendLine("  Угол обзора: FoV H: ${cam.horizontalFovDeg.toInt()}°, V: ${cam.verticalFovDeg.toInt()}°, D: ${cam.diagonalFovDeg.toInt()}°")
            sb.appendLine("  Оптическая стабилизация (OIS): ${if (cam.oisSupported) "ДА" else "НЕТ"}")
            sb.appendLine("  Съемка в RAW: ${if (cam.rawSupported) "ДА" else "НЕТ"}")
            sb.appendLine("  Аппаратный уровень HAL: ${cam.hardwareLevel}")
            sb.appendLine("  Макс. частота кадров: ${cam.maxFps} fps")
            if (cam.highSpeedFpsList.isNotEmpty()) {
                sb.appendLine("  Замедленное видео: ${cam.highSpeedFpsList.joinToString(", ")} fps")
            }
        }

        sb.appendLine()
        sb.appendLine("=========================================")
        sb.appendLine("Отчёт сформирован приложением CamSpec Pro")

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
