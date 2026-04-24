package com.nandom.heroesandvillains.domain.model

data class SuperheroModel(
    val id: Int = 0,
    val name: String = "",
    val imageUrl: String = "",
    val powers: List<HeroPowerModel> = listOf(),
    val weaknesses: List<String> = listOf()
)
