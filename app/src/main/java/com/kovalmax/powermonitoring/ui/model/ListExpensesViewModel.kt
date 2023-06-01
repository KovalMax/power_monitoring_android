package com.kovalmax.powermonitoring.ui.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kovalmax.powermonitoring.domain.IApplicationRepository
import com.kovalmax.powermonitoring.domain.IDispatcherProvider
import com.kovalmax.powermonitoring.domain.IExpenseRepository
import com.kovalmax.powermonitoring.domain.model.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListExpensesViewModel @Inject constructor(
    private val expenseRepository: IExpenseRepository,
    private val applicationRepository: IApplicationRepository,
    private val contextProvider: IDispatcherProvider,
) : ViewModel(), CoroutineScope {
    private var jobTracker = Job()
    private lateinit var appId: String

    override val coroutineContext get() = contextProvider.provideIOContext(jobTracker)

    val expenses = MutableLiveData(listOf<Expense>())

    init {
        launch {
            appId = applicationRepository.getAppId()

            val expensesList = expenseRepository.getExpenses(appId)

            withContext(contextProvider.provideUIContext(jobTracker)) {
                expenses.value = expensesList
            }
        }
    }
}