package com.eagm.pokemonlist.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eagm.pokemonlist.core.network.NetworkError
import com.eagm.pokemonlist.core.network.toNetworkError
import com.eagm.pokemonlist.core.utils.toPokemonInitialData
import com.eagm.pokemonlist.data.model.PokemonInitialData
import com.eagm.pokemonlist.domain.usecase.GetPokemonsByTypeUseCase
import com.eagm.pokemonlist.domain.usecase.GetPokemonsUseCase
import com.eagm.pokemonlist.domain.usecase.SearchPokemonUseCase
import com.eagm.pokemonlist.ui.state.SearchMode
import com.eagm.pokemonlist.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonsUsecase: GetPokemonsUseCase,
    private val searchPokemonUseCase: SearchPokemonUseCase,
    private val getPokemonsByTypeUseCase: GetPokemonsByTypeUseCase
) : ViewModel() {

    private var initialPokemons: List<PokemonInitialData> = emptyList()

    var searchQuery by mutableStateOf("")
        private set

    var searchMode by mutableStateOf(SearchMode.NAME)
        private set

    var state by mutableStateOf<UiState<List<PokemonInitialData>>>(UiState.Loading)
        private set




    init {
        loadPokemons()
    }

    private fun loadPokemons() {
        viewModelScope.launch {
            state = UiState.Loading
                val result = getPokemonsUsecase()

                result.onSuccess{ pokemons ->
                    initialPokemons = pokemons
                    state = if (pokemons.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(pokemons)
                    }
                } .onFailure { throwable ->
                        val error = throwable.toNetworkError()
                        state = UiState.Error(error.message)
                    }
        }
    }


    fun onSearchQueryChange(query: String) {
        searchQuery = query
    }

    fun searchPokemon() {
        val query = searchQuery.trim().lowercase()

        if (query.isBlank()) {
            showInitialPokemons()
            return
        }

        viewModelScope.launch {
            state = UiState.Loading

            when (searchMode) {
                SearchMode.NAME -> {
                    val result = searchPokemonUseCase(query)

                    result
                        .onSuccess { pokemon ->
                            state = UiState.Success(listOf(pokemon.toPokemonInitialData()))
                        }
                        .onFailure { throwable ->
                            val error = throwable.toNetworkError()
                            state = if (error is NetworkError.NotFound) {
                                UiState.Empty
                            } else {
                                UiState.Error(error.message)
                            }
                        }
                }

                SearchMode.NUMBER -> {
                    if (query.toIntOrNull() == null) {
                        state = UiState.Error("Ingresa un número válido")
                        return@launch
                    }

                    val result = searchPokemonUseCase(query)

                    result
                        .onSuccess { pokemon ->
                            state = UiState.Success(listOf(pokemon.toPokemonInitialData()))
                        }
                        .onFailure { throwable ->
                            val error = throwable.toNetworkError()
                            state = if (error is NetworkError.NotFound) {
                                UiState.Empty
                            } else {
                                UiState.Error(error.message)
                            }
                        }
                }

                SearchMode.TYPE -> {
                    val result = getPokemonsByTypeUseCase(query)

                    result
                        .onSuccess { pokemons ->
                            state = if (pokemons.isEmpty()) {
                                UiState.Empty
                            } else {
                                UiState.Success(pokemons)
                            }
                        }
                        .onFailure { throwable ->
                            val error = throwable.toNetworkError()
                            state = if (error is NetworkError.NotFound) {
                                UiState.Empty
                            } else {
                                UiState.Error(error.message)
                            }
                        }
                }
            }
        }

    }

    fun retry() {
        if (searchQuery.isBlank()) {
            loadPokemons()
        } else {
            searchPokemon()
        }
    }

    fun clearSearch() {
        searchQuery = ""
        searchMode = SearchMode.NAME
        showInitialPokemons()
    }

    fun searchByType(type: String) {
        viewModelScope.launch {
            state = UiState.Loading

            val result = getPokemonsByTypeUseCase(type)

            result
                .onSuccess { pokemons ->
                    state = if (pokemons.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(pokemons)
                    }
                }
                .onFailure { throwable ->
                    val error = throwable.toNetworkError()

                    state = if (error is NetworkError.NotFound) {
                        UiState.Empty
                    } else {
                        UiState.Error(error.message)
                    }
                }
        }
    }

    fun loadInitialPokemonsIfNeeded() {
        if (initialPokemons.isNotEmpty()) {
            state = UiState.Success(initialPokemons)
        } else {
            loadPokemons()
        }
    }

    fun showInitialPokemons() {
        if (initialPokemons.isEmpty()) {
            loadInitialPokemonsIfNeeded()
        } else {
            state = UiState.Success(initialPokemons)
        }
    }

    fun onSearchModeChange(mode: SearchMode) {
        searchMode = mode
    }

    fun applyTypeFilter(type: String) {
        searchMode = SearchMode.TYPE
        searchQuery = type
        searchByType(type)
    }
}