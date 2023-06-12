package com.example.projetoapi.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projetoapi.viewmodels.GamesViewModel

@Composable
fun GameDetailScreen(
    navController: NavController,
    gamesViewModel: GamesViewModel
) {
    val uiState by gamesViewModel.gamesDetailScreenUiState.collectAsState()

    BackHandler {
        gamesViewModel.onBackPressed(navController)
    }

    GameDetail(
        nome = uiState.nomeJogo,
        desc = uiState.descJogo,

    )
}

@Composable
fun GameDetail(
    nome: String,
    desc: String,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = "Sobre: $nome",
            fontSize = 20.sp,
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(text = "Descição: $desc")

        }
    }
}