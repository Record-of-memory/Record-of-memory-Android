package com.recordOfMemory.src.main.home.diary2.recycler

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recordOfMemory.R
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetDiary2Response

class Diary2GridRecyclerViewHolder(val context: Context, itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    val itemImg = itemView.findViewById<View>(R.id.item_diary2_list_iv_img)
    val itemTitle = itemView.findViewById<TextView>(R.id.item_diary2_list_tv_title)
    val itemContent = itemView.findViewById<TextView>(R.id.item_diary2_list_tv_content)
    val itemWriter = itemView.findViewById<TextView>(R.id.item_diary2_list_tv_writer)
    val itemDate = itemView.findViewById<TextView>(R.id.item_diary2_list_tv_date)

    fun bindWithView(item: GetDiary2Response) {
        Glide.with(itemView).load(item.imgUrl)
            .into(itemImg as ImageView)
        itemTitle.text = item.title
        itemContent.text = item.content
        itemWriter.text = item.writer
        itemDate.text = item.date
    }

}