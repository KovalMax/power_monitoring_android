package com.kovalmax.powermonitoring.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kovalmax.powermonitoring.ui.model.ListExpensesViewModel
import com.kovalmax.powermonitoring.domain.model.Expense

@Composable
fun ListExpensesScreen(expensesViewModel: ListExpensesViewModel = hiltViewModel()) {
    val expenses: List<Expense> by expensesViewModel.expenses.observeAsState(listOf())

    Column {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(expenses) {
                Row {
                    Text(
                        text = it.createdAtToFormat(),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(25.dp)
                    )
                    Text(
                        text = it.amount,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(25.dp)
                    )
                    Text(
                        text = it.categoryName(),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(25.dp)
                    )
                }
            }
        }
    }
}