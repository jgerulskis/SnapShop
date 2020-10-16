package com.example.snapshop

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.snapshop.api.ImagePostResponse
import com.squareup.picasso.Picasso
import androidx.core.content.ContextCompat.startActivity

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
        val name = if (item.name != "") item.name else "Couldn't identify image"
        holder.nameView.text = name
        if (item.name != "") {
            holder.container.setOnClickListener {
                val query = item.name.replace(" ", "+")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.com/s?k=$query"))
                holder.itemView.context.startActivity(intent)
            }
        }
        //set pictureView dynamically
        Picasso.get().load(item.url).into(holder.pictureView)
    //    holder.resultView.text = item.result
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.item_name)
        val pictureView: ImageView = view.findViewById(R.id.item_picture)
        val container: RelativeLayout = itemView.findViewById(R.id.item_container)
//        override fun toString(): String {
//            return super.toString() + " '" + contentView.text + "'"
//        }
    }
}