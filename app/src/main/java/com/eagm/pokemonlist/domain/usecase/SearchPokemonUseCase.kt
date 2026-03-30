package com.eagm.pokemonlist.domain.usecase

import com.eagm.pokemonlist.data.model.PokemonData
import com.eagm.pokemonlist.data.repository.PokemonRepository
import javax.inject.Inject

class SearchPokemonUseCase @Inject constructor(
    private  val repository: PokemonRepository
) {
    suspend operator fun invoke(query: String) : Result<PokemonData> = repository.getPokemon(query)
}