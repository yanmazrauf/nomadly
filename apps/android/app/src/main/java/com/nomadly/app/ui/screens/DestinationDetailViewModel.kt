package com.nomadly.app.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nomadly.app.data.di.RepositoryProvider
import com.nomadly.app.data.repository.DestinationRepository
import com.nomadly.app.model.Destination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DestinationDetailViewModel(
    private val destination: Destination,
    private val destinationRepository: DestinationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DestinationDetailUiState>(
        DestinationDetailUiState.Success(destination, destination.isSaved)
    )
    val uiState: StateFlow<DestinationDetailUiState> = _uiState.asStateFlow()

    fun toggleSaved() {
        val current = _uiState.value as? DestinationDetailUiState.Success ?: return
        viewModelScope.launch {
            if (current.isSaved) {
                destinationRepository.unsaveDestination(destination.id)
                    .onSuccess { _uiState.value = current.copy(isSaved = false) }
                    .onFailure { /* surface error if needed */ }
            } else {
                destinationRepository.saveDestination(destination.id)
                    .onSuccess { _uiState.value = current.copy(isSaved = true) }
                    .onFailure { /* surface error if needed */ }
            }
        }
    }

    companion object {
        fun provideFactory(
            destination: Destination,
            repo: DestinationRepository = RepositoryProvider.destinationRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                DestinationDetailViewModel(destination, repo) as T
        }
    }
}
