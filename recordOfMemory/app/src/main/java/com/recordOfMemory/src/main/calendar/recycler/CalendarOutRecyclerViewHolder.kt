package com.recordOfMemory.src.main.calendar.recycler

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recordOfMemory.R
import com.recordOfMemory.src.main.calendar.CalendarFragment
import com.recordOfMemory.src.main.calendar.retrofit.models.Information

class CalendarOutRecyclerViewHolder(val items: CalendarFragment.itemListAdapterToList, val context: Context, itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    val itemRecyclerView = itemView.findViewById<RecyclerView>(R.id.item_calendar_recyclerview)
    val itemTitle = itemView.findViewById<TextView>(R.id.item_calendar_title)


    fun bindWithView(item: Information) {
        val calendarLayoutManager = LinearLayoutManager(context)

        itemTitle.text = item.diaryName

        itemRecyclerView.layoutManager = calendarLayoutManager
        itemRecyclerView.adapter = CalendarRecyclerViewAdapter(items, item.records)
    }

}