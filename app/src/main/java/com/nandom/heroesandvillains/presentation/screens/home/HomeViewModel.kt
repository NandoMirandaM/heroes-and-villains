package com.nandom.heroesandvillains.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandom.heroesandvillains.domain.usecase.LoadNextHeroPageUseCase
import com.nandom.heroesandvillains.domain.usecase.ObserveHeroesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val observeHeroes: ObserveHeroesUseCase,
    private val loadNextHeroPage: LoadNextHeroPageUseCase
) : ViewModel() {
    private val currentLimit = MutableStateFlow(PAGE_SIZE)
    private val internalState = MutableStateFlow(
        HomeUiState(isInitialLoading = true)
    )

    val uiState: StateFlow<HomeUiState> = currentLimit
        .flatMapLatest { limit -> observeHeroes(limit) }
        .combine(internalState) { heroes, state ->
            state.copy(
                heroes = heroes,
                isInitialLoading = state.isInitialLoading && heroes.isEmpty()
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState(isInitialLoading = true)
        )

    init {
        onIntent(HomeIntent.LoadInitial)
    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.LoadInitial -> loadInitial()
            HomeIntent.LoadNextPage -> loadNextPage()
            HomeIntent.Retry -> retry()
        }
    }

    private fun loadInitial() {
        viewModelScope.launch {
            internalState.update {
                it.copy(
                    isInitialLoading = true,
                    errorMessage = null
                )
            }

            try {
                val reachedEnd = loadNextHeroPage(PAGE_SIZE)
                internalState.update {
                    it.copy(
                        isInitialLoading = false,
                        reachedEnd = reachedEnd
                    )
                }
            } catch (e: Exception) {
                internalState.update {
                    it.copy(
                        isInitialLoading = false,
                        errorMessage = e.message ?: "Ocurrió un error"
                    )
                }
            }
        }
    }

    private fun loadNextPage() {
        if (internalState.value.isAppending || internalState.value.reachedEnd) return

        viewModelScope.launch {
            internalState.update {
                it.copy(
                    isAppending = true,
                    errorMessage = null
                )
            }

            val newLimit = currentLimit.value + PAGE_SIZE

            try {
                val reachedEnd = loadNextHeroPage(newLimit)
                currentLimit.value = newLimit

                internalState.update {
                    it.copy(
                        isAppending = false,
                        reachedEnd = reachedEnd
                    )
                }
            } catch (e: Exception) {
                internalState.update {
                    it.copy(
                        isAppending = false,
                        errorMessage = e.message ?: "No se pudo cargar más héroes"
                    )
                }
            }
        }
    }

    private fun retry() {
        if (uiState.value.heroes.isEmpty()) {
            loadInitial()
        } else {
            loadNextPage()
        }
    }

    companion object {
        private const val PAGE_SIZE = 40
    }
}