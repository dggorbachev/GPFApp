package com.singlelab.gpf.ui.swiper_event.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.singlelab.gpf.R
import com.singlelab.gpf.model.event.Event
import kotlinx.android.synthetic.main.item_card_event.view.*

class CardEventViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_card_event, parent, false)) {

    fun bind(event: Event, listener: OnCardEventListener) {
        itemView.title.text = event.tempName + ", " + event.tempAge
        itemView.descMan.text = event.tempDescription
        itemView.record2048.text = event.record2048.toString()
        itemView.recordFlappyCats.text = event.recordFlappyCat.toString()
        itemView.recordPianoTiles.text = event.recordPiano.toString()
        itemView.text_location.text = event.tempCity
//        itemView.start_date.text =
//            event.startTime.parse(Const.DATE_FORMAT_TIME_ZONE, Const.DATE_FORMAT_ON_CARD)

        if (event.eventPrimaryImageContentUid != null) {
            event.eventPrimaryImageContentUid.let {
//                Glide.with(itemView)
//                    .load(it)
//                    .thumbnail(0.3f)
//                    .into(itemView.image)
            }
//            itemView.image.setOnClickListener {
//                val images = mutableListOf<String>()
//                images.add(event.eventPrimaryImageContentUid)
//                images.addAll(event.images ?: arrayListOf())
//                listener.onImageClick(images)
//            }
        } else {
//            itemView.image.setImageDrawable(
//                ContextCompat.getDrawable(
//                    itemView.context,
//                    R.mipmap.image_event_default
//                )
//            )
        }

        if (event.tempGames.size < 3) {
            itemView.emoji_card_three.visibility = View.GONE
        }
        if (event.tempGames.size < 2) {
            itemView.emoji_card_two.visibility = View.GONE
        }

        event.tempGames.forEachIndexed { index, s ->
            when (index) {
                0 -> {
                    if (s == "CS:GO") {
                        Glide.with(itemView)
                            .load(ContextCompat.getDrawable(itemView.context, R.drawable.csgo))
                            .into(itemView.emoji_one)
                    }
                    if (s == "Overwatch") {
                        Glide.with(itemView)
                            .load(ContextCompat.getDrawable(itemView.context, R.drawable.overwatch))
                            .into(itemView.emoji_one)
                    }
                    if (s == "Dota 2") {
                        Glide.with(itemView)
                            .load(
                                ContextCompat.getDrawable(
                                    itemView.context,
                                    R.drawable.pngwing_com
                                )
                            )
                            .into(itemView.emoji_one)
                    }
                    if (s == "Valorant") {
                        Glide.with(itemView)
                            .load(ContextCompat.getDrawable(itemView.context, R.drawable.valorant))
                            .into(itemView.emoji_one)
                    }
                    if (s == "PUBG") {
                        Glide.with(itemView)
                            .load(
                                ContextCompat.getDrawable(
                                    itemView.context,
                                    R.drawable.pubg_10201
                                )
                            )
                            .into(itemView.emoji_one)
                    }
                    if (s == "Diablo 4") {
                        Glide.with(itemView)
                            .load(ContextCompat.getDrawable(itemView.context, R.drawable.diablo4))
                            .into(itemView.emoji_one)
                    }
                }
                1 -> {
                    if (s == "CS:GO") {
                        Glide.with(itemView)
                            .load(ContextCompat.getDrawable(itemView.context, R.drawable.csgo))
                            .into(itemView.emoji_two)
                    }
                    if (s == "Overwatch") {
                        Glide.with(itemView)
                            .load(ContextCompat.getDrawable(itemView.context, R.drawable.overwatch))
                            .into(itemView.emoji_two)
                    }
                    if (s == "Dota 2") {
                        Glide.with(itemView)
                            .load(
                                ContextCompat.getDrawable(
                                    itemView.context,
                                    R.drawable.pngwing_com
                                )
                            )
                            .into(itemView.emoji_two)
                    }
                    if (s == "Valorant") {
                        Glide.with(itemView)
                            .load(ContextCompat.getDrawable(itemView.context, R.drawable.valorant))
                            .into(itemView.emoji_two)
                    }
                    if (s == "PUBG") {
                        Glide.with(itemView)
                            .load(
                                ContextCompat.getDrawable(
                                    itemView.context,
                                    R.drawable.pubg_10201
                                )
                            )
                            .into(itemView.emoji_two)
                    }
                    if (s == "Diablo 4") {
                        Glide.with(itemView)
                            .load(ContextCompat.getDrawable(itemView.context, R.drawable.diablo4))
                            .into(itemView.emoji_two)
                    }
                }
                2 -> {
                    if (s == "CS:GO") {
                        Glide.with(itemView)
                            .load(ContextCompat.getDrawable(itemView.context, R.drawable.csgo))
                            .into(itemView.emoji_three)
                    }
                    if (s == "Overwatch") {
                        Glide.with(itemView)
                            .load(ContextCompat.getDrawable(itemView.context, R.drawable.overwatch))
                            .into(itemView.emoji_three)
                    }
                    if (s == "Dota 2") {
                        Glide.with(itemView)
                            .load(
                                ContextCompat.getDrawable(
                                    itemView.context,
                                    R.drawable.pngwing_com
                                )
                            )
                            .into(itemView.emoji_three)
                    }
                    if (s == "Valorant") {
                        Glide.with(itemView)
                            .load(ContextCompat.getDrawable(itemView.context, R.drawable.valorant))
                            .into(itemView.emoji_three)
                    }
                    if (s == "PUBG") {
                        Glide.with(itemView)
                            .load(
                                ContextCompat.getDrawable(
                                    itemView.context,
                                    R.drawable.pubg_10201
                                )
                            )
                            .into(itemView.emoji_three)
                    }
                    if (s == "Diablo 4") {
                        Glide.with(itemView)
                            .load(ContextCompat.getDrawable(itemView.context, R.drawable.diablo4))
                            .into(itemView.emoji_three)
                    }
                }
            }
        }

//        event.types.forEachIndexed { index, eventType ->
//            when (index) {
//                0 -> {
//                    Glide.with(itemView)
//                        .load(
//                            ContextCompat.getDrawable(
//                                itemView.context,
//                                R.drawable.ic_emoji_party
//                            )
//                        )
//                        .into(itemView.emoji_one)
//                }
//                1 -> {
//                    Glide.with(itemView)
//                        .load(ContextCompat.getDrawable(itemView.context, eventType.resId))
//                        .into(itemView.emoji_two)
//                }
//                2 -> {
//                    Glide.with(itemView)
//                        .load(ContextCompat.getDrawable(itemView.context, eventType.resId))
//                        .into(itemView.emoji_three)
//                }
//            }
//        }

        Glide.with(itemView.context)
            .load(event.tempImage)
            .into(itemView.image_administrator1)
//        event.administrator?.let {
//            if (it.imageContentUid != null) {
//                Glide.with(itemView.context)
//                    .load(it.imageContentUid)
//                    .into(itemView.image_administrator1)
//            }
//            itemView.administrator_name.text = it.name
//
//            itemView.administrator.setOnClickListener { view ->
//                listener.onAdministratorClick(it.personUid)
//            }
//            itemView.image_administrator.setOnClickListener { view ->
//                listener.onAdministratorClick(it.personUid)
//            }
//        }

//        if (event.isOnline) {
//            itemView.text_location.text = itemView.context.getString(R.string.online)
//            itemView.icon_location.setImageResource(R.drawable.ic_online)
//        } else {
//            itemView.text_location.text =
//                itemView.context.getLocationName(event.xCoordinate, event.yCoordinate)
//                    ?: event.cityName
//            if (event.xCoordinate != null && event.xCoordinate > 0
//                && event.yCoordinate != null && event.yCoordinate > 0
//            ) {
//                itemView.text_location.setOnClickListener {
//                    listener.onLocationClick(event.xCoordinate, event.yCoordinate, event.name)
//                }
//            }
//        }

        itemView.button_report_event.setOnClickListener {
            listener.onReportClick()
        }
    }
}