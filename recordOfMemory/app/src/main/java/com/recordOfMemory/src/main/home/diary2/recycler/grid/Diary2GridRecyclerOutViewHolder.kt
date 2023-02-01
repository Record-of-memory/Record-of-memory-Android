package com.recordOfMemory.src.main.home.diary2.recycler.grid

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recordOfMemory.R
import com.recordOfMemory.src.main.home.diary2.Diary2Fragment
import com.recordOfMemory.src.main.home.diary2.recycler.grid.models.Diary2GridOutViewModel

class Diary2GridRecyclerOutViewHolder(val context: Context, val items: Diary2Fragment.itemListAdapterToList, itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    val itemWriter = itemView.findViewById<TextView>(R.id.item_diary2_grid_out_tv_writer)
    val itemRecyclerView = itemView.findViewById<RecyclerView>(R.id.item_diary2_grid_out_recycler)

    fun bindWithView(item: Diary2GridOutViewModel) {
        val diary2LayoutManager = GridLayoutManager(context, 3)

        itemWriter.text = item.writer
        itemRecyclerView.layoutManager = diary2LayoutManager
        itemRecyclerView.adapter = Diary2GridRecyclerInViewAdapter(items, item.innerList)

//        Glide.with(itemView).load(item.imgUrl)
//            .into(itemImg as ImageView)
//        itemTitle.text = item.title
//        itemContent.text = item.content
//        itemWriter.text = item.writer
//        itemDate.text = item.date
    }

}