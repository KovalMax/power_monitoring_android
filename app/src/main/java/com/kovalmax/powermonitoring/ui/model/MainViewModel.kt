package com.kovalmax.powermonitoring.ui.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kovalmax.powermonitoring.domain.DispatcherProvider
import com.kovalmax.powermonitoring.domain.IApplicationRepository
import com.kovalmax.powermonitoring.domain.IDispatcherProvider
import com.kovalmax.powermonitoring.domain.IExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val expenseRepository: IExpenseRepository,
    private val applicationRepository: IApplicationRepository,
    private val contextProvider: IDispatcherProvider,
) : ViewModel(), CoroutineScope {
    private val jobTracker: CompletableJob = Job()
    override val coroutineContext get() = contextProvider.provideIOContext(jobTracker)

    private lateinit var appId: String

    val sum = MutableLiveData(0)

    init {
        launch {
            appId = applicationRepository.getAppId()

//            val total = expenseRepository.getThisMonthExpenses(appId)
//                .map { expense -> expense.amount.toInt() }
//
//            withContext(DispatcherProvider.provideUIContext(jobTracker)) {
//                sum.value = total.sum()
//            }
        }
    }
}