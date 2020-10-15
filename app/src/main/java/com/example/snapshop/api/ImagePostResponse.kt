package com.example.snapshop.api

import com.google.gson.annotations.SerializedName

data class ImagePostResponse(
    @SerializedName("name") var name: String = "",
    @SerializedName("url") var url: String = "",
    @SerializedName("token") var token: String = ""
) {
}