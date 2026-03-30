package com.eagm.pokemonlist.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
fun PokemonSpriteItem(
    imageModel: Any?,
    label: String,
    modifier: Modifier = Modifier,
    imageSize: Dp = 120.dp
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubcomposeAsyncImage(
            model = imageModel ?: "",
            contentDescription = label,
            modifier = Modifier.size(imageSize)
        ) {
            when (painter.state) {
                is coil.compose.AsyncImagePainter.State.Loading -> {
                    Box(
                        modifier = Modifier.size(imageSize),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is coil.compose.AsyncImagePainter.State.Error -> {
                    Box(
                        modifier = Modifier.size(imageSize),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Error")
                    }
                }

                else -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }

        Spacer(modifier = Modifier.size(4.dp))
        Text(label)
    }
}