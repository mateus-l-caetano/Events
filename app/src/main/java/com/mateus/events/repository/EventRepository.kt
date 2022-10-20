package com.mateus.events.repository

import com.mateus.events.model.Event
import com.mateus.events.network.EventsApiService

class EventRepository(private val eventService: EventsApiService){
    suspend fun getEvents(): List<Event> {
        return eventService.getEvents()
    }
}