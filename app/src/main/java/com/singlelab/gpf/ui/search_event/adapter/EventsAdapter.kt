package com.singlelab.gpf.ui.search_event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singlelab.gpf.model.event.EventSummary
import com.singlelab.gpf.ui.view.event.OnEventItemClickListener

class EventsAdapter(
    private val list: MutableList<EventSummary>,
    private val listener: OnEventItemClickListener
) : RecyclerView.Adapter<EventsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EventsViewHolder(
            inflater,
            parent
        )
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val event = list[position]
        holder.bind(event, listener)
    }

    override fun getItemCount(): Int = list.size

    fun setData(list: List<EventSummary>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}