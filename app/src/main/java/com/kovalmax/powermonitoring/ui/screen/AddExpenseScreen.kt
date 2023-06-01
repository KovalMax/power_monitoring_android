package com.kovalmax.powermonitoring.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kovalmax.powermonitoring.ui.model.AddExpenseViewModel
import com.kovalmax.powermonitoring.domain.model.Category
import com.kovalmax.powermonitoring.ui.Screen

@Composable
fun AddExpenseScreen(nav: NavController, expenseViewModel: AddExpenseViewModel = hiltViewModel()) {
    val amount: String by expenseViewModel.amount.observeAsState("")
    val categories: List<Category> by expenseViewModel.categories.observeAsState(listOf())
    val selectedCategory: Category? by expenseViewModel.selectedCategory.observeAsState(
        categories.firstOrNull()
    )

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Spacer(modifier = Modifier.height(175.dp))
        AmountField(
            amount = amount,
            onAmountChange = { expenseViewModel.onAmountChange(it) },
        )
        Spacer(modifier = Modifier.height(25.dp))
        ExpenseCategoryList(
            categories = categories,
            selectedCategory,
            onCategoryChange = { expenseViewModel.onCategoryChange(it) }
        )
        Spacer(modifier = Modifier.height(300.dp))
        AddExpenseButton(
            onAddNewExpense = {
                try {
                    expenseViewModel.onNewExpense(amount, selectedCategory)
                    nav.navigate(Screen.Main.route)
                } catch (e: Exception) {
                    println("Invalid Data!")
                }
            }
        )
    }
}

@Composable
fun AmountField(amount: String, onAmountChange: (String) -> Unit) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = amount,
        onValueChange = onAmountChange,
        label = {
            Text("Amount")
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        )
    )
}

@Composable
fun ExpenseCategoryList(
    categories: List<Category>,
    selectedCategory: Category?,
    onCategoryChange: (Category) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Text(text = "Select expense category")
    Spacer(modifier = Modifier.height(15.dp))
    Box {
        Row(modifier = Modifier
            .clickable { expanded = !expanded }
            .height(50.dp)
        ) {
            Text(selectedCategory?.name ?: "")
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                null,
                tint = MaterialTheme.colors.onSurface
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.padding(24.dp)
            ) {
                categories.forEachIndexed { _, category ->
                    DropdownMenuItem(onClick = {
                        onCategoryChange(category)
                        expanded = false
                    }) {
                        val isSelected = category == selectedCategory
                        val style = if (isSelected) {
                            MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colors.secondary
                            )
                        } else {
                            MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colors.onSurface
                            )
                        }

                        Text(text = category.name, style = style)
                    }
                }
            }
        }
    }

}

@Composable
fun AddExpenseButton(onAddNewExpense: () -> Unit) {
    Button(onClick = onAddNewExpense, Modifier.fillMaxWidth()) {
        Text(text = "Add new expense")
    }
}