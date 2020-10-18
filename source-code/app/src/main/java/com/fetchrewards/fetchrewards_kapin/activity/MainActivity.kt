package com.fetchrewards.fetchrewards_kapin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.fetchrewards.fetchrewards_kapin.R
import com.fetchrewards.fetchrewards_kapin.adapter.ParentRVAdapter
import com.fetchrewards.fetchrewards_kapin.models.Item
import com.fetchrewards.fetchrewards_kapin.viewmodel.DataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    private val tag = "@MainActivity"

    // Final list of items to be displayed
    var sortedHashMap: SortedMap<Int, MutableList<Item>> = sortedMapOf(compareBy { it })

    // Initialize view components
    private lateinit var parentRecyclerView: RecyclerView
    private lateinit var loader: LottieAnimationView
    private lateinit var errorTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e(tag, "Activity Created: MainActivity")

        parentRecyclerView = findViewById(R.id.parentRecyclerView)
        loader = findViewById(R.id.loader)
        errorTV = findViewById(R.id.errorMessage)

        // Initially show loader
        parentRecyclerView.visibility = View.GONE
        loader.visibility = View.VISIBLE
        CoroutineScope(IO).launch {
            // Fetching Data and Set RecyclerView Layout
            fetchDataAndInitRecyclerView()
        }
    }

    private fun fetchDataAndInitRecyclerView() {
        // Initializing the view model
        val dataViewModel: DataViewModel =
            ViewModelProviders.of(this).get(DataViewModel::class.java)
        dataViewModel.init()
        // Fetching data through network call
        dataViewModel.fetchDataFromServer()
        // Observing for changes to the data list
        CoroutineScope(Main).launch {
            dataViewModel.getData().observe(this@MainActivity, {
                val rawItemList: List<Item>? = dataViewModel.getData().value
                Log.e(tag, "List of items have been fetched")
                if (!rawItemList.isNullOrEmpty()) {
                    // Filtering and sorting the list as per requirements
                    filterAndSortItems(rawItemList)
                    // Setting recycler view layout
                    val adapter = ParentRVAdapter(this@MainActivity)
                    parentRecyclerView.layoutManager =
                        LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                    parentRecyclerView.adapter = adapter
                    // If successful, hide loader and show recycler view
                    // Delay for making animation look smooth
                    Handler().postDelayed({
                        loader.pauseAnimation()
                        loader.visibility = View.GONE
                        parentRecyclerView.visibility = View.VISIBLE
                    }, 1000)
                } else {
                    Log.e(tag, "No results have been fetched")
                    // In case there is no data to be displayed
                    Handler().postDelayed({
                        errorTV.visibility = View.VISIBLE
                    }, 3000)
                }

            })
        }
    }

    private fun filterAndSortItems(unFilteredItemList: List<Item>) {
        val filteredItemList: MutableList<Item> = mutableListOf()
        // Removing names with null or blank values
        for (item in unFilteredItemList) {
            if (!item.name.isNullOrBlank()) {
                filteredItemList.add(item)
            }
        }
        // Building the required HashMap
        // Adding to SortedHashMap (sorted by listId)
        for (item in filteredItemList) {
            val itemList: MutableList<Item> =
                sortedHashMap.getOrDefault(item.listId, mutableListOf())
            itemList.add(item)
            sortedHashMap[item.listId] = itemList
        }
        // Sorting the list under each key(listId) in the SortedHashMap
        for ((key, value) in sortedHashMap) {
            val sortedItemList = value.sortedWith(
                compareBy({ it.listId },
                    { it.name?.length },
                    { it.name })
            ) as MutableList<Item>
            sortedHashMap[key] = sortedItemList
        }
        Log.e(tag, "SortedHashMap is ready as per requirements")
    }
}