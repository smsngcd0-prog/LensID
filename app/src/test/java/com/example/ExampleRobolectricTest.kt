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
        
        // Test matching a 50MP 1/1.56" sensor (e.g. IMX890/IMX766)
        val match = SensorDatabase.matchSensor(
            megapixels = 50.3,
            widthMm = 8.2f,
            heightMm = 6.15f,
            pixelArrayW = 8192,
            pixelArrayH = 6144,
            isFront = false,
            focalLengthMm = 5.6f,
            manufacturer = "OnePlus"
        )
        assertNotNull(match)
        assertEquals("Sony", match.vendorName)
        assertTrue(match.probableModels.any { it.contains("IMX890") || it.contains("IMX766") })
    }

    @Test
    fun `company database contains suppliers and device analysis works`() {
        assertTrue(CompanyDatabase.ALL_COMPANIES.isNotEmpty())

        val xiaomiAnalysis = CompanyDatabase.getDeviceSupplierAnalysis(
            manufacturer = "Xiaomi",
            brand = "Xiaomi",
            model = "14 Ultra",
            board = "aurora",
            hardware = "qcom"
        )
        assertNotNull(xiaomiAnalysis)
        assertTrue(xiaomiAnalysis.mostLikelySensorVendors.any { it.contains("Sony") })
        assertTrue(xiaomiAnalysis.opticPartnership?.contains("Leica") == true)

        val samsungAnalysis = CompanyDatabase.getDeviceSupplierAnalysis(
            manufacturer = "Samsung",
            brand = "Samsung",
            model = "Galaxy S24 Ultra",
            board = "pineapple",
            hardware = "qcom"
        )
        assertTrue(samsungAnalysis.mostLikelySensorVendors.any { it.contains("Samsung") })
    }
}
