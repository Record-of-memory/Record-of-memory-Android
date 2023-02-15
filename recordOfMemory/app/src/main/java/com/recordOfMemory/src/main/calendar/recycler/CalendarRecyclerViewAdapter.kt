
package com.recordOfMemory.src.main.calendar.recycler


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.recordOfMemory.R

import com.recordOfMemory.src.main.calendar.CalendarFragment
import com.recordOfMemory.src.main.calendar.retrofit.models.Information
import com.recordOfMemory.src.main.calendar.retrofit.models.Record
import com.recordOfMemory.src.main.home.diary2.recycler.list.Diary2ListRecyclerViewHolder

import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetMemberRecordResponse

class CalendarRecyclerViewAdapter(var items: CalendarFragment.itemListAdapterToList, itemList: ArrayList<Record>)
    : RecyclerView.Adapter<CalendarRecyclerViewHolder>() {
    lateinit var diary2ListRecyclerViewHolder : CalendarRecyclerViewHolder
    val itemList = itemList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarRecyclerViewHolder {
        diary2ListRecyclerViewHolder = CalendarRecyclerViewHolder(
            parent.context,
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_diary2_list, parent, false)
        )
        return diary2ListRecyclerViewHolder
    }

    override fun onBindViewHolder(holder: CalendarRecyclerViewHolder, position: Int) {
        holder.bindWithView(itemList[position])
        holder.itemView.setOnClickListener {
            val item = itemList[position]
            items.getItemId(item)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun reload(list : ArrayList<Record>) {
//        this.itemList.clear()
//        this.itemList.addAll(list)
//        notifyDataSetChanged()
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun loadMore(list : ArrayList<Record>) {
//        this.itemList.addAll(list)
//        notifyItemRangeChanged(this.itemList.size - list.size + 1, list.size)
//    }
}