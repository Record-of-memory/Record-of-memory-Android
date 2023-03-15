package kr.co.app.recordOfMemory.src.main.signUp.retrofit

import kr.co.app.recordOfMemory.src.main.signUp.models.TokenResponse

interface GetRefreshTokenInterface {
    fun onPostRefreshSuccess(response: TokenResponse)

    fun onPostRefreshFailure(message: String)
}