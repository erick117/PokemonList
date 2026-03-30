package com.eagm.pokemonlist.domain.usecase

import com.eagm.pokemonlist.data.model.PokemonInitialData
import com.eagm.pokemonlist.data.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonsByTypeUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(type: String): Result<List<PokemonInitialData>> {
        return repository.getPokemonsByType(type)
    }
}