package com.recordOfMemory.src.main.home.diary2.likes

import com.recordOfMemory.src.main.home.models.SignUpResponse

interface LikesInterface {
    fun onPostLikesSuccess(response: LikesResponse)

    fun onPostLikesFailure(message: String)
}