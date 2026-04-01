package com.nomadly.app.ui.screens

import com.nomadly.app.model.UserProfile

sealed interface ProfileUiState {
    data object Loading : ProfileUiState

    data class Success(
        val profile: UserProfile
    ) : ProfileUiState

    data class Error(val message: String) : ProfileUiState
}
