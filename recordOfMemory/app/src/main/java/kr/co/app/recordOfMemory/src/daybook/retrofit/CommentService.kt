package kr.co.app.recordOfMemory.src.daybook.retrofit

import com.google.gson.GsonBuilder
import kr.co.app.recordOfMemory.config.BaseResponse
import kr.co.app.recordOfMemory.config.ErrorResponse
import kr.co.app.recordOfMemory.src.daybook.retrofit.models.DeleteCommentRequest
import kr.co.app.recordOfMemory.src.daybook.retrofit.models.GetCommentsResponse
import kr.co.app.recordOfMemory.src.daybook.retrofit.models.PostCommentRequest
import kr.co.app.recordOfMemory.src.daybook.retrofit.models.PostCommentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class CommentService(val commentInterface: CommentInterface) {
	val token = kr.co.app.recordOfMemory.config.ApplicationClass.sSharedPreferences.getString(
		kr.co.app.recordOfMemory.config.ApplicationClass.X_ACCESS_TOKEN, null)
	val X_ACCESS_TOKEN = "Bearer $token"
	private val commentRetrofitInterface:CommentRetrofitInterface=
        kr.co.app.recordOfMemory.config.ApplicationClass.sRetrofit.create(CommentRetrofitInterface::class.java)

	fun tryGetComments(recordId:String){
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
	fun tryDeleteComment(params: DeleteCommentRequest) {
		commentRetrofitInterface.deleteComment(Authorization = X_ACCESS_TOKEN, params = params)
			.enqueue(object :Callback<BaseResponse> {
			override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
				if(response.code()==200){
					commentInterface.onDeleteCommentSuccess(response.body() as BaseResponse)
				}else if(response.code()==401){
					commentInterface.onDeleteCommentFailure("refreshToken")
				}else{
					// error body 가져오는 코드 필요함
					val gson = GsonBuilder().create()
					try {
						val error = gson.fromJson(
							response.errorBody()!!.string(),
							ErrorResponse::class.java)
						commentInterface.onDeleteCommentFailure(error.information.message.split(": ")[1].split("\"")[0])
					} catch (e: IOException) {
						commentInterface.onDeleteCommentFailure(e.message ?: "통신 오류")
					}
				}
			}

			override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
				commentInterface.onDeleteCommentFailure(t.message?:"통신 오류")
			}
		})
	}

}