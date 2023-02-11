package com.recordOfMemory.src.main.signUp.retrofit

import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.src.main.signUp.models.*

interface SignUpFragmentInterface {
    fun onPostSignUpSuccess(response: BaseResponse)

    fun onPostSignUpFailure(message: String)

    fun onPostSignInSuccess(response: TokenResponse)

    fun onPostSignInFailure(message: String)

    fun onPostChangePasswordSuccess(response: BaseResponse)

    fun onPostChangePasswordFailure(message: String)

    fun onGetUserEmailCheckSuccess(response: UserEmailCheckResponse)

    fun onGetUserEmailCheckFailure(message: String)
}