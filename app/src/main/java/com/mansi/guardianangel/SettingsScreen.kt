package com.mansi.guardianangel

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.google.firebase.auth.FirebaseAuth
import com.mansi.guardianangel.AppViewModel
import androidx.compose.ui.res.stringResource
import com.mansi.guardianangel.R

@Composable
fun SettingsScreen(navController: NavController) {
    val isHindi = AppViewModel.isHindi.value
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(stringResource(R.string.settings_title), style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(16.dp))
        Text(stringResource(R.string.my_account), style = MaterialTheme.typography.subtitle1)
        Spacer(Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.appearance))
            Spacer(Modifier.width(12.dp))
            // Appearance options (light/dark/system) can be implemented as a DropdownMenu or SegmentedButton
            Text(stringResource(R.string.dark_mode))
            Switch(checked = AppViewModel.isDarkMode.value, onCheckedChange = {
                AppViewModel.toggleTheme()
            })
        }
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.language))
            Spacer(Modifier.width(12.dp))
            Switch(checked = isHindi, onCheckedChange = {
                AppViewModel.toggleLanguage()
            })
            Spacer(Modifier.width(8.dp))
            Text(if (isHindi) stringResource(R.string.hindi) else stringResource(R.string.english))
        }
        Spacer(Modifier.height(24.dp))
        Button(onClick = {
            FirebaseAuth.getInstance().signOut()
            navController.navigate("login")
        }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.logout))
        }
    }
}
