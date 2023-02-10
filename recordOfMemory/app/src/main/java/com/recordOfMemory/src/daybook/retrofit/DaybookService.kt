package com.recordOfMemory.src.daybook.retrofit
import android.util.Log
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.src.daybook.retrofit.models.GetDaybookResponse
import com.recordOfMemory.src.daybook.retrofit.models.PatchDaybookRequest
import com.recordOfMemory.src.daybook.retrofit.models.PatchDaybookResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class DaybookService(val daybookInterface: DaybookInterface){
	private val auth:String=
		"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMiIsImlhdCI6MTY3NjA0ODM3OCwiZXhwIjoxNjc2MDUxOTc4fQ.myqr8mXMUKNUcPPNNhRYZcYmVKk89q98mHgAYN2S7abrV5ZNhn3lTSuTtN_gj61aRuUm-9pdVFuHS_N1M0yBAg"
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

	fun tryGetDaybook(daybookId:Int){
		daybookRetrofitInterface.getDaybook(Authorization = "Bearer $auth",recordId=daybookId)
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
}