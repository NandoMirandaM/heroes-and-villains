package com.nandom.heroesandvillains.presentation.screens.home

import com.nandom.heroesandvillains.domain.model.SuperheroModel

data class HomeUiState(
    val heroes: List<SuperheroModel> = emptyList(),
    val isInitialLoading: Boolean = false,
    val isAppending: Boolean = false,
    val reachedEnd: Boolean = false,
    val errorMessage: String? = null
)

sealed interface HomeIntent {
    data object LoadInitial : HomeIntent
    data object LoadNextPage : HomeIntent
    data object Retry : HomeIntent
}
