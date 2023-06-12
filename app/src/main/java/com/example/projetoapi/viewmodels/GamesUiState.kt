package com.example.projetoapi.viewmodels

import com.example.projetoapi.models.Game

sealed interface GamesUiState {

    object Loading : GamesUiState

    data class Success(val games: List<Game>) : GamesUiState

    object Error: GamesUiState
}