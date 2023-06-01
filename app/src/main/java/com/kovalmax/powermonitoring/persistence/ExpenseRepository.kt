package com.kovalmax.powermonitoring.persistence

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.kovalmax.powermonitoring.domain.IExpenseRepository
import com.kovalmax.powermonitoring.domain.model.Expense
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class ExpenseRepository(
    private val firestore: FirebaseFirestore
) : IExpenseRepository {
    private val collectionName = "expenses"
    private val appIdFieldName = "applicationId"
    private val createAtFieldName = "createdAt"

    override fun saveExpense(expense: Expense) {
        firestore
            .collection(collectionName)
            .document(UUID.randomUUID().toString())
            .set(expense)
    }

    override suspend fun getExpenses(applicationId: String): List<Expense> {
        return firestore.collection(collectionName)
            .whereEqualTo(appIdFieldName, applicationId)
            .orderBy(createAtFieldName, Query.Direction.DESCENDING)
            .get()
            .await()
            .toObjects(Expense::class.java)
    }

    override suspend fun getThisMonthExpenses(applicationId: String): List<Expense> {
        val today = LocalDate.now()

        val startOfMonth = today
            .withDayOfMonth(1)
            .atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant()

        val endOfMonth = today
            .withDayOfMonth(today.lengthOfMonth())
            .atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant()

        return firestore
            .collection(collectionName)
            .whereEqualTo(appIdFieldName, applicationId)
            .whereGreaterThanOrEqualTo(
                createAtFieldName,
                Timestamp(startOfMonth.epochSecond, startOfMonth.nano)

            )
            .whereLessThan(
                createAtFieldName,
                Timestamp(endOfMonth.epochSecond, endOfMonth.nano)
            )
            .get()
            .await()
            .toObjects(Expense::class.java)
    }
}