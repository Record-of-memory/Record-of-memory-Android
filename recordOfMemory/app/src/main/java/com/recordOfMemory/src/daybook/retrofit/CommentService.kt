package com.recordOfMemory.src.daybook.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.config.ErrorResponse
import com.recordOfMemory.src.daybook.retrofit.models.GetCommentsResponse
import com.recordOfMemory.src.daybook.retrofit.models.PostCommentRequest
import com.recordOfMemory.src.daybook.retrofit.models.PostCommentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class CommentService(val commentInterface: CommentInterface) {
	val token = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)
	val X_ACCESS_TOKEN = "Bearer $token"
	private val commentRetrofitInterface:CommentRetrofitInterface=ApplicationClass.sRetrofit.create(CommentRetrofitInterface::class.java)

	fun tryGetComments(recordId:Int){
		commentRetrofitInterface.getComments(Authorization = X_ACCESS_TOKEN, id=recordId)
			.enqueue(object :Callback<GetCommentsResponse>{
				override fun onResponse(call: Call<GetCommentsResponse>, response: Response<GetCommentsResponse>, ) {
					if(response.code()==200){
						commentInterface.onGetCommentsSuccess(response.body() as GetCommentsResponse)
					}else if(response.code()==401){
						commentInterface.onGetCommentsFailure("refreshToken")
					}else{
						// error body 가져오는 코드 필요함
						val gson = GsonBuilder().create()
						try {
							val error = gson.fromJson(
								response.errorBody()!!.string(),
								ErrorResponse::class.java)

							commentInterface.onGetCommentsFailure(error.information.message.split(": ")[1].split("\"")[0])
						} catch (e: IOException) {
							commentInterface.onGetCommentsFailure(e.message ?: "통신 오류")
						}
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
				}else if(response.code()==401){
					commentInterface.onPostCommentFailure("refreshToken")
				}else{
					// error body 가져오는 코드 필요함
					val gson = GsonBuilder().create()
					try {
						val error = gson.fromJson(
							response.errorBody()!!.string(),
							ErrorResponse::class.java)
						commentInterface.onPostCommentFailure(error.information.message.split(": ")[1].split("\"")[0])
					} catch (e: IOException) {
						commentInterface.onPostCommentFailure(e.message ?: "통신 오류")
					}
				}
			}

			override fun onFailure(call: Call<PostCommentResponse>, t: Throwable) {
				commentInterface.onPostCommentFailure(t.message?:"통신 오류")
			}
		})
	}


}