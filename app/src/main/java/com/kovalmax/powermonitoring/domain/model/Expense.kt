package com.kovalmax.powermonitoring.domain.model

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

data class Expense(
    val applicationId: String = "",
    val amount: String = "0",
    val category: Category? = null,
    val createdAt: Timestamp = Timestamp.now()
) {
    fun categoryName(): String {
        return category?.name ?: ""
    }

    fun createdAtToFormat(format: String = "dd.MM.yyyy"): String {
        return SimpleDateFormat(format, Locale.getDefault())
            .format(createdAt.toDate())
    }
}