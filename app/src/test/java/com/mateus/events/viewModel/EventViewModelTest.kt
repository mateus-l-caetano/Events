package com.mateus.events.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mateus.events.data.source.FakeDataSource
import com.mateus.events.model.Checkin
import com.mateus.events.model.Event
import com.mateus.events.repository.EventRepository
import getOrAwaitValue
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EventViewModelTest {
    private lateinit var viewModel: EventViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        val list: MutableList<Event> = mutableListOf()

        for(i in 1..10) {
            list.add(Event(1, "nome", "description", "image", "date", "latitude", "longitude", "price"))
        }

        val repository = EventRepository(FakeDataSource(list))
        viewModel = EventViewModelFactory(repository).create(EventViewModel::class.java)
    }

    @Test
    fun getEventsTest() {
        viewModel.getEvents()

        val value = viewModel.events.getOrAwaitValue()

        assertThat(value, not(nullValue()))
    }

    @Test
    fun checkinTest() {
        viewModel.checkin(Checkin(1, "title", "email"))

        val value = viewModel.status.getOrAwaitValue()

        assertThat(value, not(nullValue()))
    }
}