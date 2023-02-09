package com.recordOfMemory.src.main.home.diary2.member.invite.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.recordOfMemory.R
import com.recordOfMemory.src.main.home.diary2.member.invite.retrofit.models.PostDiary2InviteRequest
import com.recordOfMemory.src.main.home.diary2.member.models.GetUserResponse
import com.recordOfMemory.src.main.home.diary2.member.invite.retrofit.Diary2InviteInterface
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetRecordResponse

class Diary2InviteMemberRecyclerViewAdapter(val diary2InviteInterface: Diary2InviteInterface, val itemList: ArrayList<GetUserResponse>)
    : RecyclerView.Adapter<Diary2InviteMemberRecyclerViewHolder>(), Filterable {

    lateinit var diary2InviteMemberRecyclerViewHolder : Diary2InviteMemberRecyclerViewHolder
    private var unFilteredList = ArrayList<GetUserResponse>()
    private var filteredList = ArrayList<GetUserResponse>()

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
            val postDiary2InviteRequest = PostDiary2InviteRequest(email = filteredList[position].email, diaryId = "1")
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
                    filterResults.values = ArrayList<GetRecordResponse>()
                    return filterResults
                }
                else {
                    filteredList = if (charString.isEmpty()) {
                        unFilteredList
                    } else {
                        val filteringList = ArrayList<GetUserResponse>()
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
                filteredList = results.values as ArrayList<GetUserResponse>
                println(filteredList)
                diary2InviteInterface.onGetItemSize(filteredList.size)
                notifyDataSetChanged()
            }
        }

    }
}