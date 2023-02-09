package com.recordOfMemory.src.daybook.retrofit

import com.recordOfMemory.src.daybook.retrofit.models.GetDaybookResponse
import com.recordOfMemory.src.daybook.retrofit.models.PatchDaybookResponse

interface DaybookInterface {
	fun onDeleteDaybookSuccess(response: PatchDaybookResponse)
	fun onDeleteDaybookFailure(message: String)

	fun onGetDaybookSuccess(response: GetDaybookResponse)
	fun onGetDaybookFailure(message: String)
}