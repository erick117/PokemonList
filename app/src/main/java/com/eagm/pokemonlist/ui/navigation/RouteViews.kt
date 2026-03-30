package com.eagm.pokemonlist.ui.navigation

object RouteViews {
    const val LIST = "list"
    const val LIST_WITH_TYPE = "list?type={type}"
    const val DETAIL = "detail"
    const val DETAIL_WITH_ARG = "detail/{pokemonName}"

    fun detailRoute(pokemonName: String): String {
        return "$DETAIL/$pokemonName"
    }
    fun listRouteWithType(type: String): String = "list?type=$type"
}