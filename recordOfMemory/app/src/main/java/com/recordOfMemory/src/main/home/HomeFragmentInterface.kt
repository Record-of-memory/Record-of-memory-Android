package com.recordOfMemory.src.main.home

import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.src.main.home.models.UserResponse

interface HomeFragmentInterface {

    fun onGetUserSuccess(response: UserResponse)

    fun onGetUserFailure(message: String)

    fun onPostSignUpSuccess(response: BaseResponse)

    fun onPostSignUpFailure(message: String)
}