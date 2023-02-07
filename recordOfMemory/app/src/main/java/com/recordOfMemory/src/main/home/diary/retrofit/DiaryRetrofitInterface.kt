package com.recordOfMemory.src.main.home.diary.retrofit

import com.recordOfMemory.src.main.home.diary.retrofit.models.PostDiaryRequest
import com.recordOfMemory.src.main.home.diary.retrofit.models.PostDiaryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface DiaryRetrofitInterface {
    // 다이어리 생성 API
    @POST("/api/diaries")
    fun getDiaries(
        @Header("Authorization") Authorization: String,
        @Body params: PostDiaryRequest
    ) : Call<PostDiaryResponse>

}