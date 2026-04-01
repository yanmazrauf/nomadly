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

class HomeViewModel(
    private val destinationRepository: DestinationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var allDestinations: List<Destination> = emptyList()
    private var selectedCategory: String = "All"

    init {
        loadDestinations()
    }

    private fun loadDestinations() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            destinationRepository.getDestinations()
                .onSuccess { destinations ->
                    allDestinations = destinations
                    pushFilteredState()
                }
                .onFailure { e ->
                    _uiState.value = HomeUiState.Error(e.message ?: "Failed to load destinations")
                }
        }
    }

    fun selectCategory(category: String) {
        if (selectedCategory == category) return
        selectedCategory = category
        pushFilteredState()
    }

    fun onRemainingChanged(count: Int) {
        val current = _uiState.value as? HomeUiState.Success ?: return
        _uiState.value = current.copy(remainingCount = count)
    }

    fun onSwipedRight(destination: Destination) {
        viewModelScope.launch {
            destinationRepository.saveDestination(destination.id)
        }
    }

    fun onSwipedLeft(destination: Destination) {
        // intentional no-op — extend here for discard analytics / undo support
    }

    fun retry() = loadDestinations()

    private fun pushFilteredState() {
        val filtered = if (selectedCategory == "All") allDestinations
                       else allDestinations.filter { dest ->
                           dest.tags.any { it.contains(selectedCategory, ignoreCase = true) }
                       }
        _uiState.value = HomeUiState.Success(
            destinations        = allDestinations,
            filteredDestinations = filtered,
            selectedCategory    = selectedCategory,
            remainingCount      = filtered.size
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                HomeViewModel(RepositoryProvider.destinationRepository) as T
        }
    }
}
