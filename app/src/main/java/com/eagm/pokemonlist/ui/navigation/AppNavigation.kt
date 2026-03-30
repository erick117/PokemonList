package com.eagm.pokemonlist.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eagm.pokemonlist.ui.viewmodels.PokemonDetailViewModel
import com.eagm.pokemonlist.ui.viewmodels.PokemonListViewModel
import com.eagm.pokemonlist.ui.views.PokemonDetailView
import com.eagm.pokemonlist.ui.views.PokemonListView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            when {
                currentRoute?.startsWith(RouteViews.DETAIL) == true -> {
                    TopAppBar(
                        title = { Text("Detalle") },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Volver"
                                )
                            }
                        }
                    )
                }

                else -> {
                    TopAppBar(
                        title = { Text("Pokédex") }
                    )
                }
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = RouteViews.LIST,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(
                route = RouteViews.LIST_WITH_TYPE,
                arguments = listOf(
                    navArgument("type") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) { backStackEntry ->

                val selectedType = backStackEntry.arguments?.getString("type")
                val listViewModel: PokemonListViewModel = hiltViewModel()

                LaunchedEffect(selectedType) {
                    if (selectedType.isNullOrBlank()) {
                        listViewModel.loadInitialPokemonsIfNeeded()
                    } else {
                        listViewModel.applyTypeFilter(selectedType)
                    }
                }

                PokemonListView(
                    viewModel = listViewModel,
                    onPokemonClick = { pokemonName ->
                        navController.navigate(RouteViews.detailRoute(pokemonName))
                    }
                )
            }

            composable(
                route = RouteViews.DETAIL_WITH_ARG,
                arguments = listOf(
                    navArgument("pokemonName") {
                        type = NavType.StringType
                    }
                )
            ) {
                val detailViewModel: PokemonDetailViewModel = hiltViewModel()

                PokemonDetailView(
                    viewModel = detailViewModel,
                    onTypeClick = { type ->
                        navController.navigate(RouteViews.listRouteWithType(type)) {
                            popUpTo(RouteViews.LIST) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}