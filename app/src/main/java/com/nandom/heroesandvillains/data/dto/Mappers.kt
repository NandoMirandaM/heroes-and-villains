package com.nandom.heroesandvillains.data.dto

import com.nandom.heroesandvillains.data.db.SuperheroEntity
import com.nandom.heroesandvillains.domain.model.HeroPowerModel
import com.nandom.heroesandvillains.domain.model.SuperheroModel

fun SuperheroDto.toEntity(): SuperheroEntity? {
    val numericId = id?.toIntOrNull() ?: return null

    return SuperheroEntity(
        id = numericId,
        name = name.orEmpty().ifBlank { "Hero #$numericId" },
        imageUrl = image?.url.orEmpty(),
        intelligence = powerstats?.intelligence.toStat(),
        strength = powerstats?.strength.toStat(),
        speed = powerstats?.speed.toStat(),
        durability = powerstats?.durability.toStat(),
        power = powerstats?.power.toStat(),
        combat = powerstats?.combat.toStat(),
        weaknesses = powerstats.toWeaknesses().joinToString("|")
    )
}

private fun String?.toStat(): Int? {
    return this
        ?.takeUnless { it.equals("null", ignoreCase = true) }
        ?.toIntOrNull()
}

fun PowerstatsDto?.toWeaknesses(): List<String> {
    if (this == null) return listOf("Sin datos suficientes")

    val stats = listOf(
        "inteligencia" to intelligence.toStat(),
        "fuerza" to strength.toStat(),
        "velocidad" to speed.toStat(),
        "resistencia" to durability.toStat(),
        "poder" to power.toStat(),
        "combate" to combat.toStat()
    )

    val lowStats = stats
        .filter { (_, value) -> value == null || value < 40 }
        .map { (name, value) ->
            if (value == null) "$name desconocida"
            else "$name baja ($value/100)"
        }

    return lowStats.ifEmpty {
        listOf("No se detectaron debilidades claras en sus estadísticas")
    }
}


fun SuperheroEntity.toDomain(): SuperheroModel {
    return SuperheroModel(
        id = id,
        name = name,
        imageUrl = imageUrl,
        powers = listOf(
            HeroPowerModel("Inteligencia", intelligence),
            HeroPowerModel("Fuerza", strength),
            HeroPowerModel("Velocidad", speed),
            HeroPowerModel("Resistencia", durability),
            HeroPowerModel("Poder", power),
            HeroPowerModel("Combate", combat)
        ),
        weaknesses = weaknesses.split("|").filter { it.isNotBlank() }
    )
}