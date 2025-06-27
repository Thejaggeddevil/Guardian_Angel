package com.mansi.guardianangel
//
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//@Composable
//fun AppDrawer(
//    onNavigateToHistory: () -> Unit,
//    onNavigateToSettings: () -> Unit,
//    onLogout: () -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFE8F5E9))
//            .padding(16.dp)
//    ) {
//        Text("←", style = MaterialTheme.typography.headlineMedium, fontSize = 36.sp)
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        DrawerItem("🕘 SOS History", onNavigateToHistory)
//        DrawerItem("⚙️ Settings", onNavigateToSettings)
//        DrawerItem("🚪 Log out", onLogout)
//
//        Spacer(modifier = Modifier.weight(1f))
//        Text(
//            "Terms and conditions",
//            fontSize = 15.sp,
//            modifier = Modifier.padding(bottom = 12.dp),
//            style = MaterialTheme.typography.labelMedium
//        )
//    }
//}
//
//@Composable
//fun DrawerItem(title: String, onClick: () -> Unit) {
//    Text(
//        text = title,
//        fontSize = 20.sp,
//        color = Color(0xFF1A1B41),
//
//        fontWeight = FontWeight.Bold,
//        modifier = Modifier
//            .fillMaxWidth()
//
//            .clickable { onClick() }
//            .padding(vertical = 20.dp),
//        style = MaterialTheme.typography.bodyLarge
//    )
//}
//@Preview(showBackground = true)
//@Composable
//fun threedot(){
//    AppDrawer(onNavigateToHistory = {}, onNavigateToSettings = {}, onLogout = {})
//}
