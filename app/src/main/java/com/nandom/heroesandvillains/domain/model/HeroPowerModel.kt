package com.nandom.heroesandvillains.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class HeroPowerModel(
    val label: String,
    val value: Int?
)
