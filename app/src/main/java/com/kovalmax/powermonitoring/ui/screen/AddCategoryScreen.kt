package com.kovalmax.powermonitoring.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kovalmax.powermonitoring.ui.model.AddCategoryViewModel
import com.kovalmax.powermonitoring.ui.Screen

@Composable
fun AddCategoryScreen(
    nav: NavController,
    categoryViewModel: AddCategoryViewModel = hiltViewModel()
) {
    val category: String by categoryViewModel.category.observeAsState("")

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Spacer(modifier = Modifier.height(175.dp))
        CategoryField(category, onCategoryChange = { categoryViewModel.onCategoryChange(it) })
        Spacer(modifier = Modifier.height(75.dp))
        AddCategoryButton(onAddNewCategory = {
            try {
                categoryViewModel.onAddCategoryClick(category)
                nav.navigate(Screen.Main.route)
            } catch (e: Exception) {
                println("Invalid Data!")
            }
        })
    }
}

@Composable
fun CategoryField(category: String, onCategoryChange: (String) -> Unit) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = category,
        onValueChange = onCategoryChange,
        label = {
            Text("Category Name")
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        )
    )
}

@Composable
fun AddCategoryButton(onAddNewCategory: () -> Unit) {
    Button(onClick = onAddNewCategory, Modifier.fillMaxWidth()) {
        Text(text = "Add new category")
    }
}