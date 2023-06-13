package com.example.projetoapi.views

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.dotaheroes.R
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
        thumbnail = uiState.imgJogo,
        url = uiState.urlJogo,

    )
}

@Composable
fun GameDetail(
    nome: String,
    desc: String,
    thumbnail: String,
    url: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .border(BorderStroke(2.dp, Color(0xFF152C3F))),
    ) {

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter

    ) {
        Text(
            color = Color.White,
            text = "Sobre: $nome",
            fontSize = 20.sp,
            modifier = Modifier.padding(5.dp)
        )
    }

    Column(
        modifier = Modifier
            .padding(top = 40.dp)
            .padding(12.dp)
            .height(220.dp)
            .border(1.dp, Color.White, shape = MaterialTheme.shapes.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(thumbnail)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.mhw_placeholder),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium),
        )
    }

    Column(
        modifier = Modifier
            .padding(top = 280.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .border(1.dp, Color.White, shape = MaterialTheme.shapes.medium)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(color = Color.White, text = "Descrição: $desc")

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top=16.dp, bottom=16.dp),
                contentAlignment = Alignment.Center

            ){
                ClickableUrlText(url = url, displayText = "Interessado? Clique aqui!")
            }
        }
    }

    }

}


@Composable
fun ClickableUrlText(url: String, displayText: String) {
    val context = LocalContext.current
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = 22.sp,
                color = Color.White,
            )
        ) {
            append(displayText)
            addStringAnnotation("URL", url, 0, displayText.length)
        }
    }

    val onClick: (String) -> Unit = { clickedUrl ->
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(clickedUrl))
        context.startActivity(intent)
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations("URL", offset, offset)
                .firstOrNull()?.let {
                    onClick(it.item)
                }
        },
        modifier = Modifier.clickable { onClick(url) }
    )
}