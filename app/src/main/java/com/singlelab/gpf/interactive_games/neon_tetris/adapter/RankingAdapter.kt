package com.singlelab.gpf.interactive_games.neon_tetris.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.singlelab.gpf.R

class RankingAdapter(private val context: Context, itemList: List<Pair<Int, String>>) : BaseAdapter()  {

    private var mInflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val items: List<Pair<Int, String>> = itemList
    private val ranking_fields = R.layout.ranking_field

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view = convertView
        if (view == null) {
            view = mInflater.inflate(ranking_fields, null)
        }
        val username: TextView = view!!.findViewById(R.id.username_field)
        val highScore: TextView = view.findViewById(R.id.score_field)
        val rank: TextView = view.findViewById(R.id.rank_field)

        val info = items[position]

        highScore.text = info.first.toString()
        username.text = info.second
        rank.text = (position + 1).toString()

        return view
    }

    override fun getItem(position: Int): Any? {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

}