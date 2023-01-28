package com.recordOfMemory.src.daybook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recordOfMemory.databinding.ItemCommentBinding

class CommentAdapter(private val commentList:ArrayList<CommentData>) :RecyclerView.Adapter<CommentAdapter.ViewHolder>(){


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ViewHolder {
		val binding:ItemCommentBinding=ItemCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: CommentAdapter.ViewHolder, position: Int) {
		holder.bind(commentList[position])
	}

	override fun getItemCount(): Int = commentList.size


	inner class ViewHolder(val binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root){
		fun bind(commentData : CommentData){
			binding.root.width
			binding.itemComment1Writer.text=commentData.userName
			binding.itemComment1Comment.text=commentData.userComment
			binding.itemComment1Time.text=commentData.commentTime
		}
	}

}