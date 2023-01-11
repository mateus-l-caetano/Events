package com.mateus.events.presenter.fragment

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mateus.events.R
import com.mateus.events.common.Resource
import com.mateus.events.databinding.FragmentCheckinBottomSheetBinding
import com.mateus.events.domain.model.CheckInResponse
import com.mateus.events.domain.model.Checkin
import com.mateus.events.presenter.viewModel.EventViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckinBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCheckinBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val args: CheckinBottomSheetFragmentArgs by navArgs()

    private val viewModel by activityViewModels<EventViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckinBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomSheetTitleAndDate()

        binding.bottomSheetCheckinButton.setOnClickListener {
            setCheckInButtonAction(it)
        }
    }

    private fun setCheckInButtonAction(it: View) {
        binding.checkinButtonText.visibility = View.INVISIBLE
        binding.checkinButtonAnimation.visibility = View.VISIBLE
        validateFildsAndPostCheckIn(it)
    }

    private fun setBottomSheetTitleAndDate() {
        binding.bottomSheetTitle.text = args.event.title
        binding.bottomSheetDate.text = args.event.date
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
            viewModel.checkInState.observe(viewLifecycleOwner) { state ->
                if(state is Resource.Success)
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
        state: Resource<CheckInResponse>,
        it: View
    ) {
        if (state.data?.code == 200) {
            Toast.makeText(
                requireContext(),
                "Check-in realizado com sucesso",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_checkinBottomSheetFragment_to_homeFragment)
        } else if(state.data?.code != 200) {
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