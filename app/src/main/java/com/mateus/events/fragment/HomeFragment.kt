package com.mateus.events.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mateus.events.adapter.EventAdapter
import com.mateus.events.databinding.FragmentHomeBinding
import com.mateus.events.network.EventApi
import com.mateus.events.repository.EventRepository
import com.mateus.events.viewModel.EventViewModel
import com.mateus.events.viewModel.EventViewModel.State
import com.mateus.events.viewModel.EventViewModelFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val service = EventApi.retrofitService
    private val repository = EventRepository(service)

    private lateinit var viewModel: EventViewModel
    private lateinit var adapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = EventViewModelFactory(repository).create(EventViewModel::class.java)

        viewModel.events.observe(viewLifecycleOwner) { events ->
            adapter = EventAdapter(events.reversed())
            binding.homeScreenRecyclerView.adapter = adapter
        }

        viewModel.status.observe(viewLifecycleOwner) { state ->
            if(state == State.SUCCSSESS) {
                binding.loadingImage.visibility = View.INVISIBLE
                binding.homeScreenRecyclerView.visibility = View.VISIBLE
            } else if(state == State.ERROR) {

            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}