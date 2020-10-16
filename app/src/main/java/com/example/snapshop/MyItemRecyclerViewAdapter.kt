package com.example.snapshop

import android.graphics.Bitmap
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.snapshop.api.ImagePostResponse

import com.example.snapshop.dummy.DummyContent.DummyItem
import com.squareup.picasso.Picasso


class MyItemRecyclerViewAdapter(
    private val values: List<ImagePostResponse>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        //change item name from new search
        holder.nameView.text = item.name
        //set pictureView dynamically
        Picasso.get().load(item.url).into(holder.pictureView)
    //    holder.resultView.text = item.result
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.item_name)
        val pictureView: ImageView = view.findViewById(R.id.item_picture)
        val resultView: TextView = view.findViewById(R.id.search_results)
//        override fun toString(): String {
//            return super.toString() + " '" + contentView.text + "'"
//        }
    }
}