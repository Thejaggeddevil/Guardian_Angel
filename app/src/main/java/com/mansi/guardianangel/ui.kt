package com.mansi.guardianangel
//
//import android.widget.Toast
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowDropDown
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material.icons.filled.Warning
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.foundation.Image
//
//import androidx.compose.runtime.Composable
//
//import androidx.compose.ui.res.painterResource
//
//import androidx.compose.ui.layout.ContentScale
//
//
//@Composable
//fun ClickableWarning(onClick: () -> Unit) {
//    Image(
//        painter = painterResource(id = R.drawable.exclamation_mark),
//        contentDescription = "Warning Icon",
//        modifier = Modifier
//            .size(60.dp)
//            .clickable { onClick() },
//        contentScale = ContentScale.Fit
//    )
//}
//
//
//@Composable
//fun GuardianAngelMainScreen() {
//    val context = LocalContext.current
//    val contactNames = listOf("Choose Contact", "Mom", "Dad", "Friend")
//    val selectedContacts = remember { mutableStateListOf("", "", "") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFE8F5E9))
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // Top Bar with logo and menu
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 8.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text("GUARDIAN ANGEL", fontSize = 18.sp, fontWeight = FontWeight.Bold,color = Color(0xFF1A1B41))
//            Icon(Icons.Default.Menu, contentDescription = "Menu",)
//        }
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(horizontal = 24.dp, vertical = 32.dp),
//            verticalArrangement = Arrangement.SpaceBetween,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "Hello, Username",
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color(0xFF1A1B41)
//            )
//
//           // Spacer(modifier = Modifier.height(16.dp))
//
//
//            // 3 Dropdown contact pickers
//            Column(
//                verticalArrangement = Arrangement.spacedBy(10.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                repeat(3) { index ->
//                    var expanded by remember { mutableStateOf(false) }
//                    var selectedText by remember { mutableStateOf(contactNames[0]) }
//
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth(0.8f)
//                            .height(56.dp)
//                            .border(1.dp, Color(0xFF1A1B41 ), shape = MaterialTheme.shapes.medium)
//                            .background(
//                                Color(0xFFE8F5E9 ),
//                                shape =MaterialTheme.shapes.large
//                            )
//                            .clickable { expanded = true }
//                            .padding(horizontal = 16.dp),
//                        contentAlignment = Alignment.CenterStart
//                    ) {
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            Text(text = selectedText)
//                            Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown",)
//                        }
//
//                        DropdownMenu(
//                            expanded = expanded,
//                            onDismissRequest = { expanded = false }
//                        ) {
//                            contactNames.drop(1).forEach { contact ->
//                                DropdownMenuItem(
//                                    text = { Text(contact) },
//                                    onClick = {
//                                        selectedText = contact
//                                        selectedContacts[index] = contact
//                                        expanded = false
//                                    }
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//
//
//            // Spacer(modifier = Modifier.height(24.dp))
//
//            // Warning SOS Button (Red Circle)
//            Box(
//                modifier = Modifier
//                    .size(200.dp)
//                    .background(Color.Red, CircleShape)
//                    .clickable {
//                        Toast.makeText(context, "🚨 SOS Triggered", Toast.LENGTH_SHORT).show()
//                    },
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.Warning,
//                    contentDescription = "SOS",
//                    tint = Color(0xFFE8F5E9 ),
//                    modifier = Modifier.size(96.dp)
//                )
//            }
//
//            Spacer(modifier = Modifier.height(9.dp))
//
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Text(
//                    text = "Send SOS",
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF1A1B41)
//                )
//                Text(
//                    text = "Press button for help",
//                    color = Color(0xFF1A1B41),
//                    fontSize = 14.sp,
//
//                )
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GuardianAngelScreenPreview() {
//    GuardianAngelMainScreen()
//}
