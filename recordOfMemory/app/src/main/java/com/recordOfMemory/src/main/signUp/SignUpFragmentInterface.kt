package com.recordOfMemory.src.main.signUp

import com.recordOfMemory.src.main.signUp.models.*

interface SignUpFragmentInterface {
    fun onPostSignUpSuccess(response: SignUpResponse)

    fun onPostSignUpFailure(message: String)

    fun onPostSignInSuccess(response: SignInResponse)

    fun onPostSignInFailure(message: String)

    fun onPostRefreshSuccess(response: RefreshResponse)

    fun onPostRefreshFailure(message: String)

    fun onPostChangePasswordSuccess(response: ChangePasswordResponse)

    fun onPostChangePasswordFailure(message: String)

    fun onGetUserEmailCheckSuccess(response: UserEmailCheckResponse)

    fun onGetUserEmailCheckFailure(message: String)
}