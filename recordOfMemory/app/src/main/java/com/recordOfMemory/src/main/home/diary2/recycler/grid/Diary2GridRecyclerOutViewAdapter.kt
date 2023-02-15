package com.recordOfMemory.src.main.home.diary2.recycler.grid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recordOfMemory.R
import com.recordOfMemory.src.main.home.diary2.Diary2Fragment
import com.recordOfMemory.src.main.home.diary2.recycler.grid.models.Diary2GridOutViewModel

class Diary2GridRecyclerOutViewAdapter(var items: Diary2Fragment.itemListAdapterToList, val itemList: ArrayList<Diary2GridOutViewModel>)
    : RecyclerView.Adapter<Diary2GridRecyclerOutViewHolder>() { init {
    setHasStableIds(true)
    }
//    }, Filterable{

    lateinit var diary2GridRecyclerOutViewHolder : Diary2GridRecyclerOutViewHolder
//    private var unFilteredList = itemList
//    private var filteredList = itemList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Diary2GridRecyclerOutViewHolder {
        diary2GridRecyclerOutViewHolder = Diary2GridRecyclerOutViewHolder(
            parent.context,
            items,
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_diary2_grid_out_view, parent, false)
        )

        return diary2GridRecyclerOutViewHolder
    }

    override fun onBindViewHolder(holder: Diary2GridRecyclerOutViewHolder, position: Int) {
        holder.bindWithView(itemList[position])
//        holder.itemView.setOnClickListener {
//            val item = filteredList[position]
//            items.getItemId(item)
//        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}