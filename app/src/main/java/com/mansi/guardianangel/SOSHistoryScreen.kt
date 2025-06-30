package com.mansi.guardianangel

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mansi.guardianangel.AppViewModel
import com.mansi.guardianangel.SOsData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SOSHistoryScreen(
    navController: NavController,
    viewModel: AppViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadSOSHistory()
    }

    val context = LocalContext.current
    val history = viewModel.sosHistory

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SOS History", fontSize = 24.sp, fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(history) { sos ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("🕒 ${sos.timestamp}", fontWeight = FontWeight.SemiBold)
                            IconButton(onClick = { viewModel.deleteSOSRecord(sos) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("📍 Location:", fontWeight = FontWeight.Bold)
                        Text(
                            text = sos.locationLink,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable {
                                val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(sos.locationLink))
                                context.startActivity(mapIntent)
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("📤 Sent to:", fontWeight = FontWeight.Bold)
                        Column {
                            sos.contacts.forEach {
                                Text("• $it")
                            }
                        }
                    }
                }
            }
        }
    }
}
