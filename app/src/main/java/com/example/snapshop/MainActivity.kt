package com.example.snapshop

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.example.snapshop.api.ImageIdentifier
import com.example.snapshop.api.ImagePostResponse
import com.example.snapshop.ui.main.SectionsPagerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import java.io.ByteArrayOutputStream


private const val TAG="Main"
private const val REQUEST_PHOTO = 2

class MainActivity : AppCompatActivity() {

    lateinit var imageIdentifier: ImageIdentifier
    lateinit var storage: Storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        imageIdentifier = ImageIdentifier()
        storage = Storage(this.applicationContext)

        fab.setOnClickListener { view ->
            dispatchTakePictureIntent()
        }

    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_PHOTO)
        } catch (e: ActivityNotFoundException) {
            Log.d(TAG, "No camera app")
        }

       // viewPager.setCurrentItem()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PHOTO && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val byteArrayOutputStream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
            val encoded: String = Base64.encodeToString(byteArray, Base64.DEFAULT)
            val data: LiveData<ImagePostResponse> = imageIdentifier.identify("data:image/png;base64,$encoded")
            data.observe(
                this,
                Observer { response ->
                    if (response != null) {
                        storage.saveResult(response)
                        Log.d(TAG, "Main saving ${response.toString()}")
                    } else {
                        Log.d(TAG, "Main is not saving, got null")
                    }
                }
            )
        }
    }

}