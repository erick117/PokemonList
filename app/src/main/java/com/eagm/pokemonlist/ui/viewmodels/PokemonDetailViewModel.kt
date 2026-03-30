package com.eagm.pokemonlist.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eagm.pokemonlist.core.network.toNetworkError
import com.eagm.pokemonlist.data.model.PokemonData
import com.eagm.pokemonlist.domain.usecase.SearchPokemonUseCase
import com.eagm.pokemonlist.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val searchPokemonUseCase: SearchPokemonUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf<UiState<PokemonData>>(UiState.Loading)
        private set

    private val pokemonName: String = savedStateHandle["pokemonName"] ?: ""

    init {
        loadPokemonDetail()
    }

    private fun loadPokemonDetail() {
        viewModelScope.launch {
            state = UiState.Loading

            val result = searchPokemonUseCase(pokemonName)

            result
                .onSuccess { pokemon ->
                    state = UiState.Success(pokemon)
                }
                .onFailure { throwable ->
                    val error = throwable.toNetworkError()
                    state = if (error is com.eagm.pokemonlist.core.network.NetworkError.NotFound) {
                        UiState.Empty
                    } else {
                        UiState.Error(error.message)
                    }
                }
        }
    }

    fun retry() {
        loadPokemonDetail()
    }
}