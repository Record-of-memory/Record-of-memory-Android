package com.recordOfMemory.src.main.home.Diary

import com.recordOfMemory.src.main.home.Diary.retrofit.models.GetDiaryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface DiaryRetrofitInterface {
    @GET("/api/diaries")
    fun getDiary() : Call<GetDiaryResponse>

    @FormUrlEncoded
    @POST("/api/diaries")
    fun postDiary(
        @Field("name") name : String,
        @Field("diaryType") diaryType : String
        ) : Call<GetDiaryResponse>

}