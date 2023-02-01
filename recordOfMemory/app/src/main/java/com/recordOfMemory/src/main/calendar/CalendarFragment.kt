package com.recordOfMemory.src.main.calendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import com.kizitonwose.calendar.core.*
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentCalendarBinding
import com.recordOfMemory.databinding.CalendarDayContainerBinding
import com.recordOfMemory.src.daybook.DaybookActivity
import com.recordOfMemory.src.main.calendar.recycler.CalendarRecyclerViewAdapter
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetDiary2Response
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class CalendarFragment: BaseFragment<FragmentCalendarBinding>(FragmentCalendarBinding::bind, R.layout.fragment_calendar){

    private val monthCalendarView: CalendarView get() = binding.calendarDays

    private var selectedDate :LocalDate ?= null
    private val today = LocalDate.now()
    lateinit var diary2RecyclerViewAdapter : CalendarRecyclerViewAdapter


    inner class itemListAdapterToList {
        // 일기 open function
        fun getItemId(item: GetDiary2Response) {
//            openItem(item)
            println(item)
            startActivity(
                Intent(activity, DaybookActivity::class.java)
                .putExtra("item", item))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY)
        binding.calendarWeek.root.children
            .map { it as TextView }
            .forEachIndexed { index, textView ->
                val str=daysOfWeek[index].name
                textView.text = str.substring(0,1) + str.substring(1,2).lowercase(Locale.ROOT) // Mo, Tu, We ...
                textView.setTextColorRes(R.color.brownGray)
            }

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)
        val endMonth = currentMonth.plusMonths(100)
        setupMonthCalendar(startMonth, endMonth, currentMonth, daysOfWeek) //다음달로 스크롤 가능

        setMonthChangeBtn()

        // recycler view 연결
        val items = itemListAdapterToList()
        val diary2LayoutManager = LinearLayoutManager(context)

        val itemList = ArrayList<GetDiary2Response>()

        itemList.add(
            GetDiary2Response(itemId = "1", title = "ss", content = "content", date = "23.01.01",writer = "구리",
                imgUrl = "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ")
        )

        itemList.add(
            GetDiary2Response(itemId = "1", title = "ss", content = "content", date = "23.01.01",writer = "구리",
                imgUrl = "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ")
        )

        itemList.add(
            GetDiary2Response(itemId = "1", title = "ss", content = "content", date = "23.01.01",writer = "구리",
                imgUrl = "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ")
        )

        diary2RecyclerViewAdapter = CalendarRecyclerViewAdapter(items, itemList)
        binding.calendarRecyclerView.apply {
            layoutManager = diary2LayoutManager
            adapter = diary2RecyclerViewAdapter
        }
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
            // LocalDate 파싱
            val formatter = DateTimeFormatter.ofPattern("yy.MM.dd")
            val selectedDateString = date.format(formatter)
            println("date: $selectedDateString")
            diary2RecyclerViewAdapter.filter.filter(selectedDateString)
            binding.calendarRecyclerView.adapter = diary2RecyclerViewAdapter
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

    private fun setMonthChangeBtn() {
        binding.calendarDotPastMonth.setOnClickListener {
            binding.calendarDays.findFirstVisibleMonth()?.let {
                binding.calendarDays.smoothScrollToMonth(it.yearMonth.previousMonth)
            }
        }

        binding.calendarDotNextMonth.setOnClickListener {
            binding.calendarDays.findFirstVisibleMonth()?.let {
                binding.calendarDays.smoothScrollToMonth(it.yearMonth.nextMonth)
            }
        }
    }
}