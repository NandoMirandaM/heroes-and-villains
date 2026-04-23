package com.nandom.heroesandvillains.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SuperheroEntity::class, HeroSyncStateEntity::class],
    version = 1,
    exportSchema = true
)
abstract class SuperheroDatabase : RoomDatabase() {
    abstract fun superheroDao(): SuperheroDao
}