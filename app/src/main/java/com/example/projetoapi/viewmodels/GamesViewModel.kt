package com.example.projetoapi.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.projetoapi.models.Game
import com.example.projetoapi.network.GamesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class GamesViewModel: ViewModel() {

    private var _telaPrincipalUiState: MutableStateFlow<TelaPrincipalUiState> = MutableStateFlow(
        TelaPrincipalUiState()
    )

    private var _gamesDetailScreenUiState: MutableStateFlow<GameDetailScreenUiState> = MutableStateFlow(
        GameDetailScreenUiState()
    )
    val gamesDetailScreenUiState: StateFlow<GameDetailScreenUiState> = _gamesDetailScreenUiState.asStateFlow()

    private var _uiState: MutableStateFlow<GamesUiState> = MutableStateFlow(GamesUiState.Loading)
    val uiState: StateFlow<GamesUiState> = _uiState.asStateFlow()

    init {
        getGames()
    }

    private fun getGames(){
        viewModelScope.launch {
            try {
                _uiState.value = GamesUiState.Success(
                    GamesApi.retrofitService.getHeroes()
                )
            }catch (e: IOException){
                    _uiState.value = GamesUiState.Error
            }catch (e: HttpException){
                    _uiState.value = GamesUiState.Error
            }
        }
    }

    var detailGame: Boolean = false

    fun gameDetail(game: Game, navController: NavController){
        detailGame = true
        _gamesDetailScreenUiState.update { currentState ->
            currentState.copy(
                nomeJogo = game.name,
                imgJogo = game.thumbnail,
                descJogo = game.descricao,
                urlJogo = game.url
            )
        }
        navigate(navController)
    }

    fun navigate(navController: NavController){
        if(_telaPrincipalUiState.value.screenName == "ListaJogos"){
            if(detailGame){
                _telaPrincipalUiState.update { currentState ->
                    currentState.copy(
                        screenName = "Game Detail",
                    )
                }
            }else {
                _telaPrincipalUiState.update { currentState ->
                    currentState.copy(
                        screenName = "ListaJogos",
                    )
                }

                _gamesDetailScreenUiState.update {
                    GameDetailScreenUiState()
                }
            }
            navController.navigate("game_detail")
        }else{
            _telaPrincipalUiState.update { currentState ->
                currentState.copy(
                    screenName = "ListaJogos",
                )
            }

            _gamesDetailScreenUiState.update {
                GameDetailScreenUiState()
            }

            detailGame = false
            navController.navigate("games_list"){
                popUpTo("games_list"){
                    inclusive = true
                }
            }

        }
    }

    fun onBackPressed(navController: NavController){

        _telaPrincipalUiState.update { currentState ->
            currentState.copy(
                screenName = "ListaJogos",
            )
        }
        navController.popBackStack()
    }





}