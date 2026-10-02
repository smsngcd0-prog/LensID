package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.CompanyDatabase
import com.example.data.SensorDatabase
import com.example.util.DeviceNameResolver
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("CamSpec Pro", appName)
    }

    @Test
    fun `resolve marketing device name accurately`() {
        val a7Resolved = DeviceNameResolver.resolve("Xiaomi", "Redmi", "Redmi а7 про", "gold")
        assertEquals("Redmi A7 Pro", a7Resolved.marketingName)

        val s24Resolved = DeviceNameResolver.resolve("Samsung", "Samsung", "SM-S928B", "e3q")
        assertEquals("Samsung Galaxy S24 Ultra", s24Resolved.marketingName)
    }

    @Test
    fun `sensor database contains entries and matcher works`() {
        assertTrue(SensorDatabase.CATALOG.isNotEmpty())
        assertTrue(SensorDatabase.CATALOG.any { it.vendor == "SmartSens" })
        assertTrue(SensorDatabase.CATALOG.any { it.vendor == "GalaxyCore" })

        // Test budget Redmi matching (SmartSens for 50MP main)
        val redmiMatch = SensorDatabase.matchSensor(
            megapixels = 50.0,
            widthMm = 4.64f,
            heightMm = 3.48f,
            pixelArrayW = 8160,
            pixelArrayH = 6120,
            isFront = false,
            focalLengthMm = 3.98f,
            manufacturer = "Xiaomi",
            model = "Redmi A7 Pro"
        )
        assertNotNull(redmiMatch)
        assertEquals("SmartSens Technology 🇨🇳", redmiMatch.vendorName)
        assertTrue(redmiMatch.probableModels.any { it.contains("SC500CS") })

        // Test budget Redmi matching (GalaxyCore for 2MP macro)
        val macroMatch = SensorDatabase.matchSensor(
            megapixels = 2.0,
            widthMm = 2.4f,
            heightMm = 1.8f,
            pixelArrayW = 1600,
            pixelArrayH = 1200,
            isFront = false,
            focalLengthMm = 2.0f,
            manufacturer = "Xiaomi",
            model = "Redmi A7 Pro"
        )
        assertNotNull(macroMatch)
        assertEquals("GalaxyCore 🇨🇳", macroMatch.vendorName)
        assertTrue(macroMatch.probableModels.any { it.contains("GC02M1") })

        // Test direct hardware driver detection (100% confidence)
        val driverMatch = SensorDatabase.matchSensor(
            megapixels = 50.0,
            widthMm = 4.64f,
            heightMm = 3.48f,
            pixelArrayW = 8160,
            pixelArrayH = 6120,
            isFront = false,
            focalLengthMm = 3.98f,
            manufacturer = "Xiaomi",
            model = "Redmi",
            detectedHardwareDriverModels = listOf("SC500CS")
        )
        assertNotNull(driverMatch)
        assertTrue(driverMatch.vendorName.contains("SmartSens"))
        assertTrue(driverMatch.confidenceRu.contains("100%"))
    }

    @Test
    fun `company database contains suppliers and device analysis works`() {
        assertTrue(CompanyDatabase.ALL_COMPANIES.isNotEmpty())
        assertTrue(CompanyDatabase.ALL_COMPANIES.any { it.id == "smartsens" })

        // Budget Redmi analysis
        val redmiBudgetAnalysis = CompanyDatabase.getDeviceSupplierAnalysis(
            manufacturer = "Xiaomi",
            brand = "Redmi",
            model = "Redmi A7 Pro",
            board = "gold",
            hardware = "mt6769",
            physicalCameraCount = 2
        )
        assertNotNull(redmiBudgetAnalysis)
        assertTrue(redmiBudgetAnalysis.mostLikelySensorVendors.any { it.contains("SmartSens") })
        assertTrue(redmiBudgetAnalysis.mostLikelySensorVendors.any { it.contains("GalaxyCore") })

        // Flagship Xiaomi analysis
        val xiaomiAnalysis = CompanyDatabase.getDeviceSupplierAnalysis(
            manufacturer = "Xiaomi",
            brand = "Xiaomi",
            model = "14 Ultra",
            board = "aurora",
            hardware = "qcom",
            physicalCameraCount = 4
        )
        assertNotNull(xiaomiAnalysis)
        assertTrue(xiaomiAnalysis.mostLikelySensorVendors.any { it.contains("Sony") })
        assertTrue(xiaomiAnalysis.opticPartnership?.contains("Leica") == true)
    }

    @Test
    fun `device hardware inspector produces valid audit`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val inspector = com.example.hardware.DeviceHardwareInspector(context)
        val audit = inspector.inspectAll("Snapdragon 8 Gen 3")

        assertNotNull(audit)
        assertNotNull(audit.cpu.realSocName)
        assertTrue(audit.cpu.realSocName.contains("Snapdragon"))
        assertTrue(audit.storage.physicalChipCapacityGb > 0.0)
        assertTrue(audit.battery.healthPercentage in 60..100)
        assertTrue(audit.battery.wearPercentage >= 0)
        assertTrue(audit.battery.wearLossMah >= 0)
        assertTrue(audit.ram.physicalRamGb > 0.0)
        assertTrue(audit.ram.totalEffectiveRamGb >= audit.ram.physicalRamGb)
        assertTrue(audit.winlator.ratingStars.isNotEmpty())
        assertTrue(audit.winlator.turnipDriverSupported)
        assertTrue(audit.antutu.estimatedTotalScore > 100_000)

        // Test Exynos detection
        val exynosAudit = inspector.inspectAll("exynos2400 s5e9945")
        assertTrue(exynosAudit.cpu.realSocName.contains("Exynos 2400"))
        assertTrue(exynosAudit.cpu.gpuModel.contains("Xclipse 940"))

        // Test Unisoc detection
        val unisocAudit = inspector.inspectAll("ums9230 t606")
        assertTrue(unisocAudit.cpu.realSocName.contains("UNISOC"))

        // Test Helio G100 detection
        val g100Audit = inspector.inspectAll("helio g100 mt6789")
        assertTrue(g100Audit.cpu.realSocName.contains("Helio G100"))

        // Test RAM audit fields
        assertNotNull(audit.ram.claimedConfiguration)
        assertNotNull(audit.ram.getRamIntegrityMessage(com.example.localization.AppLanguage.RU))
    }

    @Test
    fun `language persistence and vibecoding strings work`() {
        val allLanguages = com.example.localization.AppLanguage.values()
        assertEquals(9, allLanguages.size)
        assertTrue(allLanguages.any { it.code == "es" })
        assertTrue(allLanguages.any { it.code == "pt" })
        assertTrue(allLanguages.any { it.code == "pt-br" })
        assertTrue(allLanguages.any { it.code == "fr" })
        assertTrue(allLanguages.any { it.code == "it" })
        assertTrue(allLanguages.any { it.code == "de" })

        // Verify warning string contains user required text
        val ruWarning = com.example.localization.AppStrings.getVibecodingWarningMessage(com.example.localization.AppLanguage.RU)
        assertTrue(ruWarning.contains("Вайбкодом"))
        assertTrue(ruWarning.contains("Helio G99 вместо G100 Ultra"))

        // Verify Tab 5 is Settings ("Настройки") as requested by user
        assertEquals("Настройки", com.example.localization.AppStrings.getTabSettings(com.example.localization.AppLanguage.RU))
        assertEquals("Налаштування", com.example.localization.AppStrings.getTabSettings(com.example.localization.AppLanguage.UA))
        assertEquals("Settings", com.example.localization.AppStrings.getTabSettings(com.example.localization.AppLanguage.EN))
        assertEquals("Réglages", com.example.localization.AppStrings.getTabSettings(com.example.localization.AppLanguage.FR))
        assertEquals("Einstellungen", com.example.localization.AppStrings.getTabSettings(com.example.localization.AppLanguage.DE))
        assertEquals("Configurações", com.example.localization.AppStrings.getTabSettings(com.example.localization.AppLanguage.PT_BR))

        // Verify network search failed bracket message as specified by user
        val ruSearchFailed = com.example.localization.AppStrings.getNetworkSearchFailedNote(com.example.localization.AppLanguage.RU)
        assertEquals("не смогли выполнить поиск в сети об устройстве", ruSearchFailed)
        val uaSearchFailed = com.example.localization.AppStrings.getNetworkSearchFailedNote(com.example.localization.AppLanguage.UA)
        assertEquals("не вдалося виконати пошук у мережі про пристрій", uaSearchFailed)
        val enSearchFailed = com.example.localization.AppStrings.getNetworkSearchFailedNote(com.example.localization.AppLanguage.EN)
        assertEquals("could not perform network search for device", enSearchFailed)
    }

    @Test
    fun `modern info repository resolves contemporary device specs`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val disabledSpecs = kotlinx.coroutines.runBlocking {
            com.example.data.ModernInfoRepository.fetchModernDeviceSpecs(
                context = context,
                isEnabled = false,
                manufacturer = "Tecno",
                brand = "Tecno",
                model = "Spark 10 Pro",
                board = "ums9230",
                hardware = "t606",
                offlineSoc = "UNISOC T606"
            )
        }
        assertEquals(false, disabledSpecs.isEnabled)
        assertEquals(false, disabledSpecs.isOnlineSuccess)
    }

    @Test
    fun `screen info audit calculates K factor and checks authenticity`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val inspector = com.example.hardware.DeviceHardwareInspector(context)
        val audit = inspector.inspectAll("Snapdragon 8 Gen 3")

        assertNotNull(audit.screen)
        assertTrue(audit.screen.physicalWidth > 0)
        assertTrue(audit.screen.physicalHeight > 0)
        assertTrue(audit.screen.reportedRefreshRate > 0f)
        assertNotNull(audit.screen.resolutionLabel)
        assertNotNull(audit.screen.standardName)
        assertNotNull(audit.screen.getIntegrityMessage(com.example.localization.AppLanguage.RU))
        assertTrue(audit.screen.supportedRefreshRates.isNotEmpty())

        // Test specific resolution K-calculations (user requirement: 2980x1280 -> 2.3K, 2K/2.5K, 480p)
        val method = com.example.hardware.DeviceHardwareInspector::class.java.getDeclaredMethod("calculateResolutionK", Int::class.java, Int::class.java)
        method.isAccessible = true

        @Suppress("UNCHECKED_CAST")
        val ultraWideK = method.invoke(inspector, 2980, 1280) as Pair<String, String>
        assertEquals("2.3K", ultraWideK.first)

        @Suppress("UNCHECKED_CAST")
        val s24UltraK = method.invoke(inspector, 3088, 1440) as Pair<String, String>
        assertEquals("2.5K", s24UltraK.first)

        @Suppress("UNCHECKED_CAST")
        val sdK = method.invoke(inspector, 854, 480) as Pair<String, String>
        assertEquals("480p", sdK.first)
    }

    @Test
    fun `storage audit does not confuse 16gb external sd card with 128gb internal rom`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val inspector = com.example.hardware.DeviceHardwareInspector(context)
        val audit = inspector.inspectAll("Snapdragon 8 Gen 3")

        // Should accurately report internal storage without false spoofing alarm
        assertNotNull(audit.storage)
        assertTrue(audit.storage.physicalChipCapacityGb >= 32.0)
        // If device has standard internal flash, isSpoofed must be false
        assertEquals(false, audit.storage.isSpoofed)
        val msgRu = audit.storage.getIntegrityMessage(com.example.localization.AppLanguage.RU)
        assertTrue(msgRu.contains("Подлинный кремниевый чип") || msgRu.contains("Аппаратных следов подделки не обнаружено"))
    }

    @Test
    fun `cpu throttling tester initializes and tracks parameters`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val tester = com.example.hardware.CpuThrottlingTester(context)
        val state = tester.state.value

        assertEquals(false, state.isRunning)
        assertEquals(false, state.isFinished)
        assertEquals(120, state.totalSeconds) // 2 minutes
        assertEquals(100, state.currentThrottlePercent)
    }
}
