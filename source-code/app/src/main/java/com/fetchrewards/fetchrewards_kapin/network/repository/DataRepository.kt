package com.fetchrewards.fetchrewards_kapin.network.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.fetchrewards.fetchrewards_kapin.models.Item
import com.fetchrewards.fetchrewards_kapin.network.RetrofitService
import com.fetchrewards.fetchrewards_kapin.network.api.FetchHiringAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepository {
    private val tag = "@DataRepo"
    private var dataRepository: DataRepository? = null
    private var fetchHiringAPI: FetchHiringAPI? = null

    init {
        fetchHiringAPI = RetrofitService().createService(FetchHiringAPI::class.java)
    }

    fun getInstance(): DataRepository {
        return if (dataRepository != null) {
            dataRepository as DataRepository
        } else DataRepository()
    }

    fun fetchData(): MutableLiveData<List<Item>> {
        Log.e(tag, "Fetching data from server")
        val data: MutableLiveData<List<Item>> = MutableLiveData()
        val call: Call<List<Item>>? =
            fetchHiringAPI?.fetchData()
        call?.enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful) {
                    Log.e(tag, "Data fetch successful")
                    data.value = response.body()
                } else Log.e(
                    tag,
                    "ERROR(fetchData)-> responseBody: ${response.body()} ; responseCode: ${response.code()}"
                )
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                Log.e(tag, "Failure(fetchData-Repository): ${t.localizedMessage}")
            }
        })
        return data
    }
}