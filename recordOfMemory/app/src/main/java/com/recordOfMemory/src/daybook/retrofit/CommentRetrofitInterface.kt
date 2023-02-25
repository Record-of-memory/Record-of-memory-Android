package com.recordOfMemory.src.daybook.retrofit

import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.src.daybook.retrofit.models.DeleteCommentRequest
import com.recordOfMemory.src.daybook.retrofit.models.GetCommentsResponse
import com.recordOfMemory.src.daybook.retrofit.models.PostCommentRequest
import com.recordOfMemory.src.daybook.retrofit.models.PostCommentResponse
import retrofit2.Call
import retrofit2.http.*

interface CommentRetrofitInterface {
	//댓글 조회 API
	@GET("/api/comments/{id}")
	fun getComments(
		@Header("Authorization") Authorization: String,
		@Path("id") id:String
	): Call<GetCommentsResponse>

	//댓글 입력 API
	@POST("/api/comments")
	fun postComment(
		@Header("Authorization") Authorization: String,
		@Body params:PostCommentRequest
	):Call<PostCommentResponse>

	//댓글 삭제 API
	@PATCH("/api/comments")
	fun deleteComment(
		@Header("Authorization") Authorization: String,
		@Body params: DeleteCommentRequest
	):Call<BaseResponse>
}