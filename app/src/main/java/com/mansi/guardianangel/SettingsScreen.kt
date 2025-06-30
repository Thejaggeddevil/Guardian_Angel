package com.mansi.guardianangel

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.mansi.guardianangel.data.PrefsManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: AppViewModel = viewModel()
) {
    val context = LocalContext.current
    val isDarkMode by viewModel.isDarkMode
    val userEmail = FirebaseAuth.getInstance().currentUser?.email ?: "Anonymous"

    val currentLangIsHindi = remember { mutableStateOf(PrefsManager.getLangPref(context)) }
    val selectedLang = remember { mutableStateOf(if (currentLangIsHindi.value) "hi" else "en") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 🔐 Account Section
            Text(text = stringResource(R.string.my_account), style = MaterialTheme.typography.titleMedium)
            Text(text = userEmail, style = MaterialTheme.typography.bodyLarge)

            // 🌗 Dark Mode
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.dark_mode))
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { viewModel.toggleTheme(context) }
                )
            }

            // 🌐 Language Selection via Radio Buttons
            Text(text = stringResource(R.string.language), style = MaterialTheme.typography.titleMedium)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedLang.value = "en"
                        currentLangIsHindi.value = false
                        PrefsManager.setLangPref(context, false)
                        restartAppWithLocale(context, "en")
                    }
            ) {
                RadioButton(
                    selected = selectedLang.value == "en",
                    onClick = {
                        selectedLang.value = "en"
                        currentLangIsHindi.value = false
                        PrefsManager.setLangPref(context, false)
                        restartAppWithLocale(context, "en")
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("English")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedLang.value = "hi"
                        currentLangIsHindi.value = true
                        PrefsManager.setLangPref(context, true)
                        restartAppWithLocale(context, "hi")
                    }
            ) {
                RadioButton(
                    selected = selectedLang.value == "hi",
                    onClick = {
                        selectedLang.value = "hi"
                        currentLangIsHindi.value = true
                        PrefsManager.setLangPref(context, true)
                        restartAppWithLocale(context, "hi")
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("हिंदी")
            }

            // 🚀 Placeholder Section
            Text(text = "More Settings", style = MaterialTheme.typography.titleMedium)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Notifications")
                Switch(checked = true, onCheckedChange = {}) // placeholder
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("About App")
                Text("v1.0.0", style = MaterialTheme.typography.bodySmall)
            }

            // 🔓 Logout
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(stringResource(R.string.logout), color = MaterialTheme.colorScheme.onError)
            }
        }
    }
}

// 🌀 Restart App with Locale
fun restartAppWithLocale(context: Context, lang: String) {
    LocaleHelper.setLocale(context, lang)
    val intent = Intent(context, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    context.startActivity(intent)
    (context as? Activity)?.finish()
}
