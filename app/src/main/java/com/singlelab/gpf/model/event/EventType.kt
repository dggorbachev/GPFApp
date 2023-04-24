package com.singlelab.gpf.model.event

import com.singlelab.gpf.R

enum class EventType(
    val id: Int,
    val resId: Int,
    val titleRes: Int,
    val colorRes: Int = R.color.colorParty
) {
    PARTY(0, R.drawable.pngwing_com, R.string.dota_2, R.color.colorParty),
    CSGO(1, R.drawable.csgo, R.string.CSGO),
    SPORT(2, R.drawable.overwatch, R.string.Overwatch),
    NATURE(3, R.drawable.valorant, R.string.Valorant),
    COMMUNICATION(4, R.drawable.pubg_10201, R.string.PUBG),
    GAME(5, R.drawable.diablo4, R.string.Diablo_4),
    STUDY(6, R.drawable.ic_emoji_book, R.string.study),
    FOOD(7, R.drawable.ic_emoji_food, R.string.food),
    CONCERT(8, R.drawable.ic_emoji_speech, R.string.concert),
    TRAVEL(9, R.drawable.ic_emoji_travel, R.string.travel);

    companion object {
        fun findById(id: Int): EventType {
            return values().find {
                it.id == id
            } ?: COMMUNICATION
        }
    }

}