package com.fetchrewards.fetchrewards_kapin.network.api

import com.fetchrewards.fetchrewards_kapin.models.Item
import retrofit2.Call
import retrofit2.http.GET

interface FetchHiringAPI {
    @GET("hiring.json")
    fun fetchData(): Call<List<Item>>
}