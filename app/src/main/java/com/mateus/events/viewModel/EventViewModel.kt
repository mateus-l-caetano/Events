package com.mateus.events.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.mateus.events.model.Checkin
import com.mateus.events.model.Event
import com.mateus.events.repository.EventRepository
import kotlinx.coroutines.launch

class EventViewModel(private val eventRepository: EventRepository): ViewModel() {
    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    private val _status = MutableLiveData<State>()
    val status: LiveData<State> = _status

    enum class State {
        LOADING, SUCCSSESS, ERROR
    }

    init {
        getEvents()
    }

    fun getEvents() {
        _status.value = State.LOADING
        viewModelScope.launch {
            try {
                val response = eventRepository.getEvents()
                if (response.isSuccessful) {
                    _status.value = State.SUCCSSESS
                    _events.value = response.body()
                } else {
                    _status.value = State.ERROR
                    _events.value = listOf()
                }
            } catch (e: Exception) {
                _status.value = State.ERROR
            }
        }
    }

    fun checkin(checkin: Checkin) {
        _status.value = State.LOADING
        viewModelScope.launch {
            try {
                val response = eventRepository.checkin(checkin)
                if(response.isSuccessful) {
                    _status.value = State.SUCCSSESS
                    Log.d("debug", response.body().toString())
                } else {
                    _status.value = State.ERROR
                    Log.d("debug", response.errorBody().toString())
                }
            } catch (e: Exception) {
                _status.value = State.ERROR
            }
        }
    }
}

class EventViewModelFactory(private val repository: EventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}