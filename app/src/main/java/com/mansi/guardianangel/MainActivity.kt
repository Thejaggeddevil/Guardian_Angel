package com.mansi.guardianangel

import android.content.Context
import android.os.*
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mansi.guardianangel.ui.theme.GuardianAngelTheme
import com.mansi.guardianangel.util.ShakeDetector

class MainActivity : ComponentActivity() {


    private lateinit var shakeDetector: ShakeDetector
    val savedContacts = PrefsManager.getContacts(context)  // returns List<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Shake Detector
        shakeDetector = ShakeDetector(this) {
            Toast.makeText(this, "Shake detected! Sending SOS...", Toast.LENGTH_SHORT).show()
            vibratePhone()
            AppViewModel.sendSOS(context, savedContacts)


        }

        shakeDetector.start()

        // Set Compose UI
        setContent {
            GuardianAngelTheme(darkTheme = AppViewModel.isDarkMode.value) {
                GuardianAngel() // Loads AppNav() internally
            }
        }
    }

    private fun vibratePhone() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        vibrator?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.vibrate(
                    VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
                )
            } else {
                @Suppress("DEPRECATION")
                it.vibrate(500)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        shakeDetector.stop()
    }
}