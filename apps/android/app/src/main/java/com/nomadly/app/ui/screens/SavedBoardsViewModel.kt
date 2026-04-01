package com.nomadly.app.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nomadly.app.data.di.RepositoryProvider
import com.nomadly.app.data.repository.BoardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SavedBoardsViewModel(
    private val boardRepository: BoardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SavedBoardsUiState>(SavedBoardsUiState.Loading)
    val uiState: StateFlow<SavedBoardsUiState> = _uiState.asStateFlow()

    init {
        loadBoards()
    }

    private fun loadBoards() {
        viewModelScope.launch {
            _uiState.value = SavedBoardsUiState.Loading
            boardRepository.getBoards()
                .onSuccess { boards -> _uiState.value = SavedBoardsUiState.Success(boards) }
                .onFailure { e -> _uiState.value = SavedBoardsUiState.Error(e.message ?: "Failed to load boards") }
        }
    }

    fun createBoard(title: String) {
        viewModelScope.launch {
            boardRepository.createBoard(title)
                .onSuccess {
                    // Refresh the list after creation
                    loadBoards()
                }
                .onFailure { e ->
                    _uiState.value = SavedBoardsUiState.Error(e.message ?: "Failed to create board")
                }
        }
    }

    fun retry() = loadBoards()

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                SavedBoardsViewModel(RepositoryProvider.boardRepository) as T
        }
    }
}
