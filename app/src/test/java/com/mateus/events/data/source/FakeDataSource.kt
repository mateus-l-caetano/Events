package com.mateus.events.data.source

import com.mateus.events.model.Checkin
import com.mateus.events.model.Event
import com.mateus.events.network.EventsApiService
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response


class FakeDataSource(var events: MutableList<Event>? = mutableListOf()) : EventsApiService {
    override suspend fun getEvents(): Response<List<Event>> {
        if(events != null) {
            if (events!!.isEmpty()) {
                return Response.error(
                    500,
                    ResponseBody.create(
                        MediaType.parse("application/json"),
                        "{\"key\":[\"internal error\"]}"
                    )
                )
            } else {
                return Response.success(events)
            }
        } else {
            return Response.error(
                400,
                ResponseBody.create(
                    MediaType.parse("application/json"),
                    "{\"key\":[\"internal error\"]}"
                )
            )
        }
    }

    override suspend fun checkin(checkin: Checkin): Response<Any> {
        return Response.success(ResponseBody.create(
            MediaType.parse("application/json"),
            "{\"code\":[\"200\"]}"
        ))
    }
}