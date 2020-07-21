package com.github.retrofittorecyclerviewkotlin

import com.github.nikeapp.model.Items
import okhttp3.internal.concurrent.Task
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("define")

    @Headers("x-rapidapi-host: mashape-community-urban-dictionary.p.rapidapi.com", "x-rapidapi-key: 62e2eab0d7mshe0129a42c2d722ap119deejsn4d4b8a17a024")
    fun getRecipes(@Query("term") query: String): Call<Items>
}

