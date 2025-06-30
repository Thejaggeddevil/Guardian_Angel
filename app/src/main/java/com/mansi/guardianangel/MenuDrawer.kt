package com.mansi.guardianangel

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppDrawer(
    navController: NavController,
    closeDrawer: () -> Unit,
    onItemSelected: (String) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Close Drawer",
            tint = Color(0xFF1A1B41),
            modifier = Modifier
                .size(36.dp)
                .clickable { closeDrawer() }
        )

        Spacer(modifier = Modifier.height(24.dp))

        DrawerItem("🕘 History") {
            navController.navigate("history")
            closeDrawer()
            onItemSelected("history")
        }

        DrawerItem("⚙️ Settings") {
            navController.navigate("settings")
            closeDrawer()
            onItemSelected("settings")
        }

        DrawerItem("🚪 Log out") {
            FirebaseAuth.getInstance().signOut()
            navController.navigate("login") {
                popUpTo(0) { inclusive = true } // clears backstack
            }
        }
        DrawerItem("💬 Chatbot") {
            navController.navigate("chatbot")
            closeDrawer()
        }


        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Terms and Conditions",
            fontSize = 15.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 12.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun DrawerItem(title: String, onClick: () -> Unit) {
    Text(
        text = title,
        fontSize = 20.sp,
        color = Color(0xFF1A1B41),
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 16.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}

//@Preview(showBackground = true)
//@Composable
//fun DrawerPreview() {
//    AppDrawer(
//        navController = androidx.navigation.compose.rememberNavController(),
//        closeDrawer = {}
//    )
//}
