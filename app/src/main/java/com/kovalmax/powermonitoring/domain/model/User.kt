package com.kovalmax.powermonitoring.domain.model

data class User(
    val email: String = "",
    val displayName: String? = null,
    val photoUrl: String? = null,
    val id: String = "",
    val tokenId: String = "",
)

data class UserSettings(
    val userId: String = "",
    val usePush: Boolean = false,
    val useTelegram: Boolean = false,
    val chatId: Long = 0
)
