package com.nandom.heroesandvillains.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SuperheroModel(
    val id: Int = 0,
    val name: String = "",
    val imageUrl: String = "",
    val powers: List<HeroPowerModel> = listOf(),
    val weaknesses: List<String> = listOf()
)
