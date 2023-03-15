@file:Suppress("DEPRECATION")

package kr.co.app.recordOfMemory.src.daybook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.app.recordOfMemory.databinding.ItemCommentBinding
import kr.co.app.recordOfMemory.src.daybook.retrofit.models.Comment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CommentAdapter(private val commentList:ArrayList<Comment>) :RecyclerView.Adapter<CommentAdapter.ViewHolder>(){
	private val sdfMini = SimpleDateFormat("yy.MM.dd", Locale.KOREA) //날짜 포맷
	private val sdfFull=SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)


	interface OnItemClickListener{
		fun onItemClick(v: View, pos : Int)
	}
	private var listener : OnItemClickListener? = null
	fun setOnItemClickListener(listener : OnItemClickListener) {
		this.listener = listener
	}



	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ViewHolder {
		val binding:ItemCommentBinding=ItemCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: CommentAdapter.ViewHolder, position: Int) {
		holder.bind(commentList[position])
	}

	override fun getItemCount(): Int = commentList.size


	inner class ViewHolder(val binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root){
		fun bind(item :Comment){
			binding.root.width
			binding.itemComment1Writer.text=item.nickname
			binding.itemComment1Comment.text=item.content

			if(item.createdAt.contains("T")){
				val date= sdfFull.parse(item.createdAt.split("T")[0])
				binding.itemComment1Time.text= sdfMini.format(date)
			}else{
				binding.itemComment1Time.text=item.createdAt
			}
			val pos = adapterPosition
			if(pos!= RecyclerView.NO_POSITION)
			{
				itemView.setOnClickListener {
					listener?.onItemClick(itemView,pos)
				}
			}
			//이미지 세팅하기 ---- 여기 체크
			if(!item.imageUrl.isNullOrEmpty()){
				Glide.with(itemView).load(item.imageUrl)
					.into(binding.itemComment1Icon)
			}
		}
	}

}

