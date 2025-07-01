package com.mansi.guardianangel.data

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.mansi.guardianangel.LocaleHelper

object AppSettings {
    val isDarkMode = mutableStateOf(false)
    val isHindi = mutableStateOf(false)

    fun loadFromPrefs(context: Context) {
        isDarkMode.value = PrefsManager.isDarkMode(context)
        isHindi.value = PrefsManager.getLangPref(context)
    }

    fun toggleDarkMode(context: Context, newValue: Boolean) {
        isDarkMode.value = newValue
        PrefsManager.setDarkMode(context, newValue)
    }

    fun toggleLanguage(context: Context, newValue: Boolean) {
        isHindi.value = newValue
        PrefsManager.setLangPref(context, newValue)
        val langCode = if (newValue) "hi" else "en"
        LocaleHelper.setLocale(context, langCode)
        (context as? Activity)?.recreate()
    }
}
