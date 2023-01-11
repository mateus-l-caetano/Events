package com.mateus.events.presenter.viewModel

import androidx.lifecycle.*
import com.mateus.events.common.Resource
import com.mateus.events.domain.model.CheckInResponse
import com.mateus.events.domain.model.Checkin
import com.mateus.events.domain.model.Event
import com.mateus.events.domain.use_case.GetEventsUseCase
import com.mateus.events.domain.use_case.CheckInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val checkInUseCase: CheckInUseCase
): ViewModel() {
    private val _events = MutableLiveData<Resource<List<Event>>>()
    val events: LiveData<Resource<List<Event>>> = _events

    private val _checkIn = MutableLiveData<Resource<CheckInResponse>>()
    val checkInState: LiveData<Resource<CheckInResponse>> = _checkIn

    init {
        getEvents()
    }

    fun getEvents() {
        getEventsUseCase().onEach { response ->
            _events.value = response
        }.launchIn(viewModelScope)
    }

    fun checkin(checkin: Checkin) {
        checkInUseCase(checkin).onEach { response ->
            _checkIn.value = response
        }.launchIn(viewModelScope)
    }
}