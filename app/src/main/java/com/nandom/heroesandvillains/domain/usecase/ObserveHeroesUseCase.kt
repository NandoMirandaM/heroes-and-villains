package com.nandom.heroesandvillains.domain.usecase

import com.nandom.heroesandvillains.domain.model.SuperheroModel
import com.nandom.heroesandvillains.domain.repository.SuperheroRepository
import kotlinx.coroutines.flow.Flow

class ObserveHeroesUseCase(
    private val repository: SuperheroRepository
) {
    operator fun invoke(limit: Int): Flow<List<SuperheroModel>> {
        return repository.observeHeroes(limit)
    }
}