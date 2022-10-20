package com.mateus.events.network

import com.mateus.events.model.Checkin
import com.mateus.events.model.Event
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private const val BASE_URL = "https://5f5a8f24d44d640016169133.mockapi.io/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface EventsApiService {
    @GET("events")
    suspend fun getEvents(): Response<List<Event>>

    @POST("checkin")
    suspend fun checkin(@Body checkin: Checkin): Response<Any>
}

object EventApi {
    val retrofitService: EventsApiService by lazy { retrofit.create(EventsApiService::class.java) }
}