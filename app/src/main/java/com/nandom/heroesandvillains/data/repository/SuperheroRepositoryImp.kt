package com.nandom.heroesandvillains.data.repository

import com.nandom.heroesandvillains.data.db.HeroSyncStateEntity
import com.nandom.heroesandvillains.data.db.SuperheroDao
import com.nandom.heroesandvillains.data.dto.toDomain
import com.nandom.heroesandvillains.data.dto.toEntity
import com.nandom.heroesandvillains.data.remote.SuperheroApi
import com.nandom.heroesandvillains.domain.model.SuperheroModel
import com.nandom.heroesandvillains.domain.repository.SuperheroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SuperheroRepositoryImp(
    private val dao: SuperheroDao,
    private val api: SuperheroApi
) : SuperheroRepository {

    override fun observeHeroes(limit: Int): Flow<List<SuperheroModel>> {
        return dao.observeHeroes(limit = limit).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun observeHero(id: Int): Flow<SuperheroModel?> {
        return dao.observeHero(id).map { entity ->
            entity?.toDomain()
        }
    }

    override suspend fun loadHeroesUntil(limit: Int): Boolean {
        val localCount = dao.heroCount()
        val currentState = dao.getSyncState() ?: HeroSyncStateEntity()

        if (currentState.reachedEnd || localCount >= limit) {
            return currentState.reachedEnd
        }

        var count = localCount
        var nextId = currentState.nextRemoteId
        var reachedEnd = currentState.reachedEnd

        while (count < limit && !reachedEnd) {
            try {
                val dto = api.getHero(nextId)

                if (dto.response == "false") {
                    reachedEnd = true
                } else {
                    val entity = dto.toEntity()
                    if (entity != null) {
                        dao.insertHero(entity)
                        count++
                    }
                    nextId++
                }
            } catch (e: Exception) {
                throw e
            }
        }

        dao.upsertSyncState(
            HeroSyncStateEntity(
                key = "default",
                nextRemoteId = nextId,
                reachedEnd = reachedEnd
            )
        )

        return reachedEnd
    }

}