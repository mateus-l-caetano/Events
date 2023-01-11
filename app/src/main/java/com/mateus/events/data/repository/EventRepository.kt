package com.mateus.events.data.repository

import com.mateus.events.domain.model.Checkin
import com.mateus.events.domain.model.Event
import com.mateus.events.data.network.EventsApiService
import com.mateus.events.data.network.dto.CheckInDTO
import com.mateus.events.data.network.dto.EventsItemDTO
import com.mateus.events.domain.repository.EventRepository
import retrofit2.Response
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val eventService: EventsApiService
) : EventRepository{
    override suspend fun getEvents(): List<EventsItemDTO> {
        return eventService.getEvents()
    }

    override suspend fun checkin(checkin: Checkin): CheckInDTO {
        return eventService.checkin(checkin)
    }
}