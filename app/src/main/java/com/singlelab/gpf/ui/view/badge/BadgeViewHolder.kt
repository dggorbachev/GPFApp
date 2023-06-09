package com.singlelab.gpf.ui.view.badge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.singlelab.gpf.R
import com.singlelab.gpf.model.profile.Badge
import kotlinx.android.synthetic.main.item_badge.view.*

class BadgeViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_badge, parent, false)) {

    fun bind(badge: Badge, isFirstItem: Boolean, isLastItem: Boolean) {
        itemView.title.text = badge.name
        itemView.description.text = badge.description

        Glide.with(itemView)
            .load(badge.badgeImageUid)
            .thumbnail(0.1f)
            .into(itemView.image)

        if (badge.isReceived) {
            itemView.image.alpha = 1f
            itemView.title.alpha = 1f
            itemView.description.alpha = 1f
        } else {
            itemView.image.alpha = 0.2f
            itemView.title.alpha = 0.2f
            itemView.description.alpha = 0.2f
        }
        itemView.top_divider.visibility = if (isFirstItem) View.VISIBLE else View.GONE
        itemView.bottom_divider.visibility = if (isLastItem) View.VISIBLE else View.GONE
    }
}