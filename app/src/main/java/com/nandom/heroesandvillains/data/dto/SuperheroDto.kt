package com.nandom.heroesandvillains.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SuperheroDto(
    val response: String? = null,
    val id: String? = null,
    val name: String? = null,
    val image: HeroImageDto? = null,
    val powerstats: PowerstatsDto? = null,
    val error: String? = null
)

@Serializable
data class HeroImageDto(
    val url: String? = null
)

@Serializable
data class PowerstatsDto(
    val intelligence: String? = null,
    val strength: String? = null,
    val speed: String? = null,
    val durability: String? = null,
    val power: String? = null,
    val combat: String? = null
)