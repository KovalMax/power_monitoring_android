package com.kovalmax.powermonitoring.domain

interface IApplicationRepository {
    suspend fun getAppId(): String
}