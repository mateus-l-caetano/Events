package com.mateus.events.presenter.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mateus.events.R
import com.mateus.events.domain.model.Event
import com.mateus.events.domain.model.EventBottomSheetData
import com.mateus.events.presenter.fragment.HomeFragmentDirections

class EventAdapter(private val dataSet: List<Event>): RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.card_title)
        val image: ImageView = view.findViewById(R.id.card_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataSet[position].title
        holder.image.load(dataSet[position].imageUrl) {
            placeholder(R.drawable.image_placeholder)
            error(R.drawable.error_image)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.image.transitionName = "item_image"
        }
        holder.view.setOnClickListener { view ->
            val extras = FragmentNavigatorExtras(holder.image to "details_image")
            val action = HomeFragmentDirections.actionHomeFragmentToEventDetailsFragment(dataSet[position])
            view.findNavController().navigate(
                action, extras
            )
        }

    }

    override fun getItemCount() = dataSet.size
}