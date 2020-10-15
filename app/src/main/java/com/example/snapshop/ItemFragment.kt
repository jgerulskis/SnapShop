package com.example.snapshop

import android.graphics.Bitmap
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.snapshop.dummy.DummyContent
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

/**
 * A fragment representing a list of Items.
 */
private const val TAG = "ItemFragment"
class ItemFragment : Fragment() {
    private lateinit var itemRecyclerView: RecyclerView
    private var adapter: MyItemRecyclerViewAdapter? = null
    private var columnCount = 1
    private val itemName : TextView? = null
    private val itemPicture : ImageView? = null
    private val searchResult : TextView? = null

    private val itemLogsViewModel : ItemLogsViewModel by lazy {
        ViewModelProviders.of(this).get(ItemLogsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Creating Items Fragment")
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        itemRecyclerView = view.findViewById(R.id.list) as RecyclerView
        itemRecyclerView.layoutManager = LinearLayoutManager(context)
        updateUI()
        return view
    }

    private fun updateUI() {
        val items = itemLogsViewModel.getItems()
        adapter = MyItemRecyclerViewAdapter(items.asList())
        itemRecyclerView.adapter = adapter
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        const val ITEM_NAME = "item-name"
        const val ITEM_PICTURE = "item-picture"
        const val SEARCH_RESULT = "search-result"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    private inner class ItemsHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var item: Item
        private val name: TextView = itemView.findViewById(R.id.item_name)
        private val picture: ImageView = itemView.findViewById(R.id.item_picture)
        private val result: TextView = itemView.findViewById(R.id.search_results)

        fun bind(item: Item, position: Int) {
            this.item = item
            name.text = "Temp Name"
            Picasso.get().load("https://cdn.mos.cms.futurecdn.net/6t8Zh249QiFmVnkQdCCtHK.jpg").into(picture)
            result.text = "Temp Result"
        }
    }

    private inner class ItemLogsAdapter(var items: List<Item>) : RecyclerView.Adapter<ItemsHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsHolder {
            val view = layoutInflater.inflate(R.layout.fragment_item, parent, false)
            return ItemsHolder(view)
        }

        override fun getItemCount(): Int {
            return games.size
        }
    }
}