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
import com.mansi.guardianangel.data.AppSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: AppViewModel = viewModel()
) {
    val context = LocalContext.current
    val isDarkMode = AppSettings.isDarkMode.value
    val isHindi = AppSettings.isHindi.value
    val userEmail = FirebaseAuth.getInstance().currentUser?.email ?: "Anonymous"

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
            // 🔐 Account Info
            Text(text = stringResource(R.string.my_account), style = MaterialTheme.typography.titleMedium)
            Text(text = userEmail, style = MaterialTheme.typography.bodyLarge)

            // 🌗 Dark Mode Toggle
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.dark_mode))
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = {
                        AppSettings.toggleDarkMode(context, it)
                    }
                )
            }

            // 🌐 Language Selection (Hindi / English)
            Text(text = stringResource(R.string.language), style = MaterialTheme.typography.titleMedium)

            LanguageOptionRow(context, "en", "English", !isHindi)
            LanguageOptionRow(context, "hi", "हिंदी", isHindi)

            // 🛠 More Settings (Placeholder)
            Text(text = "More Settings", style = MaterialTheme.typography.titleMedium)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Notifications")
                Switch(checked = true, onCheckedChange = {}) // just placeholder
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("About App")
                Text("v1.0.0", style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 🔓 Logout Button
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

@Composable
fun LanguageOptionRow(context: Context, langCode: String, label: String, selected: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                AppSettings.toggleLanguage(context, langCode == "hi")
            }
            .padding(vertical = 4.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = {
                AppSettings.toggleLanguage(context, langCode == "hi")
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(label)
    }
}
