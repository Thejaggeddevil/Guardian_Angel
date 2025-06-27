package com.mansi.guardianangel

import androidx.compose.runtime.Composable
import com.mansi.guardianangel.AppNav
import com.mansi.guardianangel.ui.theme.GuardianAngelTheme

@Composable
fun GuardianAngel() {
    GuardianAngelTheme(darkTheme = AppViewModel.isDarkMode.value) {
        AppNav()
    }
}
