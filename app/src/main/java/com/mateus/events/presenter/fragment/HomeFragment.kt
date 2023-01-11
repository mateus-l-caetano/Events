package com.mateus.events.presenter.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mateus.events.R
import com.mateus.events.common.Resource
import com.mateus.events.databinding.FragmentHomeBinding
import com.mateus.events.presenter.adapter.EventAdapter
import com.mateus.events.presenter.viewModel.EventViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<EventViewModel>()
    private lateinit var adapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.events.observe(viewLifecycleOwner) { state ->
            when(state) {
                is Resource.Loading -> {
                    binding.loadingImage.visibility = View.VISIBLE
                    binding.homeScreenRecyclerView.visibility = View.INVISIBLE
                }
                is Resource.Success -> {
                    binding.loadingImage.visibility = View.INVISIBLE
                    binding.homeScreenRecyclerView.visibility = View.VISIBLE
                    adapter = EventAdapter(state.data?.reversed() ?: emptyList())
                    binding.homeScreenRecyclerView.adapter = adapter
                }
                is Resource.Error -> {
                    findNavController().navigate(R.id.action_homeFragment_to_loadingErrorFragment)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("entrou", "home")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}