package com.example.projetoapi.network

import com.example.projetoapi.models.Game
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://www.freetogame.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface GamesApiService {

    @GET("api/games")
    //função suspensa pois roda em corrotina, acessos a internet não podem travar a thread principal
    suspend fun getHeroes(): List<Game>
}

//Cria um instancia da classe OpenDotaApiService
object GamesApi{
    val retrofitService: GamesApiService by lazy{
        retrofit.create(GamesApiService::class.java)
    }
}
