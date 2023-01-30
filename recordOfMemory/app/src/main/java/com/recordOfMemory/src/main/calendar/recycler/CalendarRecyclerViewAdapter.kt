package com.recordOfMemory.src.main.calendar.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recordOfMemory.R
import com.recordOfMemory.src.main.calendar.CalendarFragment
import com.recordOfMemory.src.main.home.diary2.recycler.Diary2ListRecyclerViewHolder
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetDiary2Response

class CalendarRecyclerViewAdapter(var items: CalendarFragment.itemListAdapterToList, val itemList: ArrayList<GetDiary2Response>)
    : RecyclerView.Adapter<Diary2ListRecyclerViewHolder>() {
    lateinit var diary2ListRecyclerViewHolder : Diary2ListRecyclerViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Diary2ListRecyclerViewHolder {
        diary2ListRecyclerViewHolder = Diary2ListRecyclerViewHolder(
            parent.context,
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_diary2_list, parent, false)
        )
        return diary2ListRecyclerViewHolder
    }

    override fun onBindViewHolder(holder: Diary2ListRecyclerViewHolder, position: Int) {
        holder.bindWithView(itemList[position])
        holder.itemView.setOnClickListener {
            val item = itemList[position]
            items.getItemId(item)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}