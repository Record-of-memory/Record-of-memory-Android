package com.recordOfMemory.src.main.home

import com.recordOfMemory.src.main.home.models.PostSignUpRequest
import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.src.main.home.models.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface HomeRetrofitInterface {
    @GET("/template/users")
    fun getUsers() : Call<UserResponse>

    @POST("/template/users")
    fun postSignUp(@Body params: PostSignUpRequest): Call<BaseResponse>
}
