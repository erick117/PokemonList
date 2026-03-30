package com.eagm.pokemonlist.data.model

import com.google.gson.annotations.SerializedName

data class PokemonType(
    @SerializedName("pokemon")
    val pokemon: PokemonInitialData,

    @SerializedName("slot")
    val slot: Int
)