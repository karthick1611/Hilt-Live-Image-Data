package com.data.hilt_live.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.data.hilt_live.R
import com.data.hilt_live.model.Photo
import kotlinx.android.synthetic.main.gallery_list_item.view.*

class ImageAdapter(private val listener: ImageItemListener) : RecyclerView.Adapter<ImageViewHolder>() {

    interface ImageItemListener {
        fun onClickedImage(image: ArrayList<Photo>, position: Int)
    }

    private val items = ArrayList<Photo>()
    private lateinit var photo: Photo

    fun setItems(items: List<Photo>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_list_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        photo = items[position]
        val photo = items[position]

        Glide.with(holder.itemLayout).load(photo.src?.large)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
//            .apply(RequestOptions().centerCrop())
            .into(holder.image)
        holder.itemLayout.setOnClickListener { listener.onClickedImage(items, position) }
    }

}

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val itemLayout: ConstraintLayout = itemView.layout_images
    val image: ImageView = itemView.image_iv

}

