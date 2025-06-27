package com.mansi.guardianangel

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PrefsManager {
    private lateinit var prefs: SharedPreferences
    private const val CONTACTS_KEY = "emergency_contacts"
    private const val DARK_MODE_KEY = "dark_mode"
    private const val LANG_HINDI_KEY = "lang_hindi"

    fun init(context: Context) {
        prefs = context.getSharedPreferences("guardian_prefs", Context.MODE_PRIVATE)
        AppViewModel.isDarkMode.value = prefs.getBoolean(DARK_MODE_KEY, false)
        AppViewModel.isHindi.value = prefs.getBoolean(LANG_HINDI_KEY, false)
    }

    fun saveThemePref(isDark: Boolean) {
        prefs.edit().putBoolean(DARK_MODE_KEY, isDark).apply()
    }

    fun saveLangPref(isHindi: Boolean) {
        prefs.edit().putBoolean(LANG_HINDI_KEY, isHindi).apply()
    }

    fun saveContacts(contacts: List<String>) {
        val json = Gson().toJson(contacts)
        prefs.edit().putString(CONTACTS_KEY, json).apply()
    }

    fun getContacts(): List<String> {
        val json = prefs.getString(CONTACTS_KEY, null) ?: return listOf("", "", "")
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, type)
    }
}
