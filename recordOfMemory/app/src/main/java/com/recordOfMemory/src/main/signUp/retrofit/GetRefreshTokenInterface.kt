package com.recordOfMemory.src.main.signUp.retrofit

import com.recordOfMemory.src.main.signUp.models.TokenResponse

interface GetRefreshTokenInterface {
    fun onPostRefreshSuccess(response: TokenResponse)

    fun onPostRefreshFailure(message: String)
}