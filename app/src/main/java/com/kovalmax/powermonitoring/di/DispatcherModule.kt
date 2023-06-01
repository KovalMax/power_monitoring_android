package com.kovalmax.powermonitoring.di

import com.kovalmax.powermonitoring.domain.DispatcherProvider
import com.kovalmax.powermonitoring.domain.IDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Singleton
    @Provides
    fun provideDispatcherProvider(): IDispatcherProvider {
        return DispatcherProvider
    }
}