package com.eagm.pokemonlist.core.network

sealed class NetworkError(val message: String) {
    data object NoInternet : NetworkError("No hay conexión a internet")
    data object Timeout : NetworkError("La solicitud tardó demasiado")
    data object NotFound : NetworkError("No se encontraron resultados")
    data object ServerError : NetworkError("Error del servidor")
    data object Unknown : NetworkError("Ocurrió un error inesperado")
}