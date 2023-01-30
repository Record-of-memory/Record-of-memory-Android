package com.recordOfMemory.src.main.home.diary
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.recordOfMemory.R

class DiaryAdapter(val itemList: ArrayList<DiaryData>) :
    RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_diary_together, parent, false)
        return DiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.tv_item_name.text = itemList[position].title
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_item_name = itemView.findViewById<TextView>(R.id.tv_item_name)
    }
}