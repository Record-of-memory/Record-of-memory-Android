package kr.co.app.recordOfMemory.src.main.home

import kr.co.app.recordOfMemory.config.BaseResponse
import kr.co.app.recordOfMemory.src.main.home.models.UserResponse

interface HomeFragmentInterface {

    fun onGetUserSuccess(response: UserResponse)

    fun onGetUserFailure(message: String)

    fun onPostSignUpSuccess(response: BaseResponse)

    fun onPostSignUpFailure(message: String)
}