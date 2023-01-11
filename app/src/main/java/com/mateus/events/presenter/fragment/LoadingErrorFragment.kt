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
import com.mateus.events.databinding.FragmentLoadingErrorBinding
import com.mateus.events.presenter.adapter.EventAdapter
import com.mateus.events.presenter.viewModel.EventViewModel

class LoadingErrorFragment : Fragment() {

    private var _binding: FragmentLoadingErrorBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<EventViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadingErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tryAgainButton.setOnClickListener {
            it.isClickable = false
            viewModel.getEvents()
            viewModel.events.observe(viewLifecycleOwner) { state ->
                when(state) {
                    is Resource.Loading -> {
                        findNavController().navigate(R.id.action_loadingErrorFragment_to_homeFragment)
                    }
                    is Resource.Success -> {

                    }
                    is Resource.Error -> {
                        it.isClickable = true
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}