package com.kovalmax.powermonitoring.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.installations.FirebaseInstallations
import com.kovalmax.powermonitoring.domain.IApplicationRepository
import com.kovalmax.powermonitoring.domain.ICategoryRepository
import com.kovalmax.powermonitoring.domain.IExpenseRepository
import com.kovalmax.powermonitoring.domain.UserRepositoryInterface
import com.kovalmax.powermonitoring.persistence.ApplicationRepository
import com.kovalmax.powermonitoring.persistence.CategoryRepository
import com.kovalmax.powermonitoring.persistence.ExpenseRepository
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
    fun provideApplicationRepository(
        installations: FirebaseInstallations
    ): IApplicationRepository {
        return ApplicationRepository(installations)
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(firestore: FirebaseFirestore): ICategoryRepository {
        return CategoryRepository(firestore)
    }

    @Singleton
    @Provides
    fun provideExpenseRepository(firestore: FirebaseFirestore): IExpenseRepository {
        return ExpenseRepository(firestore)
    }

    @Singleton
    @Provides
    fun provideUserRepository(firestore: FirebaseFirestore): UserRepositoryInterface {
        return UserRepository(firestore)
    }
}