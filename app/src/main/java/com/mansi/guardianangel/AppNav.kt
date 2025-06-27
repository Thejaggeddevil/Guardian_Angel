package com.mansi.guardianangel

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument

@Composable
fun AppNav() {
    val navController = rememberNavController()
    NavGraph(navController = navController)
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {

        // Login screen
        composable("login") {
            LoginScreen(navController)
        }

        // Sign up screen
        composable("signup") {
            SignupScreen(navController)
        }

        // Home screen
        composable("home") {
            GuardianAngelMainScreen(
                onMenuClick = { navController.navigate("menudrawer") },
                navController = navController


            )
        }
        composable("menudrawer") {
            AppDrawer(navController, closeDrawer = { navController.navigate("home") })
        }

        // Settings
        composable("settings") {
            SettingsScreen(navController)
        }

        // Contact Picker with index param
        composable(
            route = "contacts?index={index}",
            arguments = listOf(navArgument("index") {
                type = NavType.IntType
                defaultValue = 0
            })
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index") ?: 0
            ContactPickerScreen(navController, index)
        }

        // SOS history
        composable("history") {
            SOSHistoryScreen(navController)
        }
    }
}
