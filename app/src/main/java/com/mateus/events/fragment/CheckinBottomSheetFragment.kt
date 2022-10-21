package com.mateus.events.fragment

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mateus.events.R
import com.mateus.events.databinding.FragmentCheckinBottomSheetBinding
import com.mateus.events.model.Checkin
import com.mateus.events.network.EventApi
import com.mateus.events.repository.EventRepository
import com.mateus.events.viewModel.EventViewModel
import com.mateus.events.viewModel.EventViewModelFactory

class CheckinBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCheckinBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val args: CheckinBottomSheetFragmentArgs by navArgs()

    private val service = EventApi.retrofitService
    private val repository = EventRepository(service)

    private lateinit var viewModel: EventViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckinBottomSheetBinding.inflate(inflater, container, false)

        viewModel = EventViewModelFactory(repository).create(EventViewModel::class.java)

        binding.bottomSheetTitle.text = args.event.title
        binding.bottomSheetDate.text = args.event.date

        binding.bottomSheetCheckinButton.setOnClickListener {
            binding.checkinButtonText.visibility = View.INVISIBLE
            binding.checkinButtonAnimation.visibility = View.VISIBLE
            validateFildsAndPostCheckIn(it)
        }

        return binding.root
    }

    private fun validateFildsAndPostCheckIn(it: View) {
        if(binding.nameInput.text.isNullOrBlank() ||
            binding.emailInput.text.isNullOrBlank() ||
            !Patterns.EMAIL_ADDRESS
                .matcher(binding.emailInput.text.toString()).matches()) {

            validateTextFields()

        } else {
            it.isClickable = false
            viewModel.checkin(
                Checkin(
                    args.event.id,
                    binding.nameInput.text.toString(),
                    binding.emailInput.text.toString()
                )
            )
            viewModel.status.observe(viewLifecycleOwner) { state ->
                postCheckIn(state, it)
            }
        }
    }

    private fun validateTextFields() {
        binding.checkinButtonText.visibility = View.VISIBLE
        binding.checkinButtonAnimation.visibility = View.INVISIBLE

        if (binding.nameInput.text.isNullOrBlank()) {
            binding.nameInput.error = "Campo obrigatório"
        }

        if (binding.emailInput.text.isNullOrBlank()) {
            binding.emailInput.error = "Campo obrigatório"
        }

        else if (!Patterns.EMAIL_ADDRESS
                .matcher(binding.emailInput.text.toString()).matches()
        ) {
            binding.emailInput.error = "Insira um email válido"
        }
    }

    private fun postCheckIn(
        state: EventViewModel.State?,
        it: View
    ) {
        if (state == EventViewModel.State.SUCCSSESS) {
            Toast.makeText(
                requireContext(),
                "Check-in realizado com sucesso",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_checkinBottomSheetFragment_to_homeFragment)
        } else if (state == EventViewModel.State.ERROR) {
            Toast.makeText(context, "Erro ao tentar fazer check-in", Toast.LENGTH_SHORT)
                .show()
            it.isClickable = true
            binding.checkinButtonText.visibility = View.VISIBLE
            binding.checkinButtonAnimation.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}