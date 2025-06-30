package com.mansi.guardianangel

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNav(viewModel: AppViewModel) {
    val navController = rememberNavController()
    NavGraph(navController = navController, viewModel = viewModel)
}

@Composable
fun NavGraph(navController: NavHostController, viewModel: AppViewModel) {
    NavHost(navController = navController, startDestination = "login") {

        // 🔐 Auth Screens
        composable("login") {
            LoginScreen(navController = navController, viewModel = viewModel)
        }

        composable("signup") {
            SignupScreen(navController = navController, viewModel = viewModel)
        }

        // 🏠 Main Home (with drawer access)
        composable("home") {
            GuardianAngelMainScreen(
                onMenuClick = {
                    navController.navigate("menudrawer")
                },
                navController = navController,
                viewModel = viewModel
            )
        }

        // ⚙️ Menu Drawer
        composable("menudrawer") {
            AppDrawer(
                navController = navController,
                closeDrawer = {
                    navController.popBackStack("home", inclusive = false)
                },
                onItemSelected = { selected ->
                    when (selected) {
                        "history" -> navController.navigate("history")
                        "settings" -> navController.navigate("settings")
                        "logout" -> {
                            viewModel.logout()
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                        else -> navController.navigate("home")
                    }
                }
            )
        }

        // ⚙️ Settings and SOS History
        composable("settings") {
            SettingsScreen(navController = navController)
        }

        composable("history") {
            SOSHistoryScreen(navController = navController)
        }

        // 🤖 Chatbot screen (GPT)
        composable("chatbot") {
            ChatbotScreen(navController = navController)
        }

        // 🏠 Home shortcut (no drawer, only from bottom nav)
        composable("home_ui") {
            GuardianAngelMainScreen(
                onMenuClick = {
                    navController.navigate("menudrawer")
                },
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}
