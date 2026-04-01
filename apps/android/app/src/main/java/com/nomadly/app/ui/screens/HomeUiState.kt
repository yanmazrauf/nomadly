package com.nomadly.app.ui.screens

import com.nomadly.app.model.Destination

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Success(
        val destinations: List<Destination>,
        val filteredDestinations: List<Destination>,
        val selectedCategory: String,
        val remainingCount: Int
    ) : HomeUiState

    data class Error(val message: String) : HomeUiState
}
