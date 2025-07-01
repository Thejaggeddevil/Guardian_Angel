package com.mansi.guardianangel

import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mansi.guardianangel.data.PrefsManager

@Composable
fun ClickableWarning(onClick: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.exclamation_mark),
        contentDescription = "Warning Icon",
        modifier = Modifier
            .size(120.dp)
            .clickable { onClick() },
        contentScale = ContentScale.Fit
    )
}

@Composable
fun GuardianAngelMainScreen(
    onMenuClick: () -> Unit,
    navController: NavController,
    viewModel: AppViewModel = viewModel()
) {
    val context = LocalContext.current
    val userName by viewModel.username

    val savedContacts = remember { mutableStateListOf<ContactPicker.ContactData>() }
    var customName by remember { mutableStateOf("") }
    var customNumber by remember { mutableStateOf("") }
    var useSavedContact by remember { mutableStateOf(true) }

    // Load username when screen opens
    LaunchedEffect(Unit) {
        viewModel.loadUsername(context)
    }

    // Check location enabled
    LaunchedEffect(Unit) {
        val locationManager = context.getSystemService(LocationManager::class.java)
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
            !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        ) {
            Toast.makeText(context, "⚠️ Please enable location!", Toast.LENGTH_LONG).show()
            context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }

    // Load saved contacts
    LaunchedEffect(Unit) {
        val contactStrings = PrefsManager.getContacts(context)
        savedContacts.clear()
        contactStrings.forEach { entry ->
            val parts = entry.split("::")
            if (parts.size == 2) {
                savedContacts.add(ContactPicker.ContactData(name = parts[0], number = parts[1]))
            }
        }
    }

    val contactPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickContact()
    ) { uri: Uri? ->
        val contact = ContactPicker.extractContact(context, uri)
        contact?.let {
            val exists = savedContacts.any { c -> c.number == it.number }
            if (!exists && savedContacts.size < 1) {
                savedContacts.add(it)
                val formattedList = savedContacts.map { c -> "${c.name}::${c.number}" }
                PrefsManager.saveContacts(context, formattedList)
                FirebaseHelper.saveContact(it.number, it.name)
            } else {
                Toast.makeText(context, "Contact already added or max limit reached", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("GUARDIAN ANGEL", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1B41))
                IconButton(onClick = onMenuClick) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Hello, $userName", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1B41))
            Spacer(modifier = Modifier.height(24.dp))

            // 🔄 Toggle
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Saved Contact", color = if (useSavedContact) Color(0xFFEF5350) else Color.Gray)
                Switch(
                    checked = useSavedContact,
                    onCheckedChange = { useSavedContact = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF1A1B41),
                        uncheckedThumbColor = Color(0xFF1A1B41)
                    )
                )
                Text("Custom Contact", color = if (!useSavedContact) Color(0xFFEF5350) else Color.Gray)
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (useSavedContact) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    savedContacts.forEach { contact ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(56.dp)
                                .border(1.dp, Color(0xFF1A1B41), RoundedCornerShape(12.dp))
                                .background(Color.White, RoundedCornerShape(12.dp))
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("📱 ${contact.name}", color = Color(0xFF1A1B41))
                            IconButton(onClick = {
                                savedContacts.remove(contact)
                                val updatedList = savedContacts.map { "${it.name}::${it.number}" }
                                PrefsManager.saveContacts(context, updatedList)
                                FirebaseHelper.deleteContact(context, contact.number)

                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.dlt_icon),
                                    contentDescription = "Delete",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }

                    if (savedContacts.size < 1) {
                        Button(
                            onClick = { contactPickerLauncher.launch(null) },
                            modifier = Modifier.fillMaxWidth(0.5f)
                        ) {
                            Text("Add Contact")
                        }
                    }
                }
            } else {
                Column {
                    OutlinedTextField(
                        value = customName,
                        onValueChange = { customName = it },
                        label = { Text("Custom Contact Name") },
                        modifier = Modifier.fillMaxWidth(0.9f),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = customNumber,
                        onValueChange = { customNumber = it },
                        label = { Text("Custom Contact Number") },
                        modifier = Modifier.fillMaxWidth(0.9f),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 🚨 SOS
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Transparent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                ClickableWarning {
                    val selected = if (useSavedContact && savedContacts.isNotEmpty()) {
                        savedContacts.first()
                    } else if (!useSavedContact && customNumber.isNotBlank()) {
                        ContactPicker.ContactData(customName.ifBlank { "Unknown" }, customNumber)
                    } else null

                    if (selected == null) {
                        Toast.makeText(context, "⚠️ No valid contact selected", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "🚨 SOS Triggered", Toast.LENGTH_SHORT).show()
                        viewModel.sendSOS(
                            context = context,
                            contacts = listOf(selected.number)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(9.dp))
            Text("Send SOS", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1B41))
            Text("Press button for help", color = Color(0xFF1A1B41), fontSize = 14.sp)
        }

        // ✅ Bottom Nav
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color(0xFFE8F5E9))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { navController.navigate("home_ui") },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1A1B41))
            ) {
                Text("🏠 Home", color = Color.White)
            }

            Button(
                onClick = { navController.navigate("chatbot") },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1A1B41))
            ) {
                Text("🤖 Guardian-GPT", color = Color.White)
            }
        }
    }
}
