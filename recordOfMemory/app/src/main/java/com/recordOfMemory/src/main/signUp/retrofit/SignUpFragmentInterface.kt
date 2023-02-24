package com.recordOfMemory.src.main.signUp.retrofit

import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.src.main.signUp.models.*

interface SignUpFragmentInterface {
    fun onPostSignUpSuccess(response: BaseResponse)

    fun onPostSignUpFailure(message: String)

    fun onPostSignInSuccess(response: TokenResponse)

    fun onPostSignInWrong(message: String)

    fun onPostSignInFailure(message: String)

    fun onPostChangePasswordSuccess(response: BaseResponse)

    fun onPostChangePasswordFailure(message: String)

    fun onGetUserEmailCheckNoTokenExist(response: UserEmailCheckNoTokenResponse)

    fun onGetUserEmailCheckNoTokenNotExist(message: String)

    fun onGetUserEmailCheckNoTokenFailure(message: String)

    fun onPostResetPasswordSuccess(response: BaseResponse)

    fun onPostResetPasswordWrong(message: String)

    fun onPostResetPasswordFailure(message: String)
}