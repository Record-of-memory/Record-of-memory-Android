package com.recordOfMemory.src.daybook.retrofit

import com.recordOfMemory.config.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DaybookRetrofitInterface {
    @Multipart
    @POST("/api/records")
    fun postRecord(
        @Header("Authorization") Authorization: String,
        @Part img: MultipartBody.Part,
        @Part("writeRecordReq") writeRecordReq: RequestBody
    ): Call<BaseResponse>
}