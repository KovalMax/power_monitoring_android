package com.kovalmax.powermonitoring.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kovalmax.powermonitoring.ui.model.MainViewModel
import com.kovalmax.powermonitoring.ui.Screen

@Composable
fun MainScreen(nav: NavController, mainViewModel: MainViewModel = hiltViewModel()) {
    val sum: Int by mainViewModel.sum.observeAsState(0)
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Spacer(modifier = Modifier.height(55.dp))
        TotalExpenses(sum)
        Spacer(modifier = Modifier.height(75.dp))
        AddNewExpense(nav = nav)
        Spacer(modifier = Modifier.height(15.dp))
        AddNewCategory(nav = nav)
        Spacer(modifier = Modifier.height(15.dp))
        ShowListOfExpenses(nav = nav)
    }
}

@Composable
fun TotalExpenses(amount: Int) {
    Column(verticalArrangement = Arrangement.Center) {
        Text(
            "Here is your expenses this month",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            amount.toString(),
            fontSize = 19.sp,
            modifier = Modifier.align(CenterHorizontally)
        )
    }
}

@Composable
fun AddNewExpense(nav: NavController) {
    Column(verticalArrangement = Arrangement.Center) {
        Button(onClick = { nav.navigate(Screen.AddExpense.route) }, Modifier.fillMaxWidth()) {
            Text(text = "Add new expense")
        }
    }
}

@Composable
fun ShowListOfExpenses(nav: NavController) {
    Column(verticalArrangement = Arrangement.Center) {
        Button(onClick = { nav.navigate(Screen.ListExpenses.route) }, Modifier.fillMaxWidth()) {
            Text(text = "Show list of expenses")
        }
    }
}

@Composable
fun AddNewCategory(nav: NavController) {
    Column(verticalArrangement = Arrangement.Center) {
        Button(onClick = { nav.navigate(Screen.AddCategory.route) }, Modifier.fillMaxWidth()) {
            Text(text = "Add new category")
        }
    }
}