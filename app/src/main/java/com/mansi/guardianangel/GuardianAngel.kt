package com.mansi.guardianangel

import androidx.compose.runtime.Composable
import com.mansi.guardianangel.ui.theme.GuardianAngelTheme

@Composable
fun GuardianAngel(viewModel: AppViewModel) {
    // Wrap everything in your theme (dark mode toggled via ViewModel)
    GuardianAngelTheme(darkTheme = viewModel.isDarkMode.value) {
        AppNav(viewModel = viewModel)
    }
}

