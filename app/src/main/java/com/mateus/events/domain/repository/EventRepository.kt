package com.mateus.events.domain.repository

import com.mateus.events.data.network.dto.CheckInDTO
import com.mateus.events.data.network.dto.EventsItemDTO
import com.mateus.events.domain.model.Checkin
import retrofit2.Response

interface EventRepository {
    suspend fun getEvents(): List<EventsItemDTO>
    suspend fun checkin(checkin: Checkin): CheckInDTO
}