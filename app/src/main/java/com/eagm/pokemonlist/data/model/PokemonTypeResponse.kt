package com.eagm.pokemonlist.data.model

import com.google.gson.annotations.SerializedName

data class PokemonTypeResponse(
    @SerializedName("pokemon")
    val pokemon: List<PokemonType>
)

