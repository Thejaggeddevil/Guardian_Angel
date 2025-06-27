package com.mansi.guardianangel

import android.content.Context
import android.telephony.SmsManager
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.mansi.guardianangel.LocationUtils
import com.mansi.guardianangel.PrefsManager
import java.text.SimpleDateFormat
import java.util.*

import android.content.Intent
import android.net.Uri


object AppViewModel : ViewModel() {


    val isDarkMode = mutableStateOf(false)
    val isHindi = mutableStateOf(false)
    val selectedContactNumber = mutableStateOf<String?>(null)

    private val _username = mutableStateOf("User")
    val username: State<String> get() = _username

    fun setUsername(name: String) {
        _username.value = name
    }

    // 🆕 SOS History State
    val sosHistory = mutableStateListOf<SOsData>()

    fun addSOSRecord(record: SOsData) {
        sosHistory.add(record)
    }

    fun deleteSOSRecord(record: SOsData) {
        sosHistory.remove(record)
    }

    fun toggleTheme() {
        isDarkMode.value = !isDarkMode.value
        PrefsManager.saveThemePref(isDarkMode.value)
    }

    fun toggleLanguage() {
        isHindi.value = !isHindi.value
        PrefsManager.saveLangPref(isHindi.value)
    }

fun sendSOS(context: Context, contactList: List<String>) {
    LocationUtils.getLastLocation(context) { location ->
        val message = if (location != null) {
            "🚨 SOS! I need help. My location: https://maps.google.com/?q=${location.latitude},${location.longitude}"
        } else {
            "🚨 SOS! I need help. Location unavailable."
        }


        for (phoneNumber in contactList) {
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
    }
}
}