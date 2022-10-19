package com.mateus.events.model

import com.squareup.moshi.Json

data class Event (
    val title: String,
    @Json(name = "image") val imageUrl: String
)