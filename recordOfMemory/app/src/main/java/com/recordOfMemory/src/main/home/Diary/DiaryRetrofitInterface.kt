package com.recordOfMemory.src.main.home.Diary

import com.recordOfMemory.src.main.home.Diary.retrofit.models.GetDiariesResponse
import com.recordOfMemory.src.main.home.Diary.retrofit.models.GetUsersResponse
import com.recordOfMemory.src.main.home.Diary.retrofit.models.PostDiariesRequest
import com.recordOfMemory.src.main.home.Diary.retrofit.models.PostDiariesResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface DiaryRetrofitInterface {
    @GET("/api/diaries")
    fun getDiaries(@Header("Authorization") Authorization: String) : Call<GetDiariesResponse>

    @POST("/api/diaries")
    fun postDiaries(@Header("Authorization") Authorization: String, @Body params: PostDiariesRequest) : Call<PostDiariesResponse>

    @GET("/api/users/me")
    fun getUsers(
        @Header("Authorization") Authorization: String,
    ):Call<GetUsersResponse>
}

