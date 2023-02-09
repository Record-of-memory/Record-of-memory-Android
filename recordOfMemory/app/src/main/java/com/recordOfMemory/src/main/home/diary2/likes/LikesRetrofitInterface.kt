package com.recordOfMemory.src.main.home.diary2.likes

import com.recordOfMemory.src.main.home.models.PostSignUpRequest
import com.recordOfMemory.src.main.home.models.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LikesRetrofitInterface {
    @POST("/api/likes")
    fun postlikes(@Header("Authorization") Authorization: String, @Body params: PostLikesRequest): Call<LikesResponse>
}