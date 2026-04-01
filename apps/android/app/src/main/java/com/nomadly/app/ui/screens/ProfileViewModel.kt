package com.nomadly.app.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nomadly.app.data.di.RepositoryProvider
import com.nomadly.app.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            userRepository.getProfile()
                .onSuccess { profile -> _uiState.value = ProfileUiState.Success(profile) }
                .onFailure { e -> _uiState.value = ProfileUiState.Error(e.message ?: "Failed to load profile") }
        }
    }

    fun updateTravelStyles(styles: List<String>) {
        viewModelScope.launch {
            userRepository.updateTravelStyles(styles)
                .onSuccess { loadProfile() }
        }
    }

    fun retry() = loadProfile()

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                ProfileViewModel(RepositoryProvider.userRepository) as T
        }
    }
}
