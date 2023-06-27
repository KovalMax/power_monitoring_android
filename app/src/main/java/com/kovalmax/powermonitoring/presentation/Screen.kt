package com.kovalmax.powermonitoring.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Main : Screen(
        "main",
        "Home",
        Icons.Default.List,
    )
    object SignIn : Screen(
        "signIn",
        "Sign In",
        Icons.Default.Info,
    )
    object AddDevice : Screen(
        "addDevice",
        "New Device",
        Icons.Default.AddCircle,
    )
    object DeviceDetails : Screen(
        "deviceDetails",
        "Device Details",
        Icons.Default.Info,
    )
    object Profile : Screen(
        "profile",
        "Profile",
        Icons.Default.Person,
    )
}
