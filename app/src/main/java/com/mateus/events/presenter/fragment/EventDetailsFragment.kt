package com.mateus.events.presenter.fragment

import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import coil.load
import coil.request.ImageRequest
import com.mateus.events.R
import com.mateus.events.databinding.FragmentEventDetailsBinding
import com.mateus.events.domain.model.EventBottomSheetData
import dagger.hilt.android.AndroidEntryPoint
import java.sql.Timestamp
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EventDetailsFragment : Fragment() {
    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: EventDetailsFragmentArgs by navArgs()

    private lateinit var address: String
    private lateinit var date: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardImage.load(args.event.imageUrl) {
            setCardImage()
        }

        setEnterTransition()

        setLayoutTexts()

        binding.checkinButton.setOnClickListener {
            setCheckinButtonAction()
        }
    }

    private fun setCheckinButtonAction() {
        val action = EventDetailsFragmentDirections
            .actionEventDetailsFragmentToCheckinBottomSheetFragment(
                EventBottomSheetData(args.event.id, args.event.title, date, address)
            )
        findNavController().navigate(action)
    }

    private fun setEnterTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.cardImage.transitionName = "details_image"
            val transition = TransitionInflater.from(requireContext())
                .inflateTransition(android.R.transition.slide_bottom)
            sharedElementEnterTransition = transition
        }
    }

    private fun ImageRequest.Builder.setCardImage() {
        placeholder(R.drawable.image_placeholder)
        error(R.drawable.error_image)
    }

    private fun setLayoutTexts() {
        val format = NumberFormat.getCurrencyInstance()
        val timestamp = Timestamp(args.event.date.toLong())
        val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("E, HH:mm", Locale.getDefault())
        val latitude = args.event.latitude.toDouble()
        val longitude = args.event.longitude.toDouble()
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        var location: Address? = null
        try {
            location = geocoder.getFromLocation(latitude, longitude, 1)?.get(0)
        } catch (e: Exception) {
            Toast.makeText(context, "Ocorreu um erro ao buscar o endereço", Toast.LENGTH_LONG).show()
        }

        format.currency = Currency.getInstance("BRL")
        date = dateFormat.format(timestamp)

        binding.detailsPrice.text = format.format(args.event.price.toBigDecimal())
        binding.detailsTitle.text = args.event.title
        binding.detailsDescription.text = args.event.description
        binding.detailsDateText.text = date
        binding.detailsTimeText.text = timeFormat.format(timestamp)

        if (location != null) {
            if (location.featureName != null) {
                address = location.getAddressLine(0)
                binding.detailsLocationText.text = location.featureName
                binding.detailsAddresText.text = address
            } else {
                binding.detailsLocationText.text = location.getAddressLine(0)
                binding.detailsAddresText.text = ""
            }
        } else {
            binding.detailsLocationText.text = "Localização temporariamente indisponível"
            binding.detailsAddresText.text = ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}