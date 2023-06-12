package com.example.projetoapi.models

import com.squareup.moshi.Json

data class Game(
    val id: Int,
    @Json(name="title") val name: String,
    @Json(name="short_description") val descricao: String,
    val thumbnail: String,
)
