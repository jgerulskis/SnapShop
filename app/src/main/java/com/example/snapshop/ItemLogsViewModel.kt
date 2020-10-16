package com.example.snapshop

import android.content.Context
import android.media.Image
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.snapshop.api.ImagePostResponse

private const val TAG="ItemLogsViewModel"

class ItemLogsViewModel : ViewModel() {
    private var items: Array<ImagePostResponse> = arrayOf<ImagePostResponse>()
    private var isInitialized: Boolean = false

    private fun addItem(item: ImagePostResponse) {
        items = items.plus(item)
    }

    fun getItems() : Array<ImagePostResponse> {
        return items
    }

    fun setItems(items: Array<ImagePostResponse>) {
        if (!isInitialized) this.items = items
        isInitialized = true
    }
}