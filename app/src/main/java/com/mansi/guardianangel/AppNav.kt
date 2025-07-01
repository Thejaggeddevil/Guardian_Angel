package com.mansi.guardianangel

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNav(viewModel: AppViewModel) {
    val navController = rememberNavController()
    val isLoggedIn = FirebaseAuth.getInstance().currentUser != null
    val startDestination = if (isLoggedIn) "home" else "login"

    NavGraph(
        navController = navController,
        viewModel = viewModel,
        startDestination = startDestination
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: AppViewModel,
    startDestination: String
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it }, // 👉 Enter from right
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it }, // 👉 Exit to left
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it }, // 👈 Back enter from left
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it }, // 👈 Back exit to right
                animationSpec = tween(300)
            )
        }
    ) {
        composable("login") {
            LoginScreen(navController = navController, viewModel = viewModel)
        }

        composable("signup") {
            SignupScreen(navController = navController, viewModel = viewModel)
        }

        composable("home") {
            GuardianAngelMainScreen(
                onMenuClick = { navController.navigate("menudrawer") },
                navController = navController,
                viewModel = viewModel
            )
        }

        composable("menudrawer") {
            AppDrawer(
                navController = navController,
                closeDrawer = { navController.popBackStack("menudrawer", inclusive = false) },
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

        composable("settings") {
            SettingsScreen(navController = navController)
        }

        composable("history") {
            SOSHistoryScreen(navController = navController)
        }

        composable("chatbot") {
            ChatbotScreen(navController = navController)
        }

        composable("home_ui") {
            GuardianAngelMainScreen(
                onMenuClick = { navController.navigate("menudrawer") },
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}
