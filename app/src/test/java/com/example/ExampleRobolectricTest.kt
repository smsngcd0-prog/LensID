package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.CompanyDatabase
import com.example.data.SensorDatabase
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
    fun `sensor database contains entries and matcher works`() {
        assertTrue(SensorDatabase.CATALOG.isNotEmpty())
        assertTrue(SensorDatabase.CATALOG.any { it.vendor == "SmartSens" })
        assertTrue(SensorDatabase.CATALOG.any { it.vendor == "GalaxyCore" })

        // Test budget Redmi matching (SmartSens / GalaxyCore / Samsung JN1)
        val redmiMatch = SensorDatabase.matchSensor(
            megapixels = 50.0,
            widthMm = 4.64f,
            heightMm = 3.48f,
            pixelArrayW = 8160,
            pixelArrayH = 6120,
            isFront = false,
            focalLengthMm = 3.98f,
            manufacturer = "Xiaomi",
            model = "Redmi A3 Pro"
        )
        assertNotNull(redmiMatch)
        assertTrue(redmiMatch.probableModels.any { it.contains("SC500CS") || it.contains("GC50E0") || it.contains("JN1") })

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
        assertTrue(driverMatch.confidence.contains("100%"))
    }

    @Test
    fun `company database contains suppliers and device analysis works`() {
        assertTrue(CompanyDatabase.ALL_COMPANIES.isNotEmpty())
        assertTrue(CompanyDatabase.ALL_COMPANIES.any { it.id == "smartsens" })

        // Budget Redmi analysis
        val redmiBudgetAnalysis = CompanyDatabase.getDeviceSupplierAnalysis(
            manufacturer = "Xiaomi",
            brand = "Redmi",
            model = "Redmi A3",
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
}
