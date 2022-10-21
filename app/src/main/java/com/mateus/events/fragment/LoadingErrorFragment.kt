package com.mateus.events.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import com.mateus.events.R
import com.mateus.events.databinding.FragmentLoadingErrorBinding

class LoadingErrorFragment : Fragment() {

    private var _binding: FragmentLoadingErrorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoadingErrorBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.tryAgainButton.setOnClickListener {
            findNavController().navigate(R.id.action_loadingErrorFragment_to_homeFragment)
        }

        return view
    }
}