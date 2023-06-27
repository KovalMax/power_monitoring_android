package com.kovalmax.powermonitoring.domain.model

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

data class Device(
    val id: String = "",
    val userId: String = "",
    val deviceId: String = "",
    val deviceName: String = "",
    val deviceLocation: String = "",
    val active: Boolean = true,
    var lastEvent: Event? = null,
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now(),
) {


    fun createdAtToFormat(format: String = "dd.MM.yyyy"): String {
        return SimpleDateFormat(format, Locale.getDefault())
            .format(createdAt.toDate())
    }
}