package com.recordOfMemory.src.main.home.diary2.recycler

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.lang.Integer.parseInt

class Diary2RecyclerVIewAdapter(var items: HomeFragment.itemListAdapterToList, itemList: ArrayList<ItemData>)
    : RecyclerView.Adapter<HomeRecyclerViewHolder>(), Filterable{

    lateinit var homeRecyclerViewHolder : HomeRecyclerViewHolder
    private var unFilteredList = itemList
    private var filteredList = itemList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        homeRecyclerViewHolder = HomeRecyclerViewHolder(
            parent.context,
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_search, parent, false)
        )
        return homeRecyclerViewHolder
    }

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        holder.bindWithView(filteredList[position])
        holder.itemView.setOnClickListener {
            val item = filteredList[position]
            items.getItemId(item)
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                filteredList = if (charString.isEmpty()) {
                    unFilteredList
                } else {
                    val filteringList = ArrayList<ItemData>()
                    for (item in unFilteredList) {
                        if (item.category == charString) filteringList.add(item)
                    }
                    filteringList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList

                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                filteredList = results.values as ArrayList<ItemData>
                notifyDataSetChanged()
            }
        }

    }
}