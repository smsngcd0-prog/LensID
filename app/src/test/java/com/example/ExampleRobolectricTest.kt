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
    }
}
