package com.recordOfMemory.src.daybook.retrofit

import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.src.daybook.retrofit.models.GetDaybookResponse
import com.recordOfMemory.src.daybook.retrofit.models.PatchDaybookResponse

interface DaybookInterface {
    fun onPostRecordSuccess(response: BaseResponse)
    fun onPostRecordFailure(response: String)
	fun onDeleteDaybookSuccess(response: PatchDaybookResponse)
	fun onDeleteDaybookFailure(message: String)

	fun onGetDaybookSuccess(response: GetDaybookResponse)
	fun onGetDaybookFailure(message: String)
}