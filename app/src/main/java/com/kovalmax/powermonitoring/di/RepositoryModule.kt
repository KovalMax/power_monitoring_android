package com.kovalmax.powermonitoring.di

import com.google.firebase.firestore.FirebaseFirestore
import com.kovalmax.powermonitoring.domain.DeviceRepositoryInterface
import com.kovalmax.powermonitoring.domain.UserRepositoryInterface
import com.kovalmax.powermonitoring.persistence.DeviceRepository
import com.kovalmax.powermonitoring.persistence.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideDeviceRepository(firestore: FirebaseFirestore): DeviceRepositoryInterface {
        return DeviceRepository(firestore)
    }

    @Singleton
    @Provides
    fun provideUserRepository(firestore: FirebaseFirestore): UserRepositoryInterface {
        return UserRepository(firestore)
    }
}