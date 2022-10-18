package com.mateus.events.network

import androidx.lifecycle.LiveData
import com.mateus.events.model.Event
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.GET

private val BASE_URL = "https://5f5a8f24d44d640016169133.mockapi.io/api/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface EventsApiService {
    @GET("events")
    suspend fun getEvents(): String

    companion object EventApi {
        val retrofitService: EventsApiService by lazy { retrofit.create(EventsApiService::class.java) }
    }
}