package com.fetchrewards.fetchrewards_kapin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fetchrewards.fetchrewards_kapin.R
import com.fetchrewards.fetchrewards_kapin.activity.MainActivity

class ItemRVAdapter(private val context: MainActivity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ListItemViewHolder(ListItemView: View) : RecyclerView.ViewHolder(ListItemView) {
        val nameTV: TextView = ListItemView.findViewById(R.id.name)
        val listIdTV: TextView = ListItemView.findViewById(R.id.listID)
        val idTV: TextView = ListItemView.findViewById(R.id.id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListItemViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ListItemViewHolder).listIdTV.text =
            context.sortedItemList[position].listId.toString()
        holder.nameTV.text = context.sortedItemList[position].name
        holder.idTV.text = context.sortedItemList[position].id.toString()
    }

    override fun getItemCount(): Int {
        return context.sortedItemList.size
    }
}