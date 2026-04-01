package com.nomadly.app.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nomadly.app.data.di.RepositoryProvider
import com.nomadly.app.ui.screens.DestinationDetailScreen
import com.nomadly.app.ui.screens.DestinationDetailViewModel
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
                navController      = navController,
                onDestinationClick = { destination ->
                    navController.navigate(Screen.DestinationDetail.createRoute(destination.id))
                }
            )
        }

        composable(
            route     = Screen.DestinationDetail.route,
            arguments = listOf(navArgument("destinationId") { type = NavType.StringType })
        ) { backStackEntry ->
            val destinationId = backStackEntry.arguments?.getString("destinationId") ?: ""

            // Resolve the destination through the repository so the NavGraph has
            // no direct MockRepository dependency — swap RepositoryProvider here
            // when the backend is ready.
            val destination = RepositoryProvider.destinationRepository
                .let { repo ->
                    // Blocking look-up acceptable here: mock impl is synchronous.
                    // Replace with a loading screen + LaunchedEffect if the repo
                    // becomes async (e.g. network).
                    com.nomadly.app.data.mock.MockRepository.destinations.find { it.id == destinationId }
                }

            destination?.let { dest ->
                DestinationDetailScreen(
                    destination = dest,
                    onBack      = { navController.popBackStack() },
                    viewModel   = viewModel(
                        viewModelStoreOwner = backStackEntry,
                        factory             = DestinationDetailViewModel.provideFactory(dest)
                    )
                )
            }
        }

        composable(Screen.SavedBoards.route) {
            SavedBoardsScreen(navController = navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
    }
}
