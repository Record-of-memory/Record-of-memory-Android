package com.recordOfMemory.src.daybook.retrofit

import com.google.gson.annotations.SerializedName
import com.recordOfMemory.src.daybook.retrofit.models.GetCommentsResponse
import com.recordOfMemory.src.daybook.retrofit.models.PostCommentResponse

interface CommentInterface {
	fun onPostCommentSuccess(response: PostCommentResponse)
	fun onPostCommentFailure(message:String)

	fun onGetCommentsSuccess(response: GetCommentsResponse)
	fun onGetCommentsFailure(message: String)
}
