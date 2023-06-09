package com.singlelab.gpf.ui.view.image

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.singlelab.gpf.R
import kotlinx.android.synthetic.main.item_adding_image.view.*


class ImageViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_adding_image, parent, false)) {

    fun bind(bitmap: Bitmap?, position: Int, listener: OnImageClickListener?) {
        if (bitmap == null) {
            itemView.image_event.scaleType = ImageView.ScaleType.CENTER
            itemView.image_event.setImageResource(R.drawable.ic_upload)
            itemView.setOnClickListener {
                listener?.onClickNewImage()
            }
        } else {
            itemView.image_event.scaleType = ImageView.ScaleType.CENTER_CROP
            itemView.image_event.setImageBitmap(bitmap)
            itemView.setOnClickListener {
                listener?.onClickImage(position)
            }
        }
    }
}