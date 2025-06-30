package com.mansi.guardianangel

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate

object SettingUtils {

    // Applies the light or dark theme app-wide
    fun applyTheme(isDarkMode: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    // Detects current theme from system
    fun isDarkMode(context: Context?): Boolean {
        if (context == null) return false  // prevent crash if context is null

        val uiMode = context.resources?.configuration?.uiMode ?: return false
        return (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }
}
