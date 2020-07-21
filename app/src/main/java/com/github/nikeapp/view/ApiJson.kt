package com.github.nikeapp.view

import com.github.nikeapp.model.Items
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface ApiJson {
    @GET
    fun profilePicture(@Url url: String?): Call<Items>
}