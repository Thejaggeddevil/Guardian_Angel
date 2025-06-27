package com.mansi.guardianangel

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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


@Composable
fun ClickableWarning(onClick: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.exclamation_mark),
        contentDescription = "Warning Icon",
        modifier = Modifier
            .size(60.dp)
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
    val userName = viewModel.username ?: "User"
    val selectedContacts = remember { mutableStateListOf("", "", "") }

    val contactPickers = (0..2).map { index ->
        rememberLauncherForActivityResult(ActivityResultContracts.PickContact()) { uri: Uri? ->
            uri?.let {
                val cursor = context.contentResolver.query(uri, null, null, null, null)
                cursor?.use {
                    if (it.moveToFirst()) {
                        val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                        if (nameIndex >= 0) {
                            val name = it.getString(nameIndex)
                            selectedContacts[index] = name
                        }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("GUARDIAN ANGEL", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1B41))
            IconButton(onClick = { onMenuClick() }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hello, $userName",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1B41)
            )

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                (0..2).forEach { index ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(56.dp)
                            .border(1.dp, Color(0xFF1A1B41), shape = MaterialTheme.shapes.medium)
                            .background(Color(0xFFE8F5E9), shape = MaterialTheme.shapes.large)
                            .clickable { contactPickers[index].launch(null) }
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(text = selectedContacts[index].ifEmpty { "Choose Contact" })
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Transparent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                ClickableWarning {
                    Toast.makeText(context, "🚨 SOS Triggered", Toast.LENGTH_SHORT).show()
                    AppViewModel.sendSOS(context, ArrayList(selectedContacts))
                }

            }

            Spacer(modifier = Modifier.height(10.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Send SOS", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1B41))
                Text("Press button for help", color = Color(0xFF1A1B41), fontSize = 14.sp)
            }
        }
    }
}
