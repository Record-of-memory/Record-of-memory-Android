package com.recordOfMemory.src.main.home.diary2.likes

import retrofit2.Call
import retrofit2.http.*

interface LikesRetrofitInterface {
    @POST("/api/likes")
    fun postlikes(@Header("Authorization") Authorization: String, @Body params: PostLikesRequest): Call<LikesResponse>

    @DELETE("/api/likes/{diaryId}")
    fun deletelikes(@Header("Authorization") Authorization: String, @Path("diaryId") diaryId : String): Call<LikesResponse>

    @GET("api/likes/{diaryId}")
    fun checklikes(@Header("Authorization") Authorization: String, @Path("diaryId") diaryId : String): Call<CheckLikesResponse>

}