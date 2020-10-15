package com.example.snapshop

import android.content.Context
import android.content.SharedPreferences
import com.example.snapshop.api.ImagePostResponse
import com.google.gson.Gson

class Storage(context: Context) {
    private val name = "past_results"
    private val resultsKey = "results"
    private var sharedPreferences: SharedPreferences
    private var results: MutableList<ImagePostResponse>?

    init {
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        results = getResultsFromStorage().toMutableList()
    }

    private fun getResultsFromStorage(): Array<ImagePostResponse> {
        val rawResults = sharedPreferences.getString(resultsKey, null)
        if (rawResults != null) {
            val gson = Gson()
            return gson.fromJson(rawResults, Array<ImagePostResponse>::class.java)
        }
        return arrayOf()
    }

    fun saveResult(result: ImagePostResponse) {
        results?.add(result)
        val gson = Gson()
        val editor = sharedPreferences.edit()
        editor.putString(resultsKey, gson.toJson(results))
        editor.apply()
    }

    fun getResults(): MutableList<ImagePostResponse>? {
        return results
    }
}