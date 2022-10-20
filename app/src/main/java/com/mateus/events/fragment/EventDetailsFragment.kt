package com.mateus.events.fragment

import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import coil.load
import com.mateus.events.R
import com.mateus.events.databinding.FragmentEventDetailsBinding
import com.mateus.events.model.Checkin
import com.mateus.events.model.Event
import com.mateus.events.network.EventApi
import com.mateus.events.repository.EventRepository
import com.mateus.events.viewModel.EventViewModel
import com.mateus.events.viewModel.EventViewModel.State.*
import com.mateus.events.viewModel.EventViewModelFactory
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

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
        val view = binding.root

        val format = NumberFormat.getCurrencyInstance()
        format.currency = Currency.getInstance("BRL")

        binding.cardImage.load(args.event.imageUrl) {
            placeholder(R.drawable.image_placeholder)
            error(R.drawable.error_image)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.cardImage.transitionName = "details_image"
            val transition = TransitionInflater.from(requireContext())
                .inflateTransition(android.R.transition.slide_bottom)
            sharedElementEnterTransition = transition
        }

        binding.detailsTitle.text = args.event.title
        binding.detailsDescription.text = args.event.description
        binding.detailsPrice.text = format.format(args.event.price.toBigDecimal())
        val timestamp = Timestamp(args.event.date.toLong())

        val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("E, HH:mm", Locale.getDefault())
        date = dateFormat.format(timestamp)
        binding.detailsDateText.text = date
        binding.detailsTimeText.text = timeFormat.format(timestamp)

        val latitude = args.event.latitude.toDouble()
        val longitude = args.event.longitude.toDouble()
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val location = geocoder.getFromLocation(latitude, longitude, 1)?.get(0)

        if(location != null) {
            if (location.featureName != null){
                address = location.getAddressLine(0)
                binding.detailsLocationText.text = location.featureName
                binding.detailsAddresText.text = address
            }
            else {
                binding.detailsLocationText.text = location.getAddressLine(0)
                binding.detailsAddresText.text = ""
            }
        } else {
            binding.detailsLocationText.text = "Localização temporariamente indisponível"
            binding.detailsAddresText.text = ""
        }

        binding.checkinButton.setOnClickListener {
            val action = EventDetailsFragmentDirections
                .actionEventDetailsFragmentToCheckinBottomSheetFragment(
                    EventBottomSheetData(args.event.id, args.event.title, date, address)
                )
            findNavController().navigate(action)
        }

        return view
    }
}

@Parcelize
data class EventBottomSheetData (
    val id: Int,
    val title: String,
    val date: String,
    val address: String,
) : Parcelable