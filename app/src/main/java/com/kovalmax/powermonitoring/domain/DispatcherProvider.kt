package com.kovalmax.powermonitoring.domain

import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface IDispatcherProvider {
    fun provideUIContext(job: CompletableJob? = null): CoroutineContext
    fun provideIOContext(job: CompletableJob? = null): CoroutineContext
}

object DispatcherProvider : IDispatcherProvider {
    override fun provideUIContext(job: CompletableJob?): CoroutineContext {
        return if (job != null) Dispatchers.Main + job else Dispatchers.Main
    }

    override fun provideIOContext(job: CompletableJob?): CoroutineContext {
        return if (job != null) Dispatchers.IO + job else Dispatchers.IO
    }
}