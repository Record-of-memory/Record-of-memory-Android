package kr.co.app.recordOfMemory.src.main.home

import kr.co.app.recordOfMemory.config.BaseResponse
import kr.co.app.recordOfMemory.src.main.home.models.PostSignUpRequest
import kr.co.app.recordOfMemory.src.main.home.models.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface HomeRetrofitInterface {
    @GET("/template/users")
    fun getUsers() : Call<UserResponse>

    @POST("/template/users")
    fun postSignUp(@Body params: PostSignUpRequest): Call<BaseResponse>
}
