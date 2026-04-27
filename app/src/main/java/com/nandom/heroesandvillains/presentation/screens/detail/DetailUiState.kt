package com.nandom.heroesandvillains.presentation.screens.detail

import com.nandom.heroesandvillains.domain.model.SuperheroModel

data class DetailUiState(
    val hero: SuperheroModel = SuperheroModel()
)
