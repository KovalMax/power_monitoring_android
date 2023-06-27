package com.kovalmax.powermonitoring.domain.model

data class Event(
    val deviceId: String = "",
    val networkLevel: Int = 0,
    val batteryLevel: Int = 0,
    val powerState: Int = 0,
    val createdAt: Float = 0f,
)
