package com.nandom.heroesandvillains.presentation.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.nandom.heroesandvillains.presentation.screens.detail.DetailScreen
import com.nandom.heroesandvillains.presentation.screens.detail.DetailViewModel
import com.nandom.heroesandvillains.presentation.screens.home.HomeScreen
import com.nandom.heroesandvillains.presentation.screens.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavRoot() {
    val backStack = rememberNavBackStack(HomeRoute)

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
            }
        },
        entryProvider = { route ->
            when (route) {
                HomeRoute -> NavEntry(route) {
                    val viewModel: HomeViewModel = koinViewModel()
                    val state by viewModel.uiState.collectAsStateWithLifecycle()

                    HomeScreen(
                        state = state,
                        onIntent = viewModel::onIntent,
                        onHeroClick = { hero ->
                            backStack.add(DetailRoute(hero))
                        }
                    )
                }

                is DetailRoute -> NavEntry(route) {
                    val detailViewModel: DetailViewModel = koinViewModel()
                    val state by detailViewModel.uiState.collectAsStateWithLifecycle()

                    LaunchedEffect(route.hero) {
                        detailViewModel.setHero(route.hero)
                    }

                    DetailScreen(
                        state = state,
                        onBack = {
                            if (backStack.size > 1) {
                                backStack.removeLastOrNull()
                            }
                        }
                    )
                }

                else -> error("Ruta no soportada: $route")
            }
        }
    )
}