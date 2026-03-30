package com.eagm.pokemonlist.data.api

import com.eagm.pokemonlist.data.model.PokemonData
import com.eagm.pokemonlist.data.model.PokemonListResponse
import com.eagm.pokemonlist.data.model.PokemonTypeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiClient {
    @GET("pokemon/{name}")
    suspend fun getPokemonByName(
        @Path("name") name: String
    ): PokemonData

    @GET("pokemon?limit=20")
    suspend fun getPokemons(): PokemonListResponse

    @GET("type/{type}")
    suspend fun getPokemonsByType(
        @Path("type") type: String
    ): PokemonTypeResponse
}