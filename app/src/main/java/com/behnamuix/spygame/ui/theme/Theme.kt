package com.behnamuix.spygame.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SpyDarkColorScheme = darkColorScheme(
    primary = Charcoal,
    onPrimary = Color.White,

    secondary = Cream,
    onSecondary = Charcoal,

    tertiary = Danger,
    onTertiary = Charcoal,

    background = Charcoal,
    onBackground = Cream,

    surface = Charcoal,
    onSurface = Cream,

    surfaceVariant = Color(0xFF2A3138),
    onSurfaceVariant = Cream,

    outline = Copper,

    error = Danger,
    onError = Charcoal
)
@Composable
fun SpyTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SpyDarkColorScheme,
        typography = Typography,
        content = content
    )
}