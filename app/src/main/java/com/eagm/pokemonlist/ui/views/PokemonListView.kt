package com.eagm.pokemonlist.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.eagm.pokemonlist.core.utils.getImageUrl
import com.eagm.pokemonlist.data.model.PokemonInitialData
import com.eagm.pokemonlist.ui.state.SearchMode
import com.eagm.pokemonlist.ui.state.UiState
import com.eagm.pokemonlist.ui.viewmodels.PokemonListViewModel

@Composable
fun PokemonListView(viewModel: PokemonListViewModel,onPokemonClick: (String) -> Unit, modifier: Modifier = Modifier) {

    val state = viewModel.state

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        PokemonSearchSection(viewModel)

        Spacer(modifier = Modifier.size(16.dp))

        when (state) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            }

            is UiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = state.message)
                        Spacer(modifier = Modifier.size(12.dp))
                        Button(onClick = { viewModel.retry() }) {
                            Text("Reintentar")
                        }
                    }
                }
            }

            is UiState.Empty -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No se encontraron resultados")
                }
            }

            is UiState.Success -> {
                LazyColumn() {
                    items(
                        items = state.data,
                        key = { pokemon -> pokemon.name }
                    ) { pokemon ->
                        Column {
                            PokemonList(
                                pokemon = pokemon,
                                onClick = { onPokemonClick(pokemon.name) }
                            )

                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonList(pokemon: PokemonInitialData, onClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(72.dp),
            contentAlignment = Alignment.Center
        ) {
            SubcomposeAsyncImage(
                model = pokemon.getImageUrl(),
                contentDescription = "Sprite",
                modifier = Modifier.size(120.dp)
            ) {

                when (painter.state) {

                    is coil.compose.AsyncImagePainter.State.Loading -> {
                        CircularProgressIndicator()
                    }

                    is coil.compose.AsyncImagePainter.State.Error -> {
                        Text("Error")
                    }

                    else -> {
                        SubcomposeAsyncImageContent()
                    }
                }
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(text = pokemon.name.replaceFirstChar { it.uppercase()},
            fontSize = 18.sp)
    }

}



@Composable
fun SearchModeSelector(
    selectedMode: SearchMode,
    onModeSelected: (SearchMode) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SearchModeButton(
            text = "Nombre",
            isSelected = selectedMode == SearchMode.NAME,
            onClick = { onModeSelected(SearchMode.NAME) }
        )

        SearchModeButton(
            text = "Número",
            isSelected = selectedMode == SearchMode.NUMBER,
            onClick = { onModeSelected(SearchMode.NUMBER) }
        )

        SearchModeButton(
            text = "Tipo",
            isSelected = selectedMode == SearchMode.TYPE,
            onClick = { onModeSelected(SearchMode.TYPE) }
        )
    }
}

@Composable
fun SearchModeButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(onClick = onClick,
        colors = buttonColors(
            containerColor = if (isSelected) {
                colorScheme.primary
            } else {
                colorScheme.surfaceVariant
            },
            contentColor = if (isSelected) {
                colorScheme.onPrimary
            } else {
                colorScheme.onSurfaceVariant
            }
        )) {
        Text(text)
    }
}
@Composable
fun PokemonSearchSection(viewModel: PokemonListViewModel) {
    Column {
        SearchModeSelector(
            selectedMode = viewModel.searchMode,
            onModeSelected = viewModel::onSearchModeChange
        )

        Spacer(modifier = Modifier.size(12.dp))

        OutlinedTextField(
            value = viewModel.searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    when (viewModel.searchMode) {
                        SearchMode.NAME -> "Buscar por nombre"
                        SearchMode.NUMBER -> "Buscar por número"
                        SearchMode.TYPE -> "Buscar por tipo"
                    }
                )
            },
            placeholder = {
                Text(
                    when (viewModel.searchMode) {
                        SearchMode.NAME -> "Ej. pikachu"
                        SearchMode.NUMBER -> "Ej. 25"
                        SearchMode.TYPE -> "Ej. electric"
                    }
                )
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.size(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                8.dp,
                Alignment.End
            )
        ) {
            Button(onClick = { viewModel.searchPokemon() }) {
                Text("Buscar")
            }

            if (viewModel.searchQuery.isNotBlank()) {
                Button(onClick = { viewModel.clearSearch() }) {
                    Text("Limpiar")
                }
            }
        }
    }
}
