package com.example.mymovieapp.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.domain.usecase.GetFavoritesUseCase
import com.example.mymovieapp.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState(isLoading = true))
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        // Observe favorites from Room
        getFavoritesUseCase()
            .onEach { list ->
                _uiState.value = FavoritesUiState(
                    isLoading = false,
                    favorites = list
                )
            }
            .launchIn(viewModelScope)
    }

    fun toggleFavorite(movieId: String) {
        viewModelScope.launch {
            // Show loading immediately
            _uiState.value = _uiState.value.copy(isLoading = true)

            toggleFavoriteUseCase(movieId)

            // Keep spinner visible a little while (smooth UX)
            delay(400)

            // Room flow will emit the updated list and hide loading
            // But in case Room is slow, ensure we hide loading anyway
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}
