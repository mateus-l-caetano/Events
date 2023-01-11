package com.mateus.events.domain.use_case

import com.mateus.events.common.Resource
import com.mateus.events.data.network.dto.toEvent
import com.mateus.events.domain.model.Event
import com.mateus.events.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val repository: EventRepository
) {
    operator fun invoke(): Flow<Resource<List<Event>>> = flow{
        try {
            emit(Resource.Loading())
            val events = repository.getEvents().map { it.toEvent() }
            emit(Resource.Success(events))
        } catch (e: Exception){
            emit(Resource.Error("Verifique sua conexão e tente novamente"))
        }
    }
}