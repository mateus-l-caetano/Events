package com.mateus.events.domain.use_case

import com.mateus.events.common.Resource
import com.mateus.events.domain.model.CheckInResponse
import com.mateus.events.domain.model.Checkin
import com.mateus.events.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckInUseCase @Inject constructor(
    private val repository: EventRepository
) {
    operator fun invoke(checkin: Checkin): Flow<Resource<CheckInResponse>> = flow{
        try {
            emit(Resource.Loading())
            val events = repository.checkin(checkin)
            emit(
                Resource.Success(
                    CheckInResponse(
                        events.code.toInt()
                    )
                )
            )
        } catch (e: Exception){
            emit(Resource.Error("Erro ao fazer check-in"))
        }
    }
}