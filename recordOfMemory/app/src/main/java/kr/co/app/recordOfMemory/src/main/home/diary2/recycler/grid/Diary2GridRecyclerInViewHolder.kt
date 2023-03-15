package kr.co.app.recordOfMemory.src.main.home.diary2.recycler.grid

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.app.recordOfMemory.R
import kr.co.app.recordOfMemory.src.main.home.diary2.retrofit.models.GridRecord

class Diary2GridRecyclerInViewHolder(val context: Context, itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    val itemImg = itemView.findViewById<View>(R.id.item_diary2_grid_in_iv_img)
    val itemTitle = itemView.findViewById<TextView>(R.id.item_diary2_grid_in_tv_title)

    fun bindWithView(item: GridRecord) {
        Glide.with(itemView).load(item.imgUrl)
            .into(itemImg as ImageView)
        itemImg.clipToOutline = true
        itemTitle.text = item.title
    }
}