package com.kovalmax.powermonitoring.domain

import com.kovalmax.powermonitoring.domain.model.User
import com.kovalmax.powermonitoring.domain.model.UserSettings

interface UserRepositoryInterface {
    fun createNewUser(user: User)
    fun updateTelegramUserSettings(userId: String, useTg: Boolean)
    suspend fun checkIfUserExists(userId: String): Boolean
    suspend fun userSettings(userId: String): UserSettings?
}