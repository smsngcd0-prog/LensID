package com.example.model

data class DeviceInfo(
    val manufacturer: String,
    val brand: String,
    val model: String,
    val deviceCode: String,
    val board: String,
    val hardware: String,
    val socModel: String,
    val androidVersion: String,
    val apiLevel: Int,
    val totalLogicalCameras: Int,
    val totalPhysicalCameras: Int
)
