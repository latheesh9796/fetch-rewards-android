package com.fetchrewards.fetchrewards_kapin.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    private val baseURL = "https://fetch-hiring.s3.amazonaws.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}