package com.nandom.heroesandvillains.data.remote

import com.nandom.heroesandvillains.data.dto.SuperheroDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface SuperheroApi {
    suspend fun getHero(id: Int): SuperheroDto
}

class KtorSuperheroApi(
    private val client: HttpClient
) : SuperheroApi {

    override suspend fun getHero(id: Int): SuperheroDto {
        return client.get("$BASE_URL/$id").body()
    }

    companion object {
        private const val BASE_URL =
            "https://superheroapi.com/api/10220161311159074"
    }
}

val client = HttpClient(Android) {
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true // Se pone por si el servicio regresa datos adicionales no truene la app
                isLenient = true
            }
        )
    }
}