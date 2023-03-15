package kr.co.app.recordOfMemory.src.main.home.diary2.member.show.recycler

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.app.recordOfMemory.R
import kr.co.app.recordOfMemory.src.main.home.diary2.member.models.GetUserResponse

class Diary2ShowMemberRecyclerViewHolder(val context: Context, itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    val itemImg = itemView.findViewById<ImageView>(R.id.item_diary2_show_member_iv_profile)
    val itemNickname = itemView.findViewById<TextView>(R.id.item_diary2_show_member_tv_nickname)

    fun bindWithView(item: GetUserResponse) {
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