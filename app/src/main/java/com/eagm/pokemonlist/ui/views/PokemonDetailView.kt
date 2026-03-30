package com.eagm.pokemonlist.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.eagm.pokemonlist.core.utils.getImageUrl
import com.eagm.pokemonlist.data.model.PokemonData
import com.eagm.pokemonlist.ui.components.ChipComponent
import com.eagm.pokemonlist.ui.components.PokemonSpriteItem
import com.eagm.pokemonlist.ui.components.StatItem
import com.eagm.pokemonlist.ui.state.UiState
import com.eagm.pokemonlist.ui.viewmodels.PokemonDetailViewModel

@Composable
fun PokemonDetailView(
                      viewModel: PokemonDetailViewModel,
                      onTypeClick: (String) -> Unit) {

    when (val state = viewModel.state) {
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
                    Text(state.message)
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
                Text("Sin información")
            }
        }

        is UiState.Success -> {
            PokemonDetailContent(
                pokemon = state.data,
                onTypeClick = onTypeClick
            )
        }
    }

}

@Composable
fun PokemonDetailContent(
    pokemon: PokemonData,
    onTypeClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = pokemon.name.replaceFirstChar { it.uppercase() },
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Número: ${pokemon.id}",
                fontSize = 16.sp
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            PokemonSpriteItem(
                imageModel = pokemon.sprites.frontDefault,
                label = "Normal"
            )

            Spacer(modifier = Modifier.size(16.dp))

            PokemonSpriteItem(
                imageModel = pokemon.sprites.frontShiny,
                label = "Shiny"
            )
        }

        Text(
            text = "Tipos",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            pokemon.types.forEach { typeSlot ->
                ChipComponent(
                    type = typeSlot.type.name,
                    onClick = onTypeClick
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "Stats",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        pokemon.stats.forEach { stat ->
            StatItem(
                statName = stat.stat.name,
                statValue = stat.baseStat
            )
        }
    }
}
