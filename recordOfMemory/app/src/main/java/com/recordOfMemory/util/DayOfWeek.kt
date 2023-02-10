package com.recordOfMemory.util

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//enum class DayOfWeek {
//    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
//}

fun getDateTime(item : String) : LocalDateTime {
    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    return LocalDateTime.parse(item, formatter)
}

fun getDayOfWeek(dayOfWeek: DayOfWeek) : String {
    return when(dayOfWeek) {
        DayOfWeek.MONDAY -> "월"
        DayOfWeek.TUESDAY -> "화"
        DayOfWeek.WEDNESDAY -> "수"
        DayOfWeek.THURSDAY -> "목"
        DayOfWeek.FRIDAY -> "금"
        DayOfWeek.SATURDAY -> "토"
        DayOfWeek.SUNDAY -> "일"
    }
}