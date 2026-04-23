package com.nandom.heroesandvillains.domain.repository

import com.nandom.heroesandvillains.domain.model.SuperheroModel
import kotlinx.coroutines.flow.Flow

interface SuperheroRepository {
    fun observeHeroes(limit: Int): Flow<List<SuperheroModel>>
    fun observeHero(id: Int): Flow<SuperheroModel?>
    suspend fun loadHeroesUntil(limit: Int): Boolean
}