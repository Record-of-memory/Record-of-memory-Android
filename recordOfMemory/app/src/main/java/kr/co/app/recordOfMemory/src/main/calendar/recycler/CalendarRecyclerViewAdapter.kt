
package kr.co.app.recordOfMemory.src.main.calendar.recycler


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.app.recordOfMemory.R

import kr.co.app.recordOfMemory.src.main.calendar.CalendarFragment
import kr.co.app.recordOfMemory.src.main.calendar.retrofit.models.Record

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