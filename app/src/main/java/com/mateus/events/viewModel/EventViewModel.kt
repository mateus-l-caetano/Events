package com.mateus.events.viewModel

import androidx.lifecycle.*
import com.mateus.events.model.Event
import com.mateus.events.repository.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventViewModel(private val eventRepository: EventRepository): ViewModel() {
    val events: MutableLiveData<String> = MutableLiveData<String>()

    fun getEvents(): LiveData<String> {
        viewModelScope.launch {
            val response = eventRepository.getEvents()
            events.value = response
        }
        return events
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