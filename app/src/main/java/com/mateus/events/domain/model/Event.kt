package com.mateus.events.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event (
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val date: String,
    val latitude: String,
    val longitude: String,
    val price: String
) : Parcelable