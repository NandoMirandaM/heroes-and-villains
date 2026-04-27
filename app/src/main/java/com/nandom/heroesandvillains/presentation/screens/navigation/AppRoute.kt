package com.nandom.heroesandvillains.presentation.screens.navigation

import androidx.navigation3.runtime.NavKey
import com.nandom.heroesandvillains.domain.model.SuperheroModel
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute : NavKey

@Serializable
data object HomeRoute : AppRoute

@Serializable
data class DetailRoute(
    val hero: SuperheroModel
) : AppRoute