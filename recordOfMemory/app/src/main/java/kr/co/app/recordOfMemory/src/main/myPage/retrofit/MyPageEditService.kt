package kr.co.app.recordOfMemory.src.main.myPage.retrofit

import com.google.gson.GsonBuilder
import kr.co.app.recordOfMemory.config.ApplicationClass
import kr.co.app.recordOfMemory.config.BaseResponse
import kr.co.app.recordOfMemory.config.ErrorResponse
import kr.co.app.recordOfMemory.src.main.myPage.retrofit.models.DeleteUsersResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MyPageEditService(val myPageEditInterface: MyPageEditInterface) {
	val token = ApplicationClass.sSharedPreferences.getString(
		ApplicationClass.X_ACCESS_TOKEN, null)
	val X_ACCESS_TOKEN = "Bearer $token"
	private val myPageEditRetrofitInterface: MyPageEditRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageEditRetrofitInterface::class.java)

	fun tryDeleteUsers(){
		myPageEditRetrofitInterface.deleteUsers(Authorization = X_ACCESS_TOKEN).enqueue(object : Callback<DeleteUsersResponse> {
			override fun onResponse(call: Call<DeleteUsersResponse>, response: Response<DeleteUsersResponse>) {
				if(response.isSuccessful){
					myPageEditInterface.onDeleteUsersSuccess(response.body() as DeleteUsersResponse)
				}else if(response.code()==401){
					myPageEditInterface.onDeleteUsersFailure("refreshToken")
				}else{
					// error body 가져오는 코드 필요함
					val gson = GsonBuilder().create()
					try {
						val error = gson.fromJson(
							response.errorBody()!!.string(),
							kr.co.app.recordOfMemory.config.ErrorResponse::class.java)
						myPageEditInterface.onDeleteUsersFailure(error.information.message.split(": ")[1].split("\"")[0])
					} catch (e: IOException) {
						myPageEditInterface.onDeleteUsersFailure(e.message ?: "통신 오류")
					}
				}
			}

			override fun onFailure(call: Call<DeleteUsersResponse>, t: Throwable) {
				myPageEditInterface.onDeleteUsersFailure(t.message ?: "통신 오류")
			}
		})
	}

	fun tryPatchUsers(profileImg : MultipartBody.Part?, nickname : String){
		val newNickname = nickname.toRequestBody("text/plain".toMediaTypeOrNull())

		myPageEditRetrofitInterface.patchUsers(Authorization = X_ACCESS_TOKEN, profileImg = profileImg, nickname = newNickname).enqueue(object : Callback<BaseResponse> {
			override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
				if(response.isSuccessful){
					myPageEditInterface.onPatchUsersSuccess(response.body() as BaseResponse)
				}else if(response.code()==401){
					myPageEditInterface.onPatchUsersFailure("refreshToken")
				}else{
					// error body 가져오는 코드 필요함
					val gson = GsonBuilder().create()
					try {
						val error = gson.fromJson(
							response.errorBody()!!.string(),
							ErrorResponse::class.java)
						myPageEditInterface.onPatchUsersFailure(error.information.message.split(": ")[1].split("\"")[0])
					} catch (e: IOException) {
						myPageEditInterface.onPatchUsersFailure(e.message ?: "통신 오류")
					}
				}
			}

			override fun onFailure(call: Call<kr.co.app.recordOfMemory.config.BaseResponse>, t: Throwable) {
				myPageEditInterface.onPatchUsersFailure(t.message ?: "통신 오류")
			}
		})
	}
}