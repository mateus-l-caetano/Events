package com.mateus.events.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mateus.events.R
import com.mateus.events.model.Event

class EventAdapter(private val dataSet: List<Event>): RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView
        val image: ImageView
        init {
            title = view.findViewById(R.id.card_title)
            image = view.findViewById(R.id.card_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataSet[position].title
        holder.image.load(dataSet[position].imageUrl)
    }

    override fun getItemCount() = dataSet.size
}