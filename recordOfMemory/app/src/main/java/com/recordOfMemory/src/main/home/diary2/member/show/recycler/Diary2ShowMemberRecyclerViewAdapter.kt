package com.recordOfMemory.src.main.home.diary2.member.show.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recordOfMemory.R
import com.recordOfMemory.src.main.home.diary2.member.models.GetMemberResponse

class Diary2ShowMemberRecyclerViewAdapter(val itemList: ArrayList<GetMemberResponse>)
    : RecyclerView.Adapter<Diary2ShowMemberRecyclerViewHolder>() {

    lateinit var diary2InviteMemberRecyclerViewHolder : Diary2ShowMemberRecyclerViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Diary2ShowMemberRecyclerViewHolder {
        diary2InviteMemberRecyclerViewHolder = Diary2ShowMemberRecyclerViewHolder(
            parent.context,
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_diary2_show_member, parent, false)
        )
        return diary2InviteMemberRecyclerViewHolder
    }

    override fun onBindViewHolder(holder: Diary2ShowMemberRecyclerViewHolder, position: Int) {
        holder.bindWithView(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}