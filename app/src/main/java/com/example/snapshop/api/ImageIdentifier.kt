package com.example.snapshop.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val TAG = "ImageIdentifier"

class ImageIdentifier {
    private val cloudSightAPI: CloudSightAPI

    init {
        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggingInterceptor)
        val baseURL = "https://api.cloudsight.ai/v1/"
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        cloudSightAPI = retrofit.create(CloudSightAPI::class.java)
    }

    fun identify(remoteImageUrl: String): LiveData<ImagePostResponse> {
        val responseLiveData: MutableLiveData<ImagePostResponse> = MutableLiveData()
        val imageIdentificationPost: Call<ImagePostResponse> = cloudSightAPI.identify(hashMapOf("image" to remoteImageUrl))
        imageIdentificationPost.enqueue(object: Callback<ImagePostResponse> {
            override fun onFailure(call: Call<ImagePostResponse>, t: Throwable) {
                Log.d(TAG, "Request failed", t)
            }

            override fun onResponse(
                call: Call<ImagePostResponse>,
                response: Response<ImagePostResponse>
            ) {
                Log.d(TAG, "Request successful! ${response.body()}")
                responseLiveData.value = response.body()
            }
        })

        return responseLiveData
    }
}