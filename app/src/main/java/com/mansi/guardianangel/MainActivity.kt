package com.mansi.guardianangel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.mansi.guardianangel.data.PrefsManager
import com.mansi.guardianangel.ui.theme.GuardianAngelTheme

class MainActivity : ComponentActivity() {

    private val viewModel: AppViewModel by viewModels()

    // 🌍 Apply saved locale before app starts
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.applySavedLocale(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Load dark mode + language from SharedPreferences
        viewModel.isDarkMode.value = PrefsManager.isDarkMode(this)
        viewModel.isHindi.value = PrefsManager.getLangPref(this)

        // 🛂 Request Contact permission
        val contactPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (!granted) {
                Toast.makeText(
                    this,
                    "📵 Contact access denied. Add contact won't work!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        contactPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)

        // 📍 Request Location permission
        val locationPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "✅ Location permission granted", Toast.LENGTH_SHORT).show()
                checkLocationAndRedirect()
            } else {
                Toast.makeText(this, "⚠️ Location permission denied", Toast.LENGTH_LONG).show()
            }
        }
        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        // 🖍️ Set content with loaded dark mode theme
        setContent {
            GuardianAngelTheme(darkTheme = viewModel.isDarkMode.value) {
                GuardianAngel(viewModel)
            }
        }
    }

    // 🌐 Check if GPS is OFF and redirect
    private fun checkLocationAndRedirect() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!gpsEnabled && !networkEnabled) {
            Toast.makeText(this, "📍 Please turn on location!", Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}
