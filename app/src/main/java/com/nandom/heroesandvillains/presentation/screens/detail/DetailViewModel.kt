package com.nandom.heroesandvillains.presentation.screens.detail

import androidx.lifecycle.ViewModel
import com.nandom.heroesandvillains.domain.model.SuperheroModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    fun setHero(hero: SuperheroModel) {
        _uiState.value = DetailUiState(hero = hero)
    }
}