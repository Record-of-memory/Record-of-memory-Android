package com.recordOfMemory.src.main.home.diary2.likes

import retrofit2.Call
import retrofit2.http.*

interface LikesRetrofitInterface {
    @POST("/api/likes")
    fun postlikes(@Header("Authorization") Authorization: String, @Body params: PostLikesRequest): Call<LikesResponse>

    @DELETE("/api/likes/{recordId}")
    fun deletelikes(@Header("Authorization") Authorization: String, @Path("recordId") recordId : String): Call<LikesResponse>

    @GET("api/likes/{recordId}")
    fun checklikes(@Header("Authorization") Authorization: String, @Path("recordId") recordId : String): Call<CheckLikesResponse>

}