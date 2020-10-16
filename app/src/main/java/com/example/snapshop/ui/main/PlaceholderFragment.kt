package com.example.snapshop.ui.main

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.snapshop.R
import com.example.snapshop.Storage
import com.example.snapshop.api.ImageIdentifier
import com.example.snapshop.api.ImagePostResponse
import java.io.ByteArrayOutputStream


/**
 * A placeholder fragment containing a simple view.
 */
private const val REQUEST_PHOTO = 2
private const val TAG="Placeholder"
class PlaceholderFragment : Fragment() {
    private lateinit var storage: Storage
    private lateinit var captureImage: Button
    private lateinit var imageView: ImageView
    private lateinit var imageIdentifier: ImageIdentifier
    private lateinit var toast: Toast

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        imageIdentifier = ImageIdentifier()
        storage = activity?.applicationContext?.let { Storage(it) }!!
        toast = Toast.makeText(
            activity!!.applicationContext,
            "Image processed, go view in results",
            Toast.LENGTH_LONG
        )

        imageView = root.findViewById(R.id.background)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        Glide.with(this).load(R.drawable.background).into(imageView)

        captureImage = root.findViewById<Button>(R.id.capture_image)
        captureImage.setOnClickListener {
            dispatchTakePictureIntent()
        }
        return root
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_PHOTO)
        } catch (e: ActivityNotFoundException) {
            Log.d(TAG, "No camera app")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PHOTO && resultCode == AppCompatActivity.RESULT_OK) {
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
                        toast.show()
                        Log.d(TAG, "Main saving $response")
                    } else {
                        Log.d(TAG, "Main is not saving, got null")
                    }
                }
            )
        }
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}