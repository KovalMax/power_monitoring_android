package com.kovalmax.powermonitoring.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.installations.ktx.installations
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Singleton
    @Provides
    fun provideFirebaseInstallations(): FirebaseInstallations {
        return Firebase.installations
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }
}