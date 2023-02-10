package com.recordOfMemory.src.main.myPage.retrofit

import com.recordOfMemory.src.main.myPage.retrofit.models.GetUsersResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutResponse

interface MyPageInterface {
	fun onPostSignOutSuccess(response: PostSignOutResponse)
	fun onPostSignOutFailure(message:String)

	fun onGetUsersSuccess(response: GetUsersResponse)
	fun onGetUsersFailure(message: String)

}