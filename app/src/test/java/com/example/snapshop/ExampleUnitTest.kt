package com.example.snapshop

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.snapshop.api.ImageIdentifier
import com.example.snapshop.api.ImagePostResponse
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private var imageIdentifier: ImageIdentifier = ImageIdentifier()
    private val imageExample = "iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4\n" +
            "  //8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg"

    @Test
    fun canQueryAPI() {
        val data: LiveData<ImagePostResponse> = imageIdentifier.identify(imageExample)
        assertTrue(data != null)
    }

    @Test
    fun canQueryStorage() {
        assertTrue(true)
    }
}