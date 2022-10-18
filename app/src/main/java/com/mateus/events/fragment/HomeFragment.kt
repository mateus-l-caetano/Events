package com.mateus.events.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mateus.events.databinding.FragmentHomeBinding
import com.mateus.events.network.EventsApiService
import com.mateus.events.repository.EventRepository
import com.mateus.events.viewModel.EventViewModel
import com.mateus.events.viewModel.EventViewModelFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val service = EventsApiService.retrofitService
    private val repository = EventRepository(service)

    private lateinit var viewModel: EventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = EventViewModelFactory(repository).create(EventViewModel::class.java)
        
        viewModel.getEvents().observe(viewLifecycleOwner) { events ->
            Log.d("retrofit response", events)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}