package com.nandom.heroesandvillains.di

import androidx.room.Room
import com.nandom.heroesandvillains.data.db.SuperheroDatabase
import com.nandom.heroesandvillains.data.remote.KtorSuperheroApi
import com.nandom.heroesandvillains.data.remote.SuperheroApi
import com.nandom.heroesandvillains.data.repository.SuperheroRepositoryImp
import com.nandom.heroesandvillains.domain.repository.SuperheroRepository
import com.nandom.heroesandvillains.domain.usecase.LoadNextHeroPageUseCase
import com.nandom.heroesandvillains.domain.usecase.ObserveHeroesUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

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
    single {
        ObserveHeroesUseCase(get())
    }

    single {
        LoadNextHeroPageUseCase(get())
    }
}
