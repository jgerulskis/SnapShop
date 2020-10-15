package com.example.snapshop

import android.media.Image
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel

private const val TAG="ItemLogsViewModel"

class ItemLogsViewModel : ViewModel() {
    private var items: Array<Item> = arrayOf<Item>()

    private fun addItem(item: Item) {
        items = items.plus(item)
    }

    fun randomItems() {
        for(i in 1..10) {
            val name = "Camera"
            val result = "No Search Conducted"
            val item = Item(name, result)
            addItem(item)
        }
    }

    fun getItems() : Array<Item> {
        return items
    }
}