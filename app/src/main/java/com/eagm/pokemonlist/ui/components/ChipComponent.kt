package com.eagm.pokemonlist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eagm.pokemonlist.core.utils.getPokemonTypeColor

@Composable
fun ChipComponent(type: String,
                  onClick: (String) -> Unit) {
    val backgroundColor = getPokemonTypeColor(type)

    Text(
        text = type.replaceFirstChar { it.uppercase() },
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick(type) }
            .padding(horizontal = 12.dp, vertical = 6.dp),
        color = Color.White
    )
}