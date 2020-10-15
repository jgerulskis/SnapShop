package com.example.snapshop

import android.graphics.Bitmap
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.snapshop.dummy.DummyContent.DummyItem


class MyItemRecyclerViewAdapter(
    private val values: List<Item>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nameView.text = item.name
        var imageBitmap = "laptop.jpg" as Bitmap
        holder.pictureView.setImageBitmap(imageBitmap)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.item_name)
        val pictureView: ImageView = view.findViewById(R.id.item_picture)

//        override fun toString(): String {
//            return super.toString() + " '" + contentView.text + "'"
//        }
    }
}