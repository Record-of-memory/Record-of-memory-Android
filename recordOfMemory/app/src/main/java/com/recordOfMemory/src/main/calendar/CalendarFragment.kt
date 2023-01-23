package com.recordOfMemory.src.main.calendar

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import com.kizitonwose.calendar.core.*
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentCalendarBinding
import com.recordOfMemory.databinding.CalendarDayContainerBinding
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

class CalendarFragment: BaseFragment<FragmentCalendarBinding>(FragmentCalendarBinding::bind, R.layout.fragment_calendar){

    private val monthCalendarView: CalendarView get() = binding.calendarDays

    private var selectedDate :LocalDate ?= null
    private val today = LocalDate.now()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY)
        binding.calendarWeek.root.children
            .map { it as TextView }
            .forEachIndexed { index, textView ->
                val str=daysOfWeek[index].name
                textView.text = str.substring(0,1) + str.substring(1,2).toLowerCase() // Mo, Tu, We ...
                textView.setTextColorRes(R.color.brownGray)
            }

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)
        val endMonth = currentMonth.plusMonths(100)
        setupMonthCalendar(startMonth, endMonth, currentMonth, daysOfWeek) //다음달로 스크롤 가능

        setMonthChangeBtn()
    }

    private fun setupMonthCalendar(
        startMonth: YearMonth,
        endMonth: YearMonth,
        currentMonth: YearMonth,
        daysOfWeek: List<DayOfWeek>,
    ) {
        class DayViewContainer(view: View) : ViewContainer(view) {
            // Will be set when this container is bound. See the dayBinder.
            lateinit var day: CalendarDay
            val textView = CalendarDayContainerBinding.bind(view).calendarDay

            init {
                view.setOnClickListener {
                    if (day.position == DayPosition.MonthDate) {
                        dateClicked(date = day.date)
                    }
                }
            }
        }
        monthCalendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                bindDate(data.date, container.textView, data.position == DayPosition.MonthDate)
            }
        }
        monthCalendarView.monthScrollListener = {
            updateTitle()
        }
        monthCalendarView.setup(startMonth, endMonth, daysOfWeek.first())
        monthCalendarView.scrollToMonth(currentMonth)
    }

    private fun bindDate(date: LocalDate, textView: TextView, isSelectable: Boolean) {
        textView.text = date.dayOfMonth.toString()
        if (isSelectable) {
            when {
                selectedDate == date -> { //선택한 날
                    textView.setTextColorRes(R.color.colorPrimary)
                    textView.setBackgroundResource(R.drawable.calendar_selected_bg)
                }
                today == date -> {  //오늘
                    textView.setTextColorRes(R.color.colorPrimary)
                    textView.setBackgroundResource(R.drawable.calendar_today_bg)
                }
                else -> { //그 이외의 선택 가능한 날
                    textView.setTextColorRes(R.color.black)
                    textView.background = null
                }
            }
        } else { // 선택 불가능한 날 (이전 달, 이후 달)
            textView.setTextColorRes(R.color.brownGray)
            textView.background = null
        }
    }

    private fun dateClicked(date: LocalDate) {
        if (selectedDate == date ) { //원래 선택했던 것이라면 취소
            selectedDate = null
        } else { //그게 아니라면 항상 새로 선택한 것으로 추가
            selectedDate = date
            Toast.makeText(context,"$date", Toast.LENGTH_SHORT).show()
        }

        //monthCalendarView.notifyDateChanged(date) //지정된 "날짜만" 다시 로드
        //monthCalendarView.notifyMonthChanged(date.yearMonth)
        monthCalendarView.notifyCalendarChanged()
    }

    private fun updateTitle() {
        val month = monthCalendarView.findFirstVisibleMonth()?.yearMonth ?: return
        val yearText=month.year.toString() + "년"
        val monthText=monthToInt(month.month.displayText(short = true)).toString() + "월"
        binding.calendarYearMonth.text = "$yearText $monthText"
    }

    private fun setMonthChangeBtn(){
        binding.calendarDotPastMonth.setOnClickListener {
            binding.calendarDays.findFirstVisibleMonth()?.let{
                binding.calendarDays.smoothScrollToMonth(it.yearMonth.previousMonth)
            }
        }

        binding.calendarDotNextMonth.setOnClickListener {
            binding.calendarDays.findFirstVisibleMonth()?.let{
                binding.calendarDays.smoothScrollToMonth(it.yearMonth.nextMonth)
            }
        }
    }


}