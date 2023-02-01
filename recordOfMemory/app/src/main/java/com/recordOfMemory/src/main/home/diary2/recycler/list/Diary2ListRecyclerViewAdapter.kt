package com.recordOfMemory.src.main.home.diary2.recycler.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recordOfMemory.R
import com.recordOfMemory.src.main.home.diary2.Diary2Fragment
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetDiary2Response

class Diary2ListRecyclerViewAdapter(var items: Diary2Fragment.itemListAdapterToList, val itemList: ArrayList<GetDiary2Response>)
    : RecyclerView.Adapter<Diary2ListRecyclerViewHolder>() {
//    }, Filterable{

    lateinit var diary2ListRecyclerViewHolder : Diary2ListRecyclerViewHolder
//    private var unFilteredList = itemList
//    private var filteredList = itemList


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

//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val charString = constraint.toString()
//                filteredList = if (charString.isEmpty()) {
//                    unFilteredList
//                } else {
//                    val filteringList = ArrayList<GetDiary2Response>()
//                    for (item in unFilteredList) {
//                        if (item.category == charString) filteringList.add(item)
//                    }
//                    filteringList
//                }
//                val filterResults = FilterResults()
//                filterResults.values = filteredList
//
//                return filterResults
//            }
//
//            override fun publishResults(constraint: CharSequence, results: FilterResults) {
//                filteredList = results.values as ArrayList<*>
//                notifyDataSetChanged()
//            }
//        }
//
//    }
}