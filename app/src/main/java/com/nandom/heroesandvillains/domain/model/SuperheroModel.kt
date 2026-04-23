package com.nandom.heroesandvillains.domain.model

data class SuperheroModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val powers: List<HeroPowerModel>,
    val weaknesses: List<String>
)
