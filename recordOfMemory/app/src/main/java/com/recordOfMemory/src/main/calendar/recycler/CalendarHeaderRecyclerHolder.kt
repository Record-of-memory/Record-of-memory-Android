package com.recordOfMemory.src.main.calendar.recycler

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.recordOfMemory.R

class CalendarHeaderRecyclerHolder(context: Context) : FrameLayout(context) {
    var diaryName : TextView

    init {
        inflate(context, R.layout.item_calendar_header, this)
        diaryName = findViewById(R.id.item_calendar_header_tv_diary_name)
    }
    fun setDiaryName(diaryName: String) {
        this.diaryName.text = diaryName
    }

}