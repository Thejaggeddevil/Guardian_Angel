package com.mansi.guardianangel
//
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Delete
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//data class SOSData(
//    val timestamp: String,
//    val contacts: List<String>
//)
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SOSHistoryScreen(
//    sosHistoryList: List<SOSData>,
//    onDeleteItem: (SOSData) -> Unit
//) {
//    Scaffold(
//        topBar = {
//            TopAppBar(title = { Text("History", fontSize = 28.sp, fontWeight = FontWeight.Medium) })
//        }
//    ) { padding ->
//        LazyColumn(modifier = Modifier.padding(padding)) {
//            items(sosHistoryList) { sos ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//
//                        .padding(8.dp),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            Text("🕒 Time: ${sos.timestamp}")
//                            IconButton(onClick = { onDeleteItem(sos) }) {
//                                Icon(Icons.Default.Delete, contentDescription = "Delete")
//                            }
//                        }
//                        Spacer(Modifier.height(8.dp))
//                        Text("📤 Sent to:")
//                        sos.contacts.forEach {
//                            Text("• $it")
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//@Preview(showBackground = true)
//@Composable
//fun soshis(){
//    SOSHistoryScreen(sosHistoryList = listOf(), onDeleteItem = {})
//}
