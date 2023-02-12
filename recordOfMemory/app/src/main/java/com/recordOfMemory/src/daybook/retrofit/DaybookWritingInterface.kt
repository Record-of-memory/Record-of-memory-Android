package com.recordOfMemory.src.daybook.retrofit

import com.recordOfMemory.config.BaseResponse

interface DaybookWritingInterface {
    fun onPatchRecordSuccess(response: BaseResponse)
    fun onPatchRecordFailure(message: String)

    fun onPostRecordSuccess(response: BaseResponse)
    fun onPostRecordFailure(response: String)
}