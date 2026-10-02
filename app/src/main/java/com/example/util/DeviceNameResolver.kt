package com.example.util

import android.os.Build
import java.io.BufferedReader
import java.io.InputStreamReader

object DeviceNameResolver {

    data class ResolvedDevice(
        val marketingName: String,
        val fullBrandTitle: String,
        val internalCode: String
    )

    fun resolve(manufacturer: String, brand: String, model: String, deviceCode: String): ResolvedDevice {
        // 1. Try reading marketing name directly from Android system properties
        val marketNameProp = getSystemProperty("ro.product.marketname")
            ?: getSystemProperty("ro.product.vendor.marketname")
            ?: getSystemProperty("ro.product.odm.marketname")
            ?: getSystemProperty("ro.config.marketing_name")

        if (!marketNameProp.isNullOrBlank()) {
            return ResolvedDevice(
                marketingName = marketNameProp.trim(),
                fullBrandTitle = "$brand $marketNameProp".trim(),
                internalCode = "$model ($deviceCode)"
            )
        }

        // 2. Normalize input strings
        val cleanModel = model.trim().replace("а", "a", ignoreCase = true).replace("с", "c", ignoreCase = true)
        val cleanDevice = deviceCode.trim().lowercase()
        val lowerModel = cleanModel.lowercase()

        // 3. Known Models Database (Redmi, Samsung, Xiaomi, POCO, Realme, Pixel)
        val matchedMarketing = when {
            // Redmi A-series
            lowerModel.contains("a7") || cleanDevice.contains("a7") -> "Redmi A7 Pro"
            lowerModel.contains("a3 pro") || cleanDevice.contains("lake") -> "Redmi A3 Pro"
            lowerModel.contains("a3") || cleanDevice.contains("blue") -> "Redmi A3"
            lowerModel.contains("a2") || cleanDevice.contains("water") -> "Redmi A2"
            lowerModel.contains("a1") || cleanDevice.contains("ice") -> "Redmi A1"
            lowerModel.contains("a4") -> "Redmi A4 5G"

            // Redmi C-series
            lowerModel.contains("14c") || cleanDevice.contains("c3n") -> "Redmi 14C"
            lowerModel.contains("13c") || cleanDevice.contains("gale") -> "Redmi 13C"
            lowerModel.contains("12c") || cleanDevice.contains("earth") -> "Redmi 12C"
            lowerModel.contains("10c") || cleanDevice.contains("fog") -> "Redmi 10C"
            lowerModel.contains("9c") || cleanDevice.contains("angelica") -> "Redmi 9C"

            // Redmi Note series
            lowerModel.contains("note 14 pro+") || cleanDevice.contains("amethyst") -> "Redmi Note 14 Pro+ 5G"
            lowerModel.contains("note 14 pro") || cleanDevice.contains("malachite") -> "Redmi Note 14 Pro 5G"
            lowerModel.contains("note 14") || cleanDevice.contains("beryl") -> "Redmi Note 14 5G"
            lowerModel.contains("note 13 pro+") || cleanDevice.contains("zircon") -> "Redmi Note 13 Pro+ 5G"
            lowerModel.contains("note 13 pro") || cleanDevice.contains("garnet") -> "Redmi Note 13 Pro 5G"
            lowerModel.contains("note 13") || cleanDevice.contains("sapphire") -> "Redmi Note 13 4G/5G"

            // Xiaomi Flagship series
            lowerModel.contains("15 ultra") -> "Xiaomi 15 Ultra"
            lowerModel.contains("15 pro") -> "Xiaomi 15 Pro"
            lowerModel.contains("15") && !lowerModel.contains("redmi") -> "Xiaomi 15"
            lowerModel.contains("14 ultra") || cleanDevice.contains("aurora") -> "Xiaomi 14 Ultra"
            lowerModel.contains("14 pro") || cleanDevice.contains("shennong") -> "Xiaomi 14 Pro"
            lowerModel.contains("14") && !lowerModel.contains("redmi") -> "Xiaomi 14"
            lowerModel.contains("13 ultra") || cleanDevice.contains("ishtar") -> "Xiaomi 13 Ultra"

            // Samsung Galaxy S-series
            lowerModel.contains("s26 ultra") -> "Samsung Galaxy S26 Ultra"
            lowerModel.contains("s26") -> "Samsung Galaxy S26"
            lowerModel.contains("s25 ultra") -> "Samsung Galaxy S25 Ultra"
            lowerModel.contains("s25") -> "Samsung Galaxy S25"
            lowerModel.contains("s24 ultra") || cleanDevice.contains("e3q") -> "Samsung Galaxy S24 Ultra"
            lowerModel.contains("s24") || cleanDevice.contains("e1q") || cleanDevice.contains("e2q") -> "Samsung Galaxy S24"
            lowerModel.contains("s23 ultra") || cleanDevice.contains("dm3q") -> "Samsung Galaxy S23 Ultra"

            // Samsung Galaxy A-series
            lowerModel.contains("a55") || cleanDevice.contains("a55x") -> "Samsung Galaxy A55 5G"
            lowerModel.contains("a35") || cleanDevice.contains("a35x") -> "Samsung Galaxy A35 5G"
            lowerModel.contains("a25") || cleanDevice.contains("a25x") -> "Samsung Galaxy A25 5G"
            lowerModel.contains("a15") || cleanDevice.contains("a15x") -> "Samsung Galaxy A15"

            // POCO series
            lowerModel.contains("f6 pro") -> "POCO F6 Pro"
            lowerModel.contains("f6") -> "POCO F6"
            lowerModel.contains("x6 pro") -> "POCO X6 Pro 5G"
            lowerModel.contains("x6") -> "POCO X6 5G"
            lowerModel.contains("m6 pro") -> "POCO M6 Pro"

            // Google Pixel
            lowerModel.contains("pixel 9 pro xl") -> "Google Pixel 9 Pro XL"
            lowerModel.contains("pixel 9 pro") -> "Google Pixel 9 Pro"
            lowerModel.contains("pixel 9") -> "Google Pixel 9"
            lowerModel.contains("pixel 8 pro") -> "Google Pixel 8 Pro"
            lowerModel.contains("pixel 8a") -> "Google Pixel 8a"
            lowerModel.contains("pixel 8") -> "Google Pixel 8"

            else -> null
        }

        val resolvedName = matchedMarketing ?: cleanModel
        val brandPrefix = if (resolvedName.startsWith(brand, ignoreCase = true) || resolvedName.startsWith(manufacturer, ignoreCase = true)) {
            resolvedName
        } else {
            "$brand $resolvedName"
        }

        return ResolvedDevice(
            marketingName = resolvedName,
            fullBrandTitle = brandPrefix,
            internalCode = "$model ($deviceCode)"
        )
    }

    private fun getSystemProperty(propName: String): String? {
        return try {
            val clazz = Class.forName("android.os.SystemProperties")
            val getMethod = clazz.getMethod("get", String::class.java)
            val res = getMethod.invoke(null, propName) as? String
            if (!res.isNullOrBlank()) res.trim() else null
        } catch (e: Throwable) {
            null
        }
    }
}
