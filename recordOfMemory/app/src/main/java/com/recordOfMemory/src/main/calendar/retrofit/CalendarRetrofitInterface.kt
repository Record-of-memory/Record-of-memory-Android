package com.recordOfMemory.src.main.calendar.retrofit

import com.recordOfMemory.src.main.calendar.retrofit.models.GetCalendarDiariesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CalendarRetrofitInterface {
    @GET("/api/records/date")
    fun getRecordsDate(
        @Header("Authorization") Authorization: String,
        @Query("date") date : String
    ): Call<GetCalendarDiariesResponse>
}