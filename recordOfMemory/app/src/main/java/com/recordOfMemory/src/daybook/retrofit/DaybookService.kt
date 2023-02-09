package com.recordOfMemory.src.daybook.retrofit
import android.util.Log
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.src.daybook.retrofit.models.PatchDaybookRequest
import com.recordOfMemory.src.daybook.retrofit.models.PatchDaybookResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class DaybookService(val daybookInterface: DaybookInterface){
	private val auth:String=
		"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMiIsImlhdCI6MTY3NTk2Nzg5NCwiZXhwIjoxNjc1OTcxNDk0fQ.q6RiphbVCL6MXgT3uUbBhOj9CRKQ7J40oW8dPNLt6ifFfScbKF4fMMJr-BINV_KajAMmj9-x6dRW19-TsIJwQg"
	private val daybookRetrofitInterface:DaybookRetrofitInterface=ApplicationClass.sRetrofit.create(DaybookRetrofitInterface::class.java)

	fun tryDeleteDaybook(params: PatchDaybookRequest){
		daybookRetrofitInterface.deleteDaybook(Authorization = "Bearer $auth",params=params)
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
}