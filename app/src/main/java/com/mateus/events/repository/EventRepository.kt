package com.mateus.events.repository

import com.mateus.events.model.Checkin
import com.mateus.events.model.Event
import com.mateus.events.network.EventsApiService
import retrofit2.Response

class EventRepository(private val eventService: EventsApiService){
    suspend fun getEvents(): Response<List<Event>> {
        return eventService.getEvents()
    }

    suspend fun checkin(checkin: Checkin): Response<Any> {
        return eventService.checkin(checkin)
    }
}