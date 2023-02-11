package com.recordOfMemory.src.daybook.retrofit

import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.config.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import com.recordOfMemory.src.daybook.retrofit.models.GetDaybookResponse
import com.recordOfMemory.src.daybook.retrofit.models.PatchDaybookRequest
import com.recordOfMemory.src.daybook.retrofit.models.PatchDaybookResponse
import retrofit2.create

class DaybookService(val daybookInterface: DaybookInterface) {
    val X_ACCESS_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2IiwiaWF0IjoxNjc2MDU0NDIyLCJleHAiOjE2NzYwNTgwMjJ9.oxrL9v_gT1jfiONkZaVjSjLFhKYW37ii_uUi7QrAsbajgSxYSUDu_9o7FDJktlhy9__JXaZ1vZv5I_RTUJm4Hg"
	private val daybookRetrofitInterface:DaybookRetrofitInterface=ApplicationClass.sRetrofit.create(DaybookRetrofitInterface::class.java)

	fun tryDeleteDaybook(params: PatchDaybookRequest){
		daybookRetrofitInterface.deleteDaybook(Authorization = "Bearer $X_ACCESS_TOKEN",params=params)
			.enqueue(object : Callback<PatchDaybookResponse>{
				override fun onResponse(call: Call<PatchDaybookResponse>, response: Response<PatchDaybookResponse>, ) {
					if(response.code()==200){
						daybookInterface.onDeleteDaybookSuccess(response.body() as PatchDaybookResponse)
					}else{
						Log.d("fail","fail to delete Daybook")
						daybookInterface.onDeleteDaybookFailure("fail")
					}
				}

				override fun onFailure(call: Call<PatchDaybookResponse>, t: Throwable) {
					daybookInterface.onDeleteDaybookFailure(t.message?:"통신오류")
				}

			})
	}

	fun tryGetDaybook(daybookId:Int){
		daybookRetrofitInterface.getDaybook(Authorization = "Bearer $X_ACCESS_TOKEN",recordId=daybookId)
			.enqueue(object :Callback<GetDaybookResponse>{
				override fun onResponse(call: Call<GetDaybookResponse>, response: Response<GetDaybookResponse>, ) {
					if(response.code()==200){
						daybookInterface.onGetDaybookSuccess(response.body() as GetDaybookResponse)
					}else{
						Log.d("fail","fail to get Daybook")
						daybookInterface.onGetDaybookFailure("fail")
					}
				}

				override fun onFailure(call: Call<GetDaybookResponse>, t: Throwable) {
					daybookInterface.onGetDaybookFailure(t.message?:"통신 오류")
				}

			})
	}
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