package com.recordOfMemory.src.main.myPage.retrofit

import com.recordOfMemory.src.main.myPage.retrofit.models.DeleteUsersResponse
import com.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutResponse

interface MyPageEditInterface {
	fun onDeleteUsersSuccess(response: DeleteUsersResponse)
	fun onDeleteUsersFailure(message: String)
}