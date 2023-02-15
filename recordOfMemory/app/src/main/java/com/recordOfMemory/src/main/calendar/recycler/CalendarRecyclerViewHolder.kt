package com.recordOfMemory.src.main.calendar.recycler

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recordOfMemory.R
import com.recordOfMemory.src.main.calendar.retrofit.models.Record
import com.recordOfMemory.util.getDateTime
import com.recordOfMemory.util.getDayOfWeek

class CalendarRecyclerViewHolder(val context: Context, itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    val itemImg = itemView.findViewById<View>(R.id.item_diary2_list_iv_img)
    val itemTitle = itemView.findViewById<TextView>(R.id.item_diary2_list_tv_title)
    val itemContent = itemView.findViewById<TextView>(R.id.item_diary2_list_tv_content)
    val itemWriter = itemView.findViewById<TextView>(R.id.item_diary2_list_tv_writer)
    val itemDate = itemView.findViewById<TextView>(R.id.item_diary2_list_tv_date)
    val itemLikes = itemView.findViewById<TextView>(R.id.item_diary2_list_tv_likes)
    val itemComments = itemView.findViewById<TextView>(R.id.item_diary2_list_tv_comments)
    val userImg = itemView.findViewById<ImageView>(R.id.item_diary2_list_iv_user_img)


    fun bindWithView(item: Record) {
        Glide.with(itemView).load(item.imgURL)
            .into(itemImg as ImageView)
        itemImg.clipToOutline = true
        itemTitle.text = item.title
        itemContent.text = item.content
        itemWriter.text = item.user.nickname
        itemLikes.text = item.likeCount
        itemComments.text = item.commentCount
        val dateTime = getDateTime(item.date)
        val date = dateTime.year.toString() + "." + dateTime.monthValue.toString() + "." + dateTime.dayOfMonth.toString() + "."
        val dayOfWeek = getDayOfWeek(dateTime.dayOfWeek)
        itemDate.text = "$date ($dayOfWeek)"

        if(item.user.imageURL.isNullOrEmpty()){
            userImg.setImageResource(R.drawable.icn_person)
        }else{
            Glide.with(itemView).load(item.user.imageURL)
                .into(userImg)
        }

    }

}