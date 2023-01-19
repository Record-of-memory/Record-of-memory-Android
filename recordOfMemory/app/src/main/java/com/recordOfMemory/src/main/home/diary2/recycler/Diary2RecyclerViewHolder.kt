package com.recordOfMemory.src.main.home.diary2.recycler

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dibn.src.main.home.search.recycler.Item

class Diary2RecyclerViewHolder(val context: Context, itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    val itemImg = itemView.findViewById<View>(R.id.item_img)
    val itemlikes = itemView.findViewById<TextView>(R.id.item_likes)
    val itemName = itemView.findViewById<TextView>(R.id.item_name)

    fun bindWithView(item: ItemData) {
        Glide.with(itemView).load("https://s3-alpha-sig.figma.com/img/9eaa/3523/b72e89689756d61764d40e5bd8fd131c?Expires=1653264000&Signature=DJniLR-QbDjVZv-mQE0Vizn4EAy3TrgzJmcGm~NADl3duEOqzTXhOWrQ9dVSMiMG5WOPMKrxW31LVpk1GOG6AHI5o3T7p7K7Llb8bh2d3-s9qpfsOsweBc4z8JhaZPFJ0dzxLljbLWwprJVSlLhMZ7cAQHKoOB4POZ2yIERag9dZ1bhaBKA9zKLC~Y1d79e0kVElYvZusHDkzfSd1ky0ljByq8jMfGRWT0rcyGnDQN4xX7DaYU6-TwdWNMHWhS1pQG-ut8Sz7egpq4oa8ZoER2m8j7Q05j6WDRhOHpfbjBjvEyhOV7v1xARLjUkkMbOB9XbT3xJvf73Tiwdvo2XH5w__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA")
            .into(itemImg as ImageView)
        itemlikes.text = item.like.toString()
        itemName.text = item.name
    }

}