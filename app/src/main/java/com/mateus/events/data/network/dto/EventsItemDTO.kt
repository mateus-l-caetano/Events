package com.mateus.events.data.network.dto

import com.mateus.events.domain.model.Event

data class EventsItemDTO(
    val date: Long,
    val description: String,
    val id: String,
    val image: String,
    val latitude: Double,
    val longitude: Double,
    val people: List<Any>,
    val price: Double,
    val title: String
)

fun EventsItemDTO.toEvent() = Event(
    id = id,
    title = title,
    description = description,
    imageUrl = image,
    date = date.toString(),
    latitude = latitude.toString(),
    longitude = longitude.toString(),
    price = price.toString()
)