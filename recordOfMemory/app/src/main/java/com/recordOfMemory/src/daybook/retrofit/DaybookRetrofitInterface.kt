package com.recordOfMemory.src.daybook.retrofit
import com.recordOfMemory.src.daybook.retrofit.models.PatchDaybookRequest
import com.recordOfMemory.src.daybook.retrofit.models.PatchDaybookResponse
import retrofit2.Call
import retrofit2.http.*

interface DaybookRetrofitInterface {
	//일기 삭제 API
	@PATCH("/api/records")
	fun deleteDaybook(
		@Header("Authorization") Authorization: String,
		@Body params:PatchDaybookRequest
	):Call<PatchDaybookResponse>
}