package com.recordOfMemory.src.main.home.diary2.member.invite.recycler

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recordOfMemory.R
import com.recordOfMemory.src.main.home.diary2.member.models.GetMemberResponse

class Diary2InviteMemberRecyclerViewHolder(val context: Context, itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    val itemImg = itemView.findViewById<ImageView>(R.id.item_diary2_invite_member_iv_profile)
    val itemNickname = itemView.findViewById<TextView>(R.id.item_diary2_invite_member_tv_nickname)
    val itemButton = itemView.findViewById<Button>(R.id.item_diary2_invite_member_btn_invite)

    fun bindWithView(item: GetMemberResponse) {
        if (item.imageUrl != null) {
            Glide.with(itemView).load(item.imageUrl)
                .into(itemImg as ImageView)
        }
        else {
            itemImg.setImageDrawable(context.getDrawable(R.drawable.icn_user))
        }
        itemNickname.text = item.nickname
    }
}