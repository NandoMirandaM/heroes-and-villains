package com.nandom.heroesandvillains

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.nandom.heroesandvillains.domain.model.HeroPowerModel
import com.nandom.heroesandvillains.domain.model.SuperheroModel
import com.nandom.heroesandvillains.presentation.screens.detail.DetailScreen
import com.nandom.heroesandvillains.presentation.screens.detail.DetailUiState
import com.nandom.heroesandvillains.presentation.screens.home.HeroItem
import com.nandom.heroesandvillains.presentation.screens.home.HomeScreen
import com.nandom.heroesandvillains.presentation.screens.home.HomeUiState
import org.junit.Rule
import org.junit.Test

class HeroItemUiTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun muestraNombreDelHeroe() {
        val hero = SuperheroModel(
            id = 1,
            name = "Batman",
            imageUrl = "",
            powers = emptyList(),
            weaknesses = emptyList()
        )

        composeRule.setContent {
            MaterialTheme {
                HeroItem(
                    hero = hero,
                    onClick = {}
                )
            }
        }

        composeRule.onNodeWithText("Batman").assertIsDisplayed()
    }

    @Test
    fun muestraLoadingInicial() {
        composeRule.setContent {
            MaterialTheme {
                HomeScreen(
                    state = HomeUiState(isInitialLoading = true),
                    onIntent = {},
                    onHeroClick = {}
                )
            }
        }

        composeRule.onNodeWithTag("home_loading").assertIsDisplayed()
    }

    @Test
    fun detalleMuestraNombreYDebilidades() {
        val hero = SuperheroModel(
            id = 1,
            name = "Superman",
            imageUrl = "",
            powers = listOf(
                HeroPowerModel("Fuerza", 95)
            ),
            weaknesses = listOf("kryptonita")
        )

        composeRule.setContent {
            MaterialTheme {
                DetailScreen(
                    state = DetailUiState(hero = hero),
                    onBack = {}
                )
            }
        }

        composeRule.onNodeWithText("Superman").assertIsDisplayed()
        composeRule.onNodeWithText("Debilidades").assertIsDisplayed()
        composeRule.onNodeWithText("kryptonita").assertIsDisplayed()
    }
}
