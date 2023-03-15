package kr.co.app.recordOfMemory.src.main.home.diary2.search.recycler

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.app.recordOfMemory.R
import kr.co.app.recordOfMemory.src.main.home.diary2.retrofit.models.GetMemberRecordResponse

class Diary2SearchRecyclerViewHolder(val context: Context, itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    val itemImg = itemView.findViewById<View>(R.id.item_diary2_list_iv_img)
    val itemTitle = itemView.findViewById<TextView>(R.id.item_diary2_list_tv_title)
    val itemContent = itemView.findViewById<TextView>(R.id.item_diary2_list_tv_content)
    val itemWriter = itemView.findViewById<TextView>(R.id.item_diary2_list_tv_writer)
    val itemDate = itemView.findViewById<TextView>(R.id.item_diary2_list_tv_date)

    fun bindWithView(item: GetMemberRecordResponse) {
        Glide.with(itemView).load(item.imgUrl)
            .into(itemImg as ImageView)
        itemTitle.text = item.title
        itemContent.text = item.content
        itemWriter.text = item.user.nickname
        itemDate.text = item.date
    }

}