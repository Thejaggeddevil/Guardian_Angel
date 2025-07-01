package com.mansi.guardianangel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

    // ✅ Set username + cache locally
    fun setUsername(name: String, context: Context? = null) {
        _username.value = name
        context?.let {
            PrefsManager.setUsername(it, name)
            Log.d("AppViewModel", "✅ Username saved locally: $name")
        }
    }

    // ✅ Load from Firestore OR fallback to local
    fun loadUsername(context: Context) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid.isNullOrBlank()) {
            _username.value = PrefsManager.getUsername(context)
            Log.w("AppViewModel", "⚠️ No UID, loaded from prefs: ${_username.value}")
            return
        }

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { doc ->
                val name = doc.getString("name")
                if (!name.isNullOrBlank()) {
                    _username.value = name
                    PrefsManager.setUsername(context, name)
                    Log.d("AppViewModel", "✅ Username loaded from Firestore: $name")
                } else {
                    _username.value = PrefsManager.getUsername(context)
                    Log.w("AppViewModel", "⚠️ Firestore name missing, using prefs: ${_username.value}")
                }
            }
            .addOnFailureListener {
                _username.value = PrefsManager.getUsername(context)
                Log.e("AppViewModel", "❌ Firestore fetch failed: ${it.message}")
            }
    }

    // ✅ Save SOS in Firebase + update list
    fun addSOSRecord(record: SOsData) {
        sosHistory.add(0, record)
        FirebaseHelper.saveSOS(record)
    }

    fun deleteSOSRecord(record: SOsData) {
        sosHistory.remove(record)
    }

    fun loadSOSHistory() {
        FirebaseHelper.getSOSHistory { list ->
            sosHistory.clear()
            sosHistory.addAll(list.sortedByDescending { it.timestamp })
        }
    }

    fun logout() {
        FirebaseService.logout()
        _username.value = "User"
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

    // ✅ Send SOS SMS + Save History
    fun sendSOS(context: Context, contacts: List<String>) {
        LocationUtils.getLastLocation(context) { location ->
            val timeStamp = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date())
            val message = if (location != null) {
                "🚨 SOS! I need help. My location: https://maps.google.com/?q=${location.latitude},${location.longitude}"
            } else {
                "🚨 SOS! I need help. Location unavailable."
            }

            contacts.forEach { phoneNumber ->
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
