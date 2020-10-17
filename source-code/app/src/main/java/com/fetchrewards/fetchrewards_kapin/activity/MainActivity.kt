package com.fetchrewards.fetchrewards_kapin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.fetchrewards.fetchrewards_kapin.R
import com.fetchrewards.fetchrewards_kapin.adapter.ItemRVAdapter
import com.fetchrewards.fetchrewards_kapin.models.Item
import com.fetchrewards.fetchrewards_kapin.viewmodel.DataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val tag = "@MainActivity"

    // Final list of items to be displayed
    var sortedItemList: MutableList<Item> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e(tag, "Activity Created: MainActivity")
        val shimmerFrameLayout: ShimmerFrameLayout = findViewById(R.id.shimmerLayout)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Start loading animation
        recyclerView.visibility = View.GONE
        startShimmerEffect(shimmerFrameLayout)
        CoroutineScope(IO).launch {
            // Set RecyclerView Layout
            initRecyclerView(recyclerView, shimmerFrameLayout)
        }
    }

    override fun onPause() {
        super.onPause()
        val shimmerLayout: ShimmerFrameLayout = findViewById(R.id.shimmerLayout)
        shimmerLayout.stopShimmerAnimation()
    }

    override fun onResume() {
        super.onResume()
        val shimmerLayout: ShimmerFrameLayout = findViewById(R.id.shimmerLayout)
        shimmerLayout.startShimmerAnimation()
    }

    private fun initRecyclerView(
        recyclerView: RecyclerView,
        shimmerFrameLayout: ShimmerFrameLayout
    ) {
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
                if (rawItemList != null) {
                    // Filtering and sorting the list as per requirements
                    filterAndSortItems(rawItemList)
                    //
                    val adapter = ItemRVAdapter(this@MainActivity)
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerView.adapter = adapter
                } else {
                    Log.e(tag, "No results have been fetched")
                }
                stopShimmerEffect(shimmerFrameLayout)
                recyclerView.visibility = View.VISIBLE
            })
        }
    }

    private fun filterAndSortItems(unFilteredItemList: List<Item>) {
        // Clearing before refreshing the list
        // sortedItemList.clear()
        // Removing names with null or blank values
        for (item in unFilteredItemList) {
            if (!item.name.isNullOrBlank()) {
                sortedItemList.add(item)
            }
        }
        // Sorting the remaining list
        sortedItemList = sortedItemList.sortedWith(
            compareBy({ it.listId },
                { it.name?.length },
                { it.name })
        ) as MutableList<Item>
    }

    private fun startShimmerEffect(shimmerFrameLayout: ShimmerFrameLayout) {
        shimmerFrameLayout.visibility = View.VISIBLE
        shimmerFrameLayout.startShimmerAnimation()
    }

    private fun stopShimmerEffect(shimmerFrameLayout: ShimmerFrameLayout) {
        shimmerFrameLayout.visibility = View.GONE
        shimmerFrameLayout.stopShimmerAnimation()
    }
}