package com.recordOfMemory.src.daybook.retrofit

import com.recordOfMemory.src.daybook.retrofit.models.PatchDaybookResponse

interface DaybookInterface {
	fun onDeleteDaybookSuccess(response: PatchDaybookResponse)
	fun onDeleteDaybookFailure(message: String)
}