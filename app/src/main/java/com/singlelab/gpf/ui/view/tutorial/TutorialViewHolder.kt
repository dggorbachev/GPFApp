package com.singlelab.gpf.ui.view.tutorial

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singlelab.gpf.R
import com.singlelab.gpf.model.tutorial.TutorialPage
import kotlinx.android.synthetic.main.item_tutorial_page.view.*

class TutorialViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_tutorial_page, parent, false)) {

    fun bind(page: TutorialPage) {
        itemView.text.setText(page.titleId)
        itemView.image.setImageResource(page.imageId)
    }
}