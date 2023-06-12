package com.example.projetoapi.views


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projetoapi.viewmodels.GamesViewModel



@Composable
fun TelaPrincipal(
    gamesViewModel: GamesViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "games_list") {
           composable("games_list") {
                GamesScreen(navController = navController, gamesViewModel = gamesViewModel)
            }
           composable("game_detail") {
                GameDetailScreen(navController = navController, gamesViewModel = gamesViewModel)
            }
        }
    }


