package com.singlelab.gpf.ui.cities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singlelab.gpf.R
import com.singlelab.gpf.model.city.City
import kotlinx.android.synthetic.main.item_city.view.*


class CitiesViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_city, parent, false)) {

    fun bind(city: City, listener: OnCityClickListener) {
        itemView.title.text = city.cityName
        itemView.setOnClickListener {
            listener.onCityClick(city)
        }
    }
}