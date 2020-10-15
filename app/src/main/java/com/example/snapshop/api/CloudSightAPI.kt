package com.example.snapshop.api

import retrofit2.Call
import retrofit2.http.*

interface CloudSightAPI {
    @Headers("Authorization: CloudSight EAC4Y9ZLBFaGzaiM2IqPBg", "content-type: application/json")
    @POST("images")
    fun identify(@Body body: HashMap<String, Any>): Call<ImagePostResponse>
}