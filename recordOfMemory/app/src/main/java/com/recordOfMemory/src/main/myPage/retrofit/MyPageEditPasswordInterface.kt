package com.recordOfMemory.src.main.myPage.retrofit

import com.recordOfMemory.config.BaseResponse

interface MyPageEditPasswordInterface {
	fun onPostChangePasswordSuccess(response: BaseResponse)

	fun onPostChangePasswordFailure(message: String)
}