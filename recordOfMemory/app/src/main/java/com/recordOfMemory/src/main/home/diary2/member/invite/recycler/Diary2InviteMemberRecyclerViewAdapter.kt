package com.recordOfMemory.src.main.home.diary2.member.invite.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.recordOfMemory.R
import com.recordOfMemory.src.main.home.diary2.Diary2Interface
import com.recordOfMemory.src.main.home.diary2.member.models.GetMemberResponse
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetDiary2Response

class Diary2InviteMemberRecyclerViewAdapter(val diary2Interface: Diary2Interface, val itemList: ArrayList<GetMemberResponse>)
    : RecyclerView.Adapter<Diary2InviteMemberRecyclerViewHolder>(), Filterable {

    lateinit var diary2InviteMemberRecyclerViewHolder : Diary2InviteMemberRecyclerViewHolder
    private var unFilteredList = itemList
    private var filteredList = itemList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Diary2InviteMemberRecyclerViewHolder {
        diary2InviteMemberRecyclerViewHolder = Diary2InviteMemberRecyclerViewHolder(
            parent.context,
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_diary2_invite_member, parent, false)
        )
        return diary2InviteMemberRecyclerViewHolder
    }

    override fun onBindViewHolder(holder: Diary2InviteMemberRecyclerViewHolder, position: Int) {
        holder.bindWithView(filteredList[position])
        holder.itemButton.setOnClickListener {
            holder.itemButton.isEnabled = false
            holder.itemButton.text = "완료"
            // http 통신 필요
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
                    filterResults.values = ArrayList<GetDiary2Response>()
                    return filterResults
                }
                else {
                    filteredList = if (charString.isEmpty()) {
                        unFilteredList
                    } else {
                        val filteringList = ArrayList<GetMemberResponse>()
                        for (item in unFilteredList) {
                            if (item.email.contains(charString)) {
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
                filteredList = results.values as ArrayList<GetMemberResponse>
                println(filteredList)
                diary2Interface.onGetItemSize(filteredList.size)
                notifyDataSetChanged()
            }
        }

    }
}