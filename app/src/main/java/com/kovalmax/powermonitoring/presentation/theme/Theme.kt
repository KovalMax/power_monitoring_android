package com.kovalmax.powermonitoring.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = primaryGreen,
    primaryVariant = green,
    secondary = textColorLight,
    surface = lightGrey,
    onPrimary = accentAmber,
    onSurface = accentAmber
)

private val DarkColorPalette = darkColors(
    //main background color
    primary = primaryCharcoal,
    //used for text color
    secondary = textColorDark,
    //background
    surface = lightGreyAlpha,
    onPrimary = accentAmber,
    onSurface = accentAmber

)

@Composable
fun PowerMonitoringTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}