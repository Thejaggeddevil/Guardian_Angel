package com.mansi.guardianangel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.mansi.guardianangel.data.PrefsManager
import java.text.SimpleDateFormat
import java.util.*

class AppViewModel : ViewModel() {

    var isDarkMode = mutableStateOf(false)
    private set

    var isHindi = mutableStateOf(false)
    private set

            private val _username = mutableStateOf("User")
    val username: State<String> get() = _username

    val sosHistory = mutableStateListOf<SOsData>()

    fun setUsername(name: String) {
        _username.value = name
    }

    fun addSOSRecord(record: SOsData) {
        sosHistory.add(0, record)  // add to top
        FirebaseHelper.saveSOS(record)
    }

    fun deleteSOSRecord(record: SOsData) {
        sosHistory.remove(record)
        // ❗You may also want to delete from Firebase — let me know if you want that
    }

    fun loadSOSHistory() {
        FirebaseHelper.getSOSHistory { list ->
            sosHistory.clear()
            sosHistory.addAll(list.sortedByDescending { it.timestamp })
        }
    }
    fun logout() {
        FirebaseService.logout()
    }


    fun toggleTheme(context: Context) {
        val newDark = !isDarkMode.value
        isDarkMode.value = newDark
        PrefsManager.setDarkMode(context, newDark)
    }


    fun toggleLanguage(context: Context, isHindiSelected: Boolean) {
        isHindi.value = isHindiSelected
        PrefsManager.setLangPref(context, isHindiSelected)
    }
    fun restartAppWithLocale(context: Context, lang: String) {
        LocaleHelper.setLocale(context, lang)
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }



    fun sendSOS(context: Context, contacts: List<String>) {
        LocationUtils.getLastLocation(context) { location ->
            val timeStamp = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date())
            val message = if (location != null) {
                "🚨 SOS! I need help. My location: https://maps.google.com/?q=${location.latitude},${location.longitude}"
            } else {
                "🚨 SOS! I need help. Location unavailable."
            }

            for (phoneNumber in contacts) {
                if (phoneNumber.isNotBlank()) {
                    try {
                        val smsIntent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("smsto:$phoneNumber")
                            putExtra("sms_body", message)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        context.startActivity(smsIntent)
                    } catch (e: Exception) {
                        Toast.makeText(context, "❌ Failed to send SMS to $phoneNumber", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            addSOSRecord(
                SOsData(
                    timestamp = timeStamp,
                    locationLink = location?.let {
                        "https://maps.google.com/?q=${it.latitude},${it.longitude}"
                    } ?: "Unavailable",
                    contacts = contacts
                )
            )
        }
    }

    fun ensureLocationOn(context: Context) {
        if (!LocationUtils.isLocationEnabled(context)) {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}
