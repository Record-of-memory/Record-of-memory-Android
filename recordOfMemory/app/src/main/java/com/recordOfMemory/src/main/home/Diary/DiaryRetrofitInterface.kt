package com.recordOfMemory.src.main.home.Diary

import com.recordOfMemory.src.main.home.Diary.retrofit.models.GetDiaryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DiaryRetrofitInterface {
    @GET("/template/diaries")
    fun getDiary() : Call<GetDiaryResponse>
}