package com.kovalmax.powermonitoring.domain

import com.kovalmax.powermonitoring.domain.model.User

interface UserRepositoryInterface {
    fun createNewUser(user: User)

    suspend fun checkIfUserExists(userId: String): Boolean
}