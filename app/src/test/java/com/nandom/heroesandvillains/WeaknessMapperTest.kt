package com.nandom.heroesandvillains

import com.nandom.heroesandvillains.data.dto.PowerstatsDto
import com.nandom.heroesandvillains.data.dto.toWeaknesses
import junit.framework.TestCase.assertEquals
import org.junit.Test

class WeaknessMapperTest {

    @Test
    fun statsBajasSeConviertenEnDebilidades() {
        val stats = PowerstatsDto(
            intelligence = "80",
            strength = "20",
            speed = "null",
            durability = "75",
            power = "39",
            combat = "90"
        )

        val weaknesses = stats.toWeaknesses()

        assertEquals(
            listOf(
                "fuerza baja (20/100)",
                "velocidad desconocida",
                "poder baja (39/100)"
            ),
            weaknesses
        )
    }

    @Test
    fun heroeFuerteNoTieneDebilidadesClaras() {
        val stats = PowerstatsDto(
            intelligence = "90",
            strength = "90",
            speed = "90",
            durability = "90",
            power = "90",
            combat = "90"
        )

        val weaknesses = stats.toWeaknesses()

        assertEquals(
            listOf("No se detectaron debilidades claras en sus estadísticas"),
            weaknesses
        )
    }
}
