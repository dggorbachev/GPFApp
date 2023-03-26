package com.singlelab.gpf.ui.swiper_person.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.singlelab.gpf.R
import com.singlelab.gpf.model.profile.Person
import com.singlelab.gpf.ui.chat.common.view.OnClickImageListener
import com.singlelab.gpf.util.PluralsUtil
import kotlinx.android.synthetic.main.item_card_person.view.*

class CardPersonViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_card_person, parent, false)) {

    private var listener: OnClickImageListener? = null

    fun bind(person: Person, listener: OnClickImageListener) {
        this.listener = listener
        val age = PluralsUtil.getString(
            person.age,
            "год",
            "года",
            "года",
            "года",
            "лет"
        )
        itemView.name.text = "${person.name}, $age"
        itemView.login.text = "@${person.login}"
        itemView.description.text = person.description
        itemView.city.text = person.cityName
        if (!person.imageContentUid.isNullOrEmpty()) {
            Glide.with(itemView)
                .load(person.imageContentUid)
                .thumbnail(0.1f)
                .into(itemView.image)
            itemView.image.setOnClickListener {
                showFullScreenImage(person.imageContentUid)
            }
        }
    }

    private fun showFullScreenImage(imageContentUid: String) {
        itemView.context?.let {
            val links = listOf(imageContentUid)
            listener?.onClickImage(links)
        }
    }
}