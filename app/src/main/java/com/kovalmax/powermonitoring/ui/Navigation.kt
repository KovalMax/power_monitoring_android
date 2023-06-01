package com.kovalmax.powermonitoring.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kovalmax.powermonitoring.ui.screen.AddCategoryScreen
import com.kovalmax.powermonitoring.ui.screen.AddExpenseScreen
import com.kovalmax.powermonitoring.ui.screen.ListExpensesScreen
import com.kovalmax.powermonitoring.ui.screen.MainScreen
import com.kovalmax.powermonitoring.ui.screen.SignInScreen

@Composable
fun Navigation() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Screen.SignIn.route) {
        composable(route = Screen.Main.route) {
            MainScreen(nav = nav)
        }
        composable(route = Screen.AddExpense.route) {
            AddExpenseScreen(nav = nav)
        }
        composable(route = Screen.ListExpenses.route) {
            ListExpensesScreen()
        }
        composable(route = Screen.AddCategory.route) {
            AddCategoryScreen(nav = nav)
        }
        composable(route = Screen.SignIn.route) {
            SignInScreen(nav = nav)
        }
    }
}