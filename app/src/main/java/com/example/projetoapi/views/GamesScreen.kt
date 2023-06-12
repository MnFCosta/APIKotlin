package com.example.projetoapi.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dotaheroes.R
import com.example.projetoapi.models.Game
import com.example.projetoapi.viewmodels.GamesViewModel
import com.example.projetoapi.viewmodels.GamesUiState
import androidx.navigation.NavController

@Composable
fun GamesScreen(
    navController: NavController,
    gamesViewModel: GamesViewModel = viewModel()
) {
    val uiState by gamesViewModel.uiState.collectAsState()
    when(uiState){
        is GamesUiState.Loading -> LoadingScreen()
        is GamesUiState.Success -> GamesList((uiState as GamesUiState.Success).games, onClickGame = { gamesViewModel.gameDetail(it, navController)})
        is GamesUiState.Error -> ErrorScreen()
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "CARREGANDO")
    }
}

@Composable
fun GamesList(
    games: List<Game>,
    onClickGame: (Game) -> Unit,
) {
    LazyVerticalGrid(
        contentPadding = PaddingValues(vertical = 8.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),

        columns = GridCells.Fixed(1)
    ){
        items(games){ game ->
            ItemGame(game = game, onClickGame = {onClickGame(game)})
            
        }
    }
}

@Composable
fun ItemGame(
    game: Game,
    onClickGame: () -> Unit,
) {
    val density = LocalDensity.current.density
    val width = remember { mutableStateOf(0F) }
    val height = remember { mutableStateOf(0F) }

    Box(
        modifier = Modifier
            .padding(bottom = 30.dp)
            .size(width = 10.dp, height = 240.dp)
    ) {

        Card(
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, Color.White, shape = MaterialTheme.shapes.medium)
                .clickable { onClickGame()},
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Box() {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(game.thumbnail)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.mhw_placeholder),
                    contentDescription = game.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RectangleShape)
                        .onGloballyPositioned {
                            width.value = it.size.width / density
                            height.value = it.size.height / density
                        }
                )
                Box(
                    modifier = Modifier
                        .size(
                            width = width.value.dp,
                            height = height.value.dp
                        )
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black),
                                400F,
                                650F
                            )
                        )
                ) {

                }
                Text(
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 10.dp),
                    text = game.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}



@Composable
fun ErrorScreen() {
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "DEU RUIM")
    }
}