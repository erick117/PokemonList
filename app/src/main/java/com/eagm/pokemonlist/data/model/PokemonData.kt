package com.eagm.pokemonlist.data.model

import com.google.gson.annotations.SerializedName

data class PokemonData(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("sprites")
    val sprites: SpritesDto,

    @SerializedName("stats")
    val stats: List<StatDto>,

    @SerializedName("types")
    val types: List<TypeSlotDto>
)

data class SpritesDto(
    @SerializedName("front_default")
    val frontDefault: String?,

    @SerializedName("front_shiny")
    val frontShiny: String?
)

data class StatDto(
    @SerializedName("base_stat")
    val baseStat: Int,

    @SerializedName("effort")
    val effort: Int,

    @SerializedName("stat")
    val stat: ApiReferenceDto
)

data class TypeSlotDto(
    @SerializedName("slot")
    val slot: Int,

    @SerializedName("type")
    val type: ApiReferenceDto
)

data class ApiReferenceDto(
    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
)