package com.mateus.events.fragment

import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.mateus.events.R
import com.mateus.events.databinding.FragmentEventDetailsBinding
import java.sql.Timestamp
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class EventDetailsFragment : Fragment() {
    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!

    val args: EventDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        val format = NumberFormat.getCurrencyInstance()
        format.currency = Currency.getInstance("BRL")

        binding.imageView.load(args.event.imageUrl) {
            placeholder(R.drawable.image_placeholder)
            error(R.drawable.error_image)
        }
        binding.detailsTitle.text = args.event.title
        binding.detailsDescription.text = args.event.description
        binding.detailsPrice.text = format.format(args.event.price.toBigDecimal())
        val timestamp = Timestamp(args.event.date.toLong())

        val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("E, HH:mm", Locale.getDefault())
        binding.detailsDateText.text = dateFormat.format(timestamp)
        binding.detailsTimeText.text = timeFormat.format(timestamp)

        val latitude = args.event.latitude.toDouble()
        val longitude = args.event.longitude.toDouble()
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val location = geocoder.getFromLocation(latitude, longitude, 1)?.get(0)

        if(location != null) {
            if (location.featureName != null){
                binding.detailsLocationText.text = location.featureName
                binding.detailsAddresText.text = location.getAddressLine(0)
            }
            else {
                binding.detailsLocationText.text = location.getAddressLine(0)
                binding.detailsAddresText.text = ""
            }
        } else {
            binding.detailsLocationText.text = "Localização temporariamente indisponível"
            binding.detailsAddresText.text = ""
        }

        return view
    }
}