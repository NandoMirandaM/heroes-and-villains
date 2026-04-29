package com.nandom.heroesandvillains

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.nandom.heroesandvillains.data.db.SuperheroDatabase
import com.nandom.heroesandvillains.data.dto.SuperheroDto
import com.nandom.heroesandvillains.data.dto.HeroImageDto
import com.nandom.heroesandvillains.data.dto.PowerstatsDto
import com.nandom.heroesandvillains.data.remote.SuperheroApi
import com.nandom.heroesandvillains.data.repository.SuperheroRepositoryImp
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SuperheroRepositoryIntegrationTest {

    private lateinit var database: SuperheroDatabase
    private lateinit var repository: SuperheroRepositoryImp

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            SuperheroDatabase::class.java
        ).allowMainThreadQueries().build()

        repository = SuperheroRepositoryImp(
            dao = database.superheroDao(),
            api = FakeApi(maxId = 3)
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun cargaHeroesDesdeApiYLosGuardaEnRoom() = runTest {
        val reachedEnd = repository.loadHeroesUntil(5)

        assertTrue(reachedEnd)

        repository.observeHeroes(40).test {
            val heroes = awaitItem()
            assertEquals(3, heroes.size)
            assertEquals("Hero 1", heroes[0].name)
            assertEquals("Hero 2", heroes[1].name)
            assertEquals("Hero 3", heroes[2].name)
            cancelAndIgnoreRemainingEvents()
        }
    }
}


class FakeApi(
    private val maxId: Int
) : SuperheroApi {

    override suspend fun getHero(id: Int): SuperheroDto {
        if (id > maxId) {
            return SuperheroDto(
                response = "false",
                error = "invalid id"
            )
        }

        return SuperheroDto(
            response = "success",
            id = id.toString(),
            name = "Hero $id",
            image = HeroImageDto("https://example.com/$id.png"),
            powerstats = PowerstatsDto(
                intelligence = "50",
                strength = "50",
                speed = "50",
                durability = "50",
                power = "50",
                combat = "50"
            )
        )
    }
}
