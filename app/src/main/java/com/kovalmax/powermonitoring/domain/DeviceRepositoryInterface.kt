package com.kovalmax.powermonitoring.domain

import com.kovalmax.powermonitoring.domain.model.Device
import com.kovalmax.powermonitoring.domain.model.Event

interface DeviceRepositoryInterface {
    fun addDevice(device: Device)

    suspend fun getDevices(userId: String): List<Device>
    suspend fun getLastEvent(id: String): Event?
}