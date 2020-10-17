package com.fetchrewards.fetchrewards_kapin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fetchrewards.fetchrewards_kapin.models.Item
import com.fetchrewards.fetchrewards_kapin.network.repository.DataRepository

class DataViewModel: ViewModel() {
    private var data: MutableLiveData<List<Item>>? = null
    private var dataRepository: DataRepository? = null

    fun init() {
        if (dataRepository != null) {
            return
        }
        dataRepository = DataRepository().getInstance()
    }

    fun fetchDataFromServer() {
        data = dataRepository?.fetchData()
    }

    fun getData(): LiveData<List<Item>> {
        return data as LiveData<List<Item>>
    }
}