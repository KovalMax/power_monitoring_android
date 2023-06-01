package com.kovalmax.powermonitoring.ui.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kovalmax.powermonitoring.domain.IApplicationRepository
import com.kovalmax.powermonitoring.domain.ICategoryRepository
import com.kovalmax.powermonitoring.domain.IDispatcherProvider
import com.kovalmax.powermonitoring.domain.IExpenseRepository
import com.kovalmax.powermonitoring.domain.model.Category
import com.kovalmax.powermonitoring.domain.model.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val expenseRepository: IExpenseRepository,
    private val categoryRepository: ICategoryRepository,
    private val applicationRepository: IApplicationRepository,
    private val contextProvider: IDispatcherProvider,
) : ViewModel(), CoroutineScope {
    private val jobTracker = Job()
    private lateinit var appId: String

    val amount = MutableLiveData("")
    val selectedCategory = MutableLiveData<Category?>()
    val categories = MutableLiveData(listOf<Category>())

    override val coroutineContext get() = contextProvider.provideUIContext(jobTracker)

    init {
        launch {
            appId = applicationRepository.getAppId()

            val list = categoryRepository.getCategories(appId)
            if (list.isNotEmpty()) {
                withContext(contextProvider.provideUIContext(jobTracker)) {
                    selectedCategory.value = list.first()
                    categories.value = list
                }
            }
        }
    }

    fun onAmountChange(newAmount: String) {
        amount.value = newAmount.filter { it.isDigit() }
    }

    fun onCategoryChange(changedCategory: Category) {
        selectedCategory.value = changedCategory
    }

    fun onNewExpense(amount: String, category: Category?) {
        if (amount.isEmpty()) {
            throw Exception()
        }

        val newExpense = Expense(appId, amount, category)
        expenseRepository.saveExpense(newExpense)
    }
}
