package com.recordOfMemory.src.main.home.diary2.search.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.recordOfMemory.R
import com.recordOfMemory.src.main.home.diary2.recycler.list.Diary2ListRecyclerViewHolder
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetMemberRecordResponse
import com.recordOfMemory.src.main.home.diary2.search.Diary2SearchFragment
import com.recordOfMemory.src.main.home.diary2.search.retrofit.Diary2SearchInterface

class Diary2SearchRecyclerViewAdapter(val diary2SearchInterface: Diary2SearchInterface, var items: Diary2SearchFragment.itemListAdapterToList, itemList: ArrayList<GetMemberRecordResponse>)

    : RecyclerView.Adapter<Diary2ListRecyclerViewHolder>(), Filterable { init{
    setHasStableIds(true)
    }

    lateinit var diary2ListRecyclerViewHolder : Diary2ListRecyclerViewHolder
    private var unFilteredList = itemList
    private var filteredList = itemList

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
        println(filteredList[position])
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
                if(charString == "") {
                    val filterResults = FilterResults()
                    filterResults.values = ArrayList<GetMemberRecordResponse>()
                    return filterResults
                }
                else {
                    filteredList = if (charString.isEmpty()) {
                        unFilteredList
                    } else {
                        val filteringList = ArrayList<GetMemberRecordResponse>()
                        for (item in unFilteredList) {
                            if (item.title.contains(charString) || item.content.contains(charString)) {
                                filteringList.add(item)
                            }
                        }
                        filteringList
                    }
                    val filterResults = FilterResults()
                    filterResults.values = filteredList

                    return filterResults
                }
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                filteredList = results.values as ArrayList<GetMemberRecordResponse>
                println(filteredList)
                diary2SearchInterface.onGetItemSize(filteredList.size)
                notifyDataSetChanged()
            }
        }

    }
}