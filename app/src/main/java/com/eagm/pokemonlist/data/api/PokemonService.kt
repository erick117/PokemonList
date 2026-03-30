package com.eagm.pokemonlist.data.api

import com.eagm.pokemonlist.data.model.PokemonData
import com.eagm.pokemonlist.data.model.PokemonInitialData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonService @Inject constructor(
    private  val api: PokemonApiClient
) {

    suspend fun getPokemon(name : String) : PokemonData
    {
        return withContext(Dispatchers.IO) {
             api.getPokemonByName(name)
        }
    }

    suspend fun getPokemons() : List<PokemonInitialData>
    {
        return withContext(Dispatchers.IO){
            api.getPokemons().results
        }
    }

    suspend fun getPokemonsByType(type: String): List<PokemonInitialData> {
        return withContext(Dispatchers.IO) {
            api.getPokemonsByType(type).pokemon.map { it.pokemon }
        }
    }
}