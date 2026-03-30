package com.eagm.pokemonlist.data.repository

import com.eagm.pokemonlist.data.api.PokemonService
import com.eagm.pokemonlist.data.model.PokemonData
import com.eagm.pokemonlist.data.model.PokemonInitialData
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val api : PokemonService
) {



    suspend fun getPokemon(name : String): Result<PokemonData>{
        return runCatching {
             api.getPokemon(name)
        }
    }

    suspend fun getPokemons(): Result<List<PokemonInitialData>>{
        return  try {
            var response = api.getPokemons()
            Result.success(response)
        }
        catch (e : Exception){
            Result.failure(e)
        }

    }

    suspend fun getPokemonsByType(type: String): Result<List<PokemonInitialData>> {
        return runCatching { api.getPokemonsByType(type) }
    }
}