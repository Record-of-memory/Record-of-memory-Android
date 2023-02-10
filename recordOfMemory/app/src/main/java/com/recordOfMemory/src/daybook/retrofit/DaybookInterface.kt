package com.recordOfMemory.src.daybook.retrofit

import com.recordOfMemory.config.BaseResponse

interface DaybookInterface {
    fun onPostRecordSuccess(response: BaseResponse)
    fun onPostRecordFailure(response: String)
}