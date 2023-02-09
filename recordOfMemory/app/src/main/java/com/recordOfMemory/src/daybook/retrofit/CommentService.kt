package com.recordOfMemory.src.daybook.retrofit

import android.util.Log
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.src.daybook.retrofit.models.PostCommentRequest
import com.recordOfMemory.src.daybook.retrofit.models.PostCommentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentService(val commentInterface: CommentInterface) {
	private val auth:String=
		"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMCIsImlhdCI6MTY3NTk1MzU1NCwiZXhwIjoxNjc1OTU3MTU0fQ.N57yF0fPyP87WMd-RZ5lHpQOFlzW_SikJz9b2amHuYrr72Yw42TngJakelstQrfk02v4-0YUdAGiWxOxpSSaPg"
	private val commentRetrofitInterface:CommentRetrofitInterface=ApplicationClass.sRetrofit.create(CommentRetrofitInterface::class.java)

	fun tryPostComment(params:PostCommentRequest){
		commentRetrofitInterface.postComment(Authorization = "Bearer $auth",params=params)
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