package com.nomadly.app.ui.screens

import com.nomadly.app.model.Board

sealed interface SavedBoardsUiState {
    data object Loading : SavedBoardsUiState

    data class Success(
        val boards: List<Board>
    ) : SavedBoardsUiState

    data class Error(val message: String) : SavedBoardsUiState
}
