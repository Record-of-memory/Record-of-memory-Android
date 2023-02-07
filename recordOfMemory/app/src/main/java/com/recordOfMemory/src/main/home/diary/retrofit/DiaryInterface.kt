package com.recordOfMemory.src.main.home.diary.retrofit

import com.recordOfMemory.src.main.home.diary.retrofit.models.PostDiaryResponse

interface DiaryInterface {
    fun onPostDiarySuccess(response : PostDiaryResponse)
    fun onPostDiaryFailure(message: String)
}