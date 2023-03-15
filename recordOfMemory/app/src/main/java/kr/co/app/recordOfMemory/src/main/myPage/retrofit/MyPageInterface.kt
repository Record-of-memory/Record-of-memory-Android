package kr.co.app.recordOfMemory.src.main.myPage.retrofit

import kr.co.app.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import kr.co.app.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutResponse

interface MyPageInterface {
	fun onPostSignOutSuccess(response: PostSignOutResponse)
	fun onPostSignOutFailure(message:String)

	fun onGetUsersSuccess(response: GetUsersResponse)
	fun onGetUsersFailure(message: String)

}