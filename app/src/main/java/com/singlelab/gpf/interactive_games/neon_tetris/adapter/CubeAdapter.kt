package com.singlelab.gpf.interactive_games.neon_tetris.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import com.singlelab.gpf.interactive_games.neon_tetris.piece.Piece
import com.singlelab.gpf.R
import java.util.*


class CubeAdapter(private val context: Context, itemList: ArrayList<Piece>, block: Int, colSize: Int? = null) : BaseAdapter() {

    private var mInflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var mBlock: String
    private lateinit var background: ImageView
    private var mAxe: Int = 0
    private val items: ArrayList<Piece> = itemList
    private val piece = block
    private var columnSize: Int? = colSize

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView

        if (view == null) {
            view = mInflater.inflate(piece, null)
        }

        if(columnSize != null){
            background = view!!.findViewById(R.id.template_swipe)
            val params = LinearLayout.LayoutParams(columnSize!!, columnSize!!)
            background.layoutParams = params
        } else {
            background = view!!.findViewById(R.id.template)
        }

        mBlock = items[position].block
        mAxe = items[position].axe

        view.visibility = View.GONE

        if (mBlock == "square"){
            square(position, view)
        }
        if (mBlock == "zi"){
            zi(position, view)
        }
        if (mBlock == "ti"){
            ti(position, view)
        }
        if (mBlock == "el"){
            el(position, view)
        }
        if (mBlock == "line"){
            line(position, view)
        }
        if (mBlock == "ji"){
            ji(position, view)
        }
        if (mBlock == "es"){
            es(position, view)
        }
        if (mBlock == "you"){
            you(position, view)
        }
        if (mBlock == "em"){
            em(position, view)
        }
        return view
    }

    override fun getCount(): Int {
        return items.size
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getItem(position: Int): Any? {
        return items[position]
    }

    private fun square(position: Int, view: View){

        if (position == mAxe) {
            view.visibility = View.VISIBLE
            background.setBackgroundResource(R.drawable.cube_yellow)
        }
    }

    private fun zi(position: Int, view: View){

        if (position == mAxe) {
            view.visibility = View.VISIBLE
            background.setBackgroundResource(R.drawable.cube_purple)
        }
    }

    private fun ti(position: Int, view: View){

        if (position == mAxe) {
            view.visibility = View.VISIBLE
            background.setBackgroundResource(R.drawable.cube_green)
        }
    }

    private fun line(position: Int, view: View){

        if (position == mAxe) {
            view.visibility = View.VISIBLE
            background.setBackgroundResource(R.drawable.cube_orange)
        }
    }

    private fun el(position: Int, view: View){

        if (position == mAxe) {
            view.visibility = View.VISIBLE
            background.setBackgroundResource(R.drawable.cube_blue)
        }
    }

    private fun ji(position: Int, view: View){

        if (position == mAxe) {
            view.visibility = View.VISIBLE
            background.setBackgroundResource(R.drawable.cube_rose)
        }
    }

    private fun es(position: Int, view: View){

        if (position == mAxe) {
            view.visibility = View.VISIBLE
            background.setBackgroundResource(R.drawable.cube_red)
        }
    }

    private fun you(position: Int, view: View){

        if (position == mAxe) {
            view.visibility = View.VISIBLE
            background.setBackgroundResource(R.drawable.cube_red)
        }
    }

    private fun em(position: Int, view: View){

        if (position == mAxe) {
            view.visibility = View.VISIBLE
            background.setBackgroundResource(R.drawable.cube_blue)
        }
    }

}