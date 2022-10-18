package com.mateus.events.repository

import androidx.lifecycle.LiveData
import com.mateus.events.model.Event
import com.mateus.events.network.EventsApiService

class EventRepository(private val eventService: EventsApiService){
    suspend fun getEvents(): String {
        return eventService.getEvents()
    }
}