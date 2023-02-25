package com.recordOfMemory.src.main.home.diary2.recycler.grid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recordOfMemory.R
import com.recordOfMemory.src.main.home.diary2.Diary2Fragment
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GridRecord

class Diary2GridRecyclerInViewAdapter(var items: Diary2Fragment.itemListAdapterToList, val itemList: ArrayList<GridRecord>)
    : RecyclerView.Adapter<Diary2GridRecyclerInViewHolder>() { init {
    setHasStableIds(true)
    }
    lateinit var diary2GridRecyclerInViewHolder : Diary2GridRecyclerInViewHolder


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Diary2GridRecyclerInViewHolder {
        diary2GridRecyclerInViewHolder = Diary2GridRecyclerInViewHolder(
            parent.context,
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_diary2_grid_in_view, parent, false)
        )
        return diary2GridRecyclerInViewHolder
    }

    override fun onBindViewHolder(holder: Diary2GridRecyclerInViewHolder, position: Int) {
        println(itemList[position])
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