package com.nandom.heroesandvillains.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "heroes")
data class SuperheroEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val intelligence: Int?,
    val strength: Int?,
    val speed: Int?,
    val durability: Int?,
    val power: Int?,
    val combat: Int?,
    val weaknesses: String
)

@Entity(tableName = "hero_sync_state")
data class HeroSyncStateEntity(
    @PrimaryKey val key: String = "default",
    val nextRemoteId: Int = 1,
    val reachedEnd: Boolean = false
)