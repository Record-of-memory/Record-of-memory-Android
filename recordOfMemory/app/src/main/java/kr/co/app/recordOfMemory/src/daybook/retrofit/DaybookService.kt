package kr.co.app.recordOfMemory.src.daybook.retrofit

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.GsonBuilder
import kr.co.app.recordOfMemory.config.ApplicationClass
import kr.co.app.recordOfMemory.config.BaseResponse
import kr.co.app.recordOfMemory.config.ErrorResponse
import kr.co.app.recordOfMemory.src.daybook.retrofit.models.GetDaybookResponse
import kr.co.app.recordOfMemory.src.daybook.retrofit.models.PatchDaybookRequest
import kr.co.app.recordOfMemory.src.daybook.retrofit.models.PatchDaybookResponse
import java.io.IOException

class DaybookService() {
	val token = ApplicationClass.sSharedPreferences.getString(
		ApplicationClass.X_ACCESS_TOKEN, null)

	lateinit var daybookWritingInterface: DaybookWritingInterface
	lateinit var daybookInterface: DaybookInterface
	constructor(daybookWritingInterface: DaybookWritingInterface) : this() {
		this.daybookWritingInterface = daybookWritingInterface
	}
	constructor(daybookInterface: DaybookInterface) : this() {
		this.daybookInterface = daybookInterface
	}

	val X_ACCESS_TOKEN = "Bearer ${
        ApplicationClass.sSharedPreferences.getString(
            ApplicationClass.X_ACCESS_TOKEN, null)}"

	private val daybookRetrofitInterface:DaybookRetrofitInterface=
        ApplicationClass.sRetrofit.create(DaybookRetrofitInterface::class.java)

	fun tryDeleteDaybook(params: PatchDaybookRequest){
		daybookRetrofitInterface.deleteDaybook(Authorization = X_ACCESS_TOKEN,params=params)
			.enqueue(object : Callback<PatchDaybookResponse>{
				override fun onResponse(call: Call<PatchDaybookResponse>, response: Response<PatchDaybookResponse>, ) {
					if(response.code()==200){
						daybookInterface.onDeleteDaybookSuccess(response.body() as PatchDaybookResponse)
					}else if(response.code()==401){
						daybookInterface.onDeleteDaybookFailure("refreshToken")
					}else{
						// error body 가져오는 코드 필요함
						val gson = GsonBuilder().create()
						try {
							val error = gson.fromJson(
								response.errorBody()!!.string(),
								ErrorResponse::class.java)
							daybookInterface.onDeleteDaybookFailure(error.information.message.split(": ")[1].split("\"")[0])
						} catch (e: IOException) {
							daybookInterface.onDeleteDaybookFailure(e.message ?: "통신 오류")
						}

					}
				}

				override fun onFailure(call: Call<PatchDaybookResponse>, t: Throwable) {
					daybookInterface.onDeleteDaybookFailure(t.message?:"통신오류")
				}

			})
	}

	fun tryGetDaybook(daybookId:String){
		daybookRetrofitInterface.getDaybook(Authorization = X_ACCESS_TOKEN,recordId=daybookId)
			.enqueue(object :Callback<GetDaybookResponse>{
				override fun onResponse(call: Call<GetDaybookResponse>, response: Response<GetDaybookResponse>, ) {
					if(response.code()==200){
						daybookInterface.onGetDaybookSuccess(response.body() as GetDaybookResponse)
					}else if(response.code()==401){
						daybookInterface.onGetDaybookFailure("refreshToken")
					}else{
						val gson = GsonBuilder().create()
						try {
							val error = gson.fromJson(
								response.errorBody()!!.string(),
								ErrorResponse::class.java)
							daybookInterface.onGetDaybookFailure(error.information.message.split(": ")[1].split("\"")[0])
						} catch (e: IOException) {
							daybookInterface.onGetDaybookFailure(e.message ?: "통신 오류")
						}

					}
				}

				override fun onFailure(call: Call<GetDaybookResponse>, t: Throwable) {
					daybookInterface.onGetDaybookFailure(t.message?:"통신 오류")
				}

			})
	}

	fun tryPostRecord(imgUrl : MultipartBody.Part?, writeRecordReq : RequestBody) {
		val daybookRetrofitInterface = ApplicationClass.sRetrofit.create(DaybookRetrofitInterface::class.java)
		daybookRetrofitInterface.postRecord(Authorization = X_ACCESS_TOKEN, img = imgUrl, writeRecordReq = writeRecordReq)
			.enqueue(object :
				Callback<BaseResponse> {
				override fun onResponse(
					call: Call<BaseResponse>,
					response: Response<BaseResponse>
				) {
					daybookWritingInterface.onPostRecordSuccess(response.body() as BaseResponse)
				}

				override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
					daybookWritingInterface.onPostRecordFailure(t.message ?: "통신 오류")
				}
			})
	}

	fun tryPatchRecord(imgUrl : MultipartBody.Part?, updateRecordReq : RequestBody) {
		if (imgUrl != null) {
			println("imgUrl: ${imgUrl.body}")
		}
		println("updateRecordReq $updateRecordReq")

		val daybookRetrofitInterface = ApplicationClass.sRetrofit.create(DaybookRetrofitInterface::class.java)
		daybookRetrofitInterface.patchRecord(Authorization = X_ACCESS_TOKEN, img = imgUrl, updateRecordReq = updateRecordReq)
			.enqueue(object :
				Callback<BaseResponse> {
				override fun onResponse(
					call: Call<BaseResponse>,
					response: Response<BaseResponse>
				) {
					daybookWritingInterface.onPatchRecordSuccess(response.body() as BaseResponse)
				}

				override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
					daybookWritingInterface.onPatchRecordFailure(t.message ?: "통신 오류")
				}
			})
	}
}