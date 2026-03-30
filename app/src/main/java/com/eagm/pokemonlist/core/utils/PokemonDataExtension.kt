package com.eagm.pokemonlist.core.utils

import com.eagm.pokemonlist.data.model.PokemonData
import com.eagm.pokemonlist.data.model.PokemonInitialData

fun PokemonInitialData.getId(): String {
    return url.trimEnd('/').split("/").last()
}

fun PokemonInitialData.getImageUrl(): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${getId()}.png"
}

fun PokemonData.toPokemonInitialData(): PokemonInitialData {
    return PokemonInitialData(
        name = name,
        url = "https://pokeapi.co/api/v2/pokemon/$id/"
    )
}