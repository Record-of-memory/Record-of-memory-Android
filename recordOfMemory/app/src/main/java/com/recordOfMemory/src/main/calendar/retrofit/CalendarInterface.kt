package com.recordOfMemory.src.main.calendar.retrofit

import com.recordOfMemory.src.main.calendar.retrofit.models.GetCalendarDiariesResponse

interface CalendarInterface {
    fun onGetRecordsDateSuccess(getCalendarDiariesResponse: GetCalendarDiariesResponse)
    fun onGetRecordsDateFailure(message: String)
}