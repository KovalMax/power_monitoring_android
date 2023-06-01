package com.kovalmax.powermonitoring.domain

import com.kovalmax.powermonitoring.domain.model.Expense

interface IExpenseRepository {
    fun saveExpense(expense: Expense)

    suspend fun getExpenses(applicationId: String): List<Expense>

    suspend fun getThisMonthExpenses(applicationId: String): List<Expense>
}