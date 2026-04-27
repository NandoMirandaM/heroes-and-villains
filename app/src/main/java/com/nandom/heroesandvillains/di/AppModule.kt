package com.nandom.heroesandvillains.di

import androidx.room.Room
import com.nandom.heroesandvillains.data.db.SuperheroDatabase
import com.nandom.heroesandvillains.data.remote.KtorSuperheroApi
import com.nandom.heroesandvillains.data.remote.SuperheroApi
import com.nandom.heroesandvillains.data.repository.SuperheroRepositoryImp
import com.nandom.heroesandvillains.domain.repository.SuperheroRepository
import com.nandom.heroesandvillains.domain.usecase.LoadNextHeroPageUseCase
import com.nandom.heroesandvillains.domain.usecase.ObserveHeroesUseCase
import com.nandom.heroesandvillains.presentation.screens.detail.DetailViewModel
import com.nandom.heroesandvillains.presentation.screens.home.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

val appModule = module {

    single<CoroutineScope> {
        CoroutineScope(SupervisorJob() + Dispatchers.Main)
    }

    // Database
    single {
        Room.databaseBuilder(
            androidContext(),
            SuperheroDatabase::class.java,
            "superheroes.db"
        ).build()
    }

    // Dao
    single {
        get<SuperheroDatabase>().superheroDao()
    }

    // httpClient
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true // Se pone por si el servicio regresa datos adicionales no truene la app
                        isLenient = true
                    }
                )
            }
        }
    }

    // Api
    single<SuperheroApi> {
        KtorSuperheroApi(get())
    }

    // Repository
    single<SuperheroRepository> {
        SuperheroRepositoryImp(
            dao = get(),
            api = get()
        )
    }

    // Use Case
    factory { ObserveHeroesUseCase(get()) }
    factory { LoadNextHeroPageUseCase(get()) }

    //viewModels
    viewModel {
        HomeViewModel(
            observeHeroes = get(),
            loadNextHeroPage = get()
        )
    }

    viewModel {
        DetailViewModel()
    }
}
