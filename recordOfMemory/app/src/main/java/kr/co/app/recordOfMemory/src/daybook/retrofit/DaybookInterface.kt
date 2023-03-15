package kr.co.app.recordOfMemory.src.daybook.retrofit

import kr.co.app.recordOfMemory.src.daybook.retrofit.models.GetDaybookResponse
import kr.co.app.recordOfMemory.src.daybook.retrofit.models.PatchDaybookResponse

interface DaybookInterface {
	fun onDeleteDaybookSuccess(response: PatchDaybookResponse)
	fun onDeleteDaybookFailure(message: String)

	fun onGetDaybookSuccess(response: GetDaybookResponse)
	fun onGetDaybookFailure(message: String)
}