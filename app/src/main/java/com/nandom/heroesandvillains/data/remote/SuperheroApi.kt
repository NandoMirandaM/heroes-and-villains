package com.nandom.heroesandvillains.data.remote

import com.nandom.heroesandvillains.data.dto.SuperheroDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

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