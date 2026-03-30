package com.eagm.pokemonlist.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StatItem(
    statName: String,
    statValue: Int
) {
    val progress = (statValue / 255f).coerceIn(0f, 1f)

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = formatStatName(statName))
            Text(text = statValue.toString())
        }

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

fun formatStatName(statName: String): String {
    return when (statName.lowercase()) {
        "hp" -> "HP"
        "attack" -> "Attack"
        "defense" -> "Defense"
        "special-attack" -> "Special Attack"
        "special-defense" -> "Special Defense"
        "speed" -> "Speed"
        else -> statName.replaceFirstChar { it.uppercase() }
    }
}