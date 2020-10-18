package com.fetchrewards.fetchrewards_kapin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fetchrewards.fetchrewards_kapin.R
import com.fetchrewards.fetchrewards_kapin.activity.MainActivity
import com.fetchrewards.fetchrewards_kapin.models.Item

class ChildRVAdapter(private val context: MainActivity, private val itemList: List<Item>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ListItemObjectViewHolder(ListItemObjectView: View) :
        RecyclerView.ViewHolder(ListItemObjectView) {
        val nameTV: TextView = ListItemObjectView.findViewById(R.id.name)
        val idTV: TextView = ListItemObjectView.findViewById(R.id.id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListItemObjectViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.child_recycler_view_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ListItemObjectViewHolder).nameTV.text = itemList[position].name
        holder.idTV.text = itemList[position].id.toString()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}