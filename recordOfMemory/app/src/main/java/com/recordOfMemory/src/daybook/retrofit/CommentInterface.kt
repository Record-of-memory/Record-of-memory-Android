package com.recordOfMemory.src.daybook.retrofit

import com.google.gson.annotations.SerializedName
import com.recordOfMemory.src.daybook.retrofit.models.PostCommentResponse

interface CommentInterface {
	fun onPostCommentSuccess(response: PostCommentResponse)
	fun onPostCommentFailure(message:String)
}
