package com.eagm.pokemonlist.domain.usecase

import com.eagm.pokemonlist.data.model.PokemonInitialData
import com.eagm.pokemonlist.data.repository.PokemonRepository
import javax.inject.Inject


class GetPokemonsUseCase @Inject constructor(
    private  val repository: PokemonRepository

) {


    suspend operator fun invoke() : Result<List<PokemonInitialData>> = repository.getPokemons()
}