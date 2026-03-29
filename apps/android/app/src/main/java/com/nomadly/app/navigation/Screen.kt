package com.nomadly.app.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object DestinationDetail : Screen("destination_detail/{destinationId}") {
        fun createRoute(destinationId: String) = "destination_detail/$destinationId"
    }
    object SavedBoards : Screen("saved_boards")
    object Profile : Screen("profile")
}
