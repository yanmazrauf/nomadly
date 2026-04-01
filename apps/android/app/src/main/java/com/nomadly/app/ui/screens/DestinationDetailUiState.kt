package com.nomadly.app.ui.screens

import com.nomadly.app.model.Destination

sealed interface DestinationDetailUiState {
    data object Loading : DestinationDetailUiState

    data class Success(
        val destination: Destination,
        val isSaved: Boolean
    ) : DestinationDetailUiState

    data class Error(val message: String) : DestinationDetailUiState
}
