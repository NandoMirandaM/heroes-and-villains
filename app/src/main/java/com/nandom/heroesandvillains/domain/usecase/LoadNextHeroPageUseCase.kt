package com.nandom.heroesandvillains.domain.usecase

import com.nandom.heroesandvillains.domain.repository.SuperheroRepository

class LoadNextHeroPageUseCase(
    private val repository: SuperheroRepository
) {
    suspend operator fun invoke(limit: Int): Boolean {
        return repository.loadHeroesUntil(limit)
    }
}