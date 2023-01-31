package com.recordOfMemory.src.main.home.Diary
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.recordOfMemory.R
import com.recordOfMemory.src.main.MainActivity

class DiaryAdapter(val itemList: ArrayList<DiaryData>) :
    RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

    var datas = mutableListOf<DiaryData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_diary, parent, false)
        return DiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    inner class DiaryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_item_name: TextView  = itemView.findViewById(R.id.tv_item_name)

        fun bind(item: DiaryData) {
            tv_item_name.text = item.title
            itemView.setOnClickListener {
                Toast
                    .makeText(itemView.getContext(),"선택한 일기로 이동", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(itemView.getContext(), MainActivity::class.java)
                itemView.getContext().startActivity(intent)
            }
        }

    }
}