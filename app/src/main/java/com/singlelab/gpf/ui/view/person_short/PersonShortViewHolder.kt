package com.singlelab.gpf.ui.view.person_short

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.singlelab.gpf.R
import com.singlelab.gpf.model.profile.Person
import com.singlelab.gpf.util.PluralsUtil
import kotlinx.android.synthetic.main.item_person.view.image_person
import kotlinx.android.synthetic.main.item_person.view.name
import kotlinx.android.synthetic.main.item_person_short.view.*

class PersonShortViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_person_short, parent, false)) {

    fun bind(person: Person, listener: OnPersonShortClickListener?) {
        itemView.name.text = person.name

        if (person.imageContentUid != null) {
            Glide.with(itemView)
                .load(person.imageContentUid)
                .into(itemView.image_person)
        }
        itemView.setOnClickListener {
            listener?.onPersonClick(person.personUid)
        }

        val age = PluralsUtil.getString(
            person.age,
            "год",
            "года",
            "года",
            "года",
            "лет"
        )
        itemView.age_and_city.text = "$age, ${person.cityName}"
    }
}