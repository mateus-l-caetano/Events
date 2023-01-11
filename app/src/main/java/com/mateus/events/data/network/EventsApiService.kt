package com.mateus.events.data.network

import com.mateus.events.data.network.dto.CheckInDTO
import com.mateus.events.data.network.dto.EventsItemDTO
import com.mateus.events.domain.model.Checkin
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EventsApiService {
    @GET("events")
    suspend fun getEvents(): List<EventsItemDTO>

    @POST("checkin")
    suspend fun checkin(@Body checkin: Checkin): CheckInDTO
}