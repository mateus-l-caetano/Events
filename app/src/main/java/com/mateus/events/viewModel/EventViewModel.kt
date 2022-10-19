package com.mateus.events.viewModel

import androidx.lifecycle.*
import com.mateus.events.model.Event
import com.mateus.events.repository.EventRepository
import kotlinx.coroutines.launch

class EventViewModel(private val eventRepository: EventRepository): ViewModel() {
    val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    init {
        getEvents()
    }

    fun getEvents() {
        viewModelScope.launch {
            val response = eventRepository.getEvents()
            _events.value = response
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