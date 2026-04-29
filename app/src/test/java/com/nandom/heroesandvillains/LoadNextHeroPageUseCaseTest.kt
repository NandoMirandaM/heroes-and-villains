package com.nandom.heroesandvillains

import com.nandom.heroesandvillains.domain.model.SuperheroModel
import com.nandom.heroesandvillains.domain.repository.SuperheroRepository
import com.nandom.heroesandvillains.domain.usecase.LoadNextHeroPageUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LoadNextHeroPageUseCaseTest {

    @Test
    fun delegaElLimiteAlRepository() = runTest {
        val fakeRepository = FakeRepository()
        val useCase = LoadNextHeroPageUseCase(fakeRepository)

        val reachedEnd = useCase(40)

        assertFalse(reachedEnd)
        assertEquals(40, fakeRepository.lastRequestedLimit)
    }

    private class FakeRepository : SuperheroRepository {
        var lastRequestedLimit = -1

        override fun observeHeroes(limit: Int): Flow<List<SuperheroModel>> = flowOf(emptyList())

        override suspend fun loadHeroesUntil(limit: Int): Boolean {
            lastRequestedLimit = limit
            return false
        }
    }
}