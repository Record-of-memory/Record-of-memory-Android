package kr.co.app.recordOfMemory.src.main.myPage.retrofit

import kr.co.app.recordOfMemory.src.main.myPage.retrofit.models.DeleteUsersResponse

interface MyPageEditInterface {
	fun onDeleteUsersSuccess(response: DeleteUsersResponse)
	fun onDeleteUsersFailure(message: String)

	fun onPatchUsersSuccess(response: kr.co.app.recordOfMemory.config.BaseResponse)
	fun onPatchUsersFailure(message: String)
}