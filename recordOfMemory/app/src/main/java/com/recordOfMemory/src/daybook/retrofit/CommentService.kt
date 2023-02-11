package com.recordOfMemory.src.daybook.retrofit

import android.util.Log
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.src.daybook.retrofit.models.GetCommentsResponse
import com.recordOfMemory.src.daybook.retrofit.models.PostCommentRequest
import com.recordOfMemory.src.daybook.retrofit.models.PostCommentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentService(val commentInterface: CommentInterface) {
	val token = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)
	val X_ACCESS_TOKEN = "Bearer $token"
	private val commentRetrofitInterface:CommentRetrofitInterface=ApplicationClass.sRetrofit.create(CommentRetrofitInterface::class.java)

	fun tryGetComments(daybookId:Int){
		commentRetrofitInterface.getComments(Authorization = X_ACCESS_TOKEN, id=daybookId)
			.enqueue(object :Callback<GetCommentsResponse>{
				override fun onResponse(call: Call<GetCommentsResponse>, response: Response<GetCommentsResponse>, ) {
					if(response.code()==200){
						commentInterface.onGetCommentsSuccess(response.body() as GetCommentsResponse)
					}else{
						Log.d("fail","fail to read Comments")
						commentInterface.onGetCommentsFailure("fail")
					}
				}

				override fun onFailure(call: Call<GetCommentsResponse>, t: Throwable) {
					commentInterface.onGetCommentsFailure(t.message?:"통신오류")
				}

			})
	}


	fun tryPostComment(params:PostCommentRequest){
		commentRetrofitInterface.postComment(Authorization = X_ACCESS_TOKEN,params=params)
			.enqueue(object :Callback<PostCommentResponse>{
			override fun onResponse(call: Call<PostCommentResponse>, response: Response<PostCommentResponse>) {
				if(response.code()==200){
					commentInterface.onPostCommentSuccess(response.body() as PostCommentResponse)
				}else{
					//여기 수정하자
					Log.d("fail","fail to write Comment")
					commentInterface.onPostCommentFailure("fail")
				}
			}

			override fun onFailure(call: Call<PostCommentResponse>, t: Throwable) {
				commentInterface.onPostCommentFailure(t.message?:"통신 오류")
			}
		})
	}
}