package com.kovalmax.powermonitoring.persistence

import com.google.firebase.installations.FirebaseInstallations
import com.kovalmax.powermonitoring.domain.IApplicationRepository
import kotlinx.coroutines.tasks.await

class ApplicationRepository(
    private val installations: FirebaseInstallations
) : IApplicationRepository {
    override suspend fun getAppId(): String {
        return installations.id.await().toString()
    }
}
