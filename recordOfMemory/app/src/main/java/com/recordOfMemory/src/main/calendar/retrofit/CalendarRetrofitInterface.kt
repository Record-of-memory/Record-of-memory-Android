package com.recordOfMemory.src.main.calendar.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface CalendarRetrofitInterface {
    @GET("/api/records/date")
    fun getRecordsDate(
        @Header("Authorization") Authorization: String,
        
    ): Call<BaseResponse>
}