package com.eagm.pokemonlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.eagm.pokemonlist.ui.navigation.AppNavigation
import com.eagm.pokemonlist.ui.theme.PokemonListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonListTheme {
                    AppNavigation()
                }
            }
        }
}

