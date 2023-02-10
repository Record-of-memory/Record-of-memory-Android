package com.recordOfMemory.src.daybook.retrofit

import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.config.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DaybookService(val daybookInterface: DaybookInterface) {
    val X_ACCESS_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2IiwiaWF0IjoxNjc2MDU0NDIyLCJleHAiOjE2NzYwNTgwMjJ9.oxrL9v_gT1jfiONkZaVjSjLFhKYW37ii_uUi7QrAsbajgSxYSUDu_9o7FDJktlhy9__JXaZ1vZv5I_RTUJm4Hg"
    fun tryPostRecord(imgUrl : MultipartBody.Part, writeRecordReq : RequestBody) {
        println("imgUrl: ${imgUrl.body}")
        println("writeRecordReq $writeRecordReq")

        val daybookRetrofitInterface = ApplicationClass.sRetrofit.create(DaybookRetrofitInterface::class.java)
        daybookRetrofitInterface.postRecord(Authorization = X_ACCESS_TOKEN, img = imgUrl, writeRecordReq = writeRecordReq)
            .enqueue(object :
            Callback<BaseResponse> {
            override fun onResponse(
                call: Call<BaseResponse>,
                response: Response<BaseResponse>
            ) {
                daybookInterface.onPostRecordSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                daybookInterface.onPostRecordFailure(t.message ?: "통신 오류")
            }
        })
    }
}