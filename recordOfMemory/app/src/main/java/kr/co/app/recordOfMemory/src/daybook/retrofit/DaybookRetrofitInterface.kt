package kr.co.app.recordOfMemory.src.daybook.retrofit

import kr.co.app.recordOfMemory.config.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import kr.co.app.recordOfMemory.src.daybook.retrofit.models.GetDaybookResponse
import kr.co.app.recordOfMemory.src.daybook.retrofit.models.PatchDaybookRequest
import kr.co.app.recordOfMemory.src.daybook.retrofit.models.PatchDaybookResponse
import retrofit2.http.*

interface DaybookRetrofitInterface {
	//일기 작성 API
	@Multipart
	@POST("/api/records")
	fun postRecord(
		@Header("Authorization") Authorization: String,
		@Part img: MultipartBody.Part?,
		@Part("writeRecordReq") writeRecordReq: RequestBody
	): Call<BaseResponse>

	//일기 수정 API
	@Multipart
	@PATCH("/api/records/edit")
	fun patchRecord(
		@Header("Authorization") Authorization: String,
		@Part img: MultipartBody.Part?,
		@Part("updateRecordReq") updateRecordReq: RequestBody
	): Call<BaseResponse>
	//일기 삭제 API
	@PATCH("/api/records")
	fun deleteDaybook(
		@Header("Authorization") Authorization: String,
		@Body params:PatchDaybookRequest
	):Call<PatchDaybookResponse>

	//일기 상세 조회 API
	@GET("api/records/detail/{recordId}")
	fun getDaybook(
		@Header("Authorization") Authorization: String,
		@Path("recordId") recordId:String
	):Call<GetDaybookResponse>
}