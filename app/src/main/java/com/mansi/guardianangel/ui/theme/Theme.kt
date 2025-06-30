package com.mansi.guardianangel.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
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
private val LightColors = lightColorScheme(
    primary = MidnightBlue,
    background = CreamColor,
    onPrimary = CreamColor,
    secondary = EmergencyRed
)

private val DarkColors = darkColorScheme(
    primary = CreamColor,
    background = MidnightBlue,
    onPrimary = MidnightBlue,
    secondary = EmergencyRed
)

@Composable
fun GuardianAngelTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    MaterialTheme(colors = colors, typography = Typography, shapes = Shapes, content = content)
}

