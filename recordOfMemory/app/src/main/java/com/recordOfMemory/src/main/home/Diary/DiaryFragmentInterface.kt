package com.recordOfMemory.src.main.home.Diary

import com.recordOfMemory.src.main.home.Diary.retrofit.models.GetDiariesResponse
import com.recordOfMemory.src.main.home.Diary.retrofit.models.PostDiariesResponse

interface DiaryFragmentInterface {

    fun onGetDiariesSuccess(response: GetDiariesResponse)

    fun onGetDiariesFailure(message: String)

    fun onPostDiariesSuccess(response: PostDiariesResponse)

    fun onPostDiariesFailure(message: String)
}