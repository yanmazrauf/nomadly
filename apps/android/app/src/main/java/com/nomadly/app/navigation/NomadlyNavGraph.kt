package com.nomadly.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nomadly.app.data.mock.MockRepository
import com.nomadly.app.ui.screens.DestinationDetailScreen
import com.nomadly.app.ui.screens.HomeScreen
import com.nomadly.app.ui.screens.OnboardingScreen
import com.nomadly.app.ui.screens.ProfileScreen
import com.nomadly.app.ui.screens.SavedBoardsScreen

@Composable
fun NomadlyNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onStartExploring = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                onSignIn = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                onDestinationClick = { destination ->
                    navController.navigate(Screen.DestinationDetail.createRoute(destination.id))
                }
            )
        }

        composable(
            route = Screen.DestinationDetail.route,
            arguments = listOf(
                navArgument("destinationId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val destinationId = backStackEntry.arguments?.getString("destinationId") ?: ""
            val destination = MockRepository.destinations.find { it.id == destinationId }
            destination?.let {
                DestinationDetailScreen(
                    destination = it,
                    onBack = { navController.popBackStack() }
                )
            }
        }

        composable(Screen.SavedBoards.route) {
            SavedBoardsScreen(
                navController = navController
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                navController = navController
            )
        }
    }
}
