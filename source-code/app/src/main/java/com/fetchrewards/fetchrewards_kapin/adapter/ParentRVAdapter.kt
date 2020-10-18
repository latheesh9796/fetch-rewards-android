package com.fetchrewards.fetchrewards_kapin.adapter

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.fetchrewards.fetchrewards_kapin.R
import com.fetchrewards.fetchrewards_kapin.activity.MainActivity
import com.fetchrewards.fetchrewards_kapin.models.Item

class ParentRVAdapter(private val context: MainActivity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var listIdValues: List<Int>

    class ParentViewHolder(ParentView: View) : RecyclerView.ViewHolder(ParentView) {
        val listIDTV: TextView = ParentView.findViewById(R.id.listID)
        val expandedView: LinearLayout = ParentView.findViewById(R.id.expandedView)
        val childRecyclerView: RecyclerView = ParentView.findViewById(R.id.childRecyclerView)
        val dropDownArrow: LottieAnimationView = ParentView.findViewById(R.id.dropDownArrow)
        var isExpanded: Boolean = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ParentViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.parent_recycler_view_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Set ListID Header
        val listIdString = "List ID : ${listIdValues[position]}"
        (holder as ParentViewHolder).listIDTV.text = listIdString
        // Show/Hide Expanded View
        holder.listIDTV.setOnClickListener {
            holder.isExpanded = !holder.isExpanded
            TransitionManager.beginDelayedTransition(holder.expandedView, AutoTransition())
            toggleExpandedView(holder)
        }
        // Child RecyclerView Items
        val childAdapter =
            ChildRVAdapter(context, context.sortedHashMap[listIdValues[position]] as List<Item>)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.childRecyclerView.layoutManager = linearLayoutManager
        holder.childRecyclerView.adapter = childAdapter
    }

    override fun getItemCount(): Int {
        listIdValues = ArrayList(context.sortedHashMap.keys)
        return listIdValues.size
    }

    private fun toggleExpandedView(holder: ParentViewHolder) {
        when (holder.isExpanded) {
            true -> {
                // Animate arrow -> expand list
                holder.dropDownArrow.speed = 3F
                holder.dropDownArrow.playAnimation()
                holder.expandedView.visibility = View.VISIBLE
            }
            false -> {
                // Animate arrow -> collapse list
                holder.dropDownArrow.speed = -2F
                holder.dropDownArrow.playAnimation()
                holder.expandedView.visibility = View.GONE
            }
        }
    }

}