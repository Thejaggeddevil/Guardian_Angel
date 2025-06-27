package com.mansi.guardianangel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.provider.ContactsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@SuppressLint("ContextCastToActivity")
@Composable

fun ContactPickerScreen(navController: NavController, index: Int) {
    val context = LocalContext.current

    // ✅ Make it a state so you can use it anywhere in this composable
    var contactNumber by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            contactNumber = ContactPicker.handleContactPicked(context, data)
            AppViewModel.selectedContactNumber.value = contactNumber
            contactNumber?.let { FirebaseHelper.saveContact(it) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("📞 Pick Contact", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
            launcher.launch(intent)
        }) {
            Text("Pick Contact")
        }

        Spacer(Modifier.height(12.dp))

        // ✅ Now it will work
        Text("Selected: ${contactNumber ?: "None"}")

        Spacer(Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Go Back")
        }
    }
}
