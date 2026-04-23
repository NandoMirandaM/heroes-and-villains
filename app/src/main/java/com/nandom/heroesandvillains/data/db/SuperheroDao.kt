package com.nandom.heroesandvillains.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SuperheroDao {

    @Query("SELECT * FROM heroes ORDER BY id ASC LIMIT :limit")
    fun observeHeroes(limit: Int): Flow<List<SuperheroEntity>>

    @Query("SELECT * FROM heroes WHERE id = :id LIMIT 1")
    fun observeHero(id: Int): Flow<SuperheroEntity?>

    @Query("SELECT COUNT(*) FROM heroes")
    suspend fun heroCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHero(hero: SuperheroEntity)

    @Query("SELECT * FROM hero_sync_state WHERE `key` = :key LIMIT 1")
    suspend fun getSyncState(key: String = "default"): HeroSyncStateEntity?

    @Upsert
    suspend fun upsertSyncState(state: HeroSyncStateEntity)
}