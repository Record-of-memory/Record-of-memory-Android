package kr.co.app.recordOfMemory.src.main.myPage.retrofit

interface MyPageEditPasswordInterface {
	fun onPostChangePasswordSuccess(response: kr.co.app.recordOfMemory.config.BaseResponse)

	fun onPostChangePasswordFailure(message: String)
}