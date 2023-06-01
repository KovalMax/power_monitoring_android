package com.kovalmax.powermonitoring.ui

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object SignIn: Screen("signIn")
    object AddExpense : Screen("addExpense")
    object ListExpenses : Screen("listExpenses")
    object AddCategory : Screen("addCategory")
}
