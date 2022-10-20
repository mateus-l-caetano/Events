package com.mateus.events.repository

import com.mateus.events.model.Checkin
import com.mateus.events.model.Event
import com.mateus.events.network.EventsApiService
import com.squareup.moshi.Json
import retrofit2.Response
import retrofit2.http.Body

class EventRepository(private val eventService: EventsApiService){
    suspend fun getEvents(): Response<List<Event>> {
        return eventService.getEvents()
    }

    suspend fun checkin(checkin: Checkin): Response<Any> {
        return eventService.checkin(checkin)
    }
}