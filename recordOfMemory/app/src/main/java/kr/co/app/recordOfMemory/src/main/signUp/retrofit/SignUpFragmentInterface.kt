package kr.co.app.recordOfMemory.src.main.signUp.retrofit

import kr.co.app.recordOfMemory.src.main.signUp.models.*

interface SignUpFragmentInterface {
    fun onPostSignUpSuccess(response: kr.co.app.recordOfMemory.config.BaseResponse)

    fun onPostSignUpFailure(message: String)

    fun onPostSignInSuccess(response: TokenResponse)

    fun onPostSignInWrong(message: String)

    fun onPostSignInFailure(message: String)

    fun onPostChangePasswordSuccess(response: kr.co.app.recordOfMemory.config.BaseResponse)

    fun onPostChangePasswordFailure(message: String)

    fun onGetUserEmailCheckNoTokenExist(response: UserEmailCheckNoTokenResponse)

    fun onGetUserEmailCheckNoTokenNotExist(message: String)

    fun onGetUserEmailCheckNoTokenFailure(message: String)

    fun onPostResetPasswordSuccess(response: kr.co.app.recordOfMemory.config.BaseResponse)

    fun onPostResetPasswordWrong(message: String)

    fun onPostResetPasswordFailure(message: String)
}