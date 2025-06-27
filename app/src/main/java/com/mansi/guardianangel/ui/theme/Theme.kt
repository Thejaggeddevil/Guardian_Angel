package com.mansi.guardianangel.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Red500,
    background = Black,
    surface = DarkGray,
    onPrimary = White,
    onBackground = White,
    onSurface = White
)

private val LightColorPalette = lightColors(
    primary = Red500,
    background = White,
    surface = LightGray,
    onPrimary = Black,
    onBackground = Black,
    onSurface = Black
)

@Composable
fun GuardianAngelTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    MaterialTheme(colors = colors, typography = Typography, shapes = Shapes, content = content)
}
