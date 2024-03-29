package kr.co.app.recordOfMemory.src.main.calendar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.kizitonwose.calendar.core.*
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import kr.co.app.recordOfMemory.R
import kr.co.app.recordOfMemory.config.BaseFragment
import kr.co.app.recordOfMemory.config.ApplicationClass
import kr.co.app.recordOfMemory.databinding.FragmentCalendarBinding
import kr.co.app.recordOfMemory.databinding.CalendarDayContainerBinding
import kr.co.app.recordOfMemory.src.daybook.DaybookActivity
import kr.co.app.recordOfMemory.src.main.calendar.recycler.CalendarOutRecyclerViewAdapter
import kr.co.app.recordOfMemory.src.main.calendar.retrofit.CalendarInterface
import kr.co.app.recordOfMemory.src.main.calendar.retrofit.CalendarService
import kr.co.app.recordOfMemory.src.main.calendar.retrofit.models.GetCalendarDiariesRequest
import kr.co.app.recordOfMemory.src.main.calendar.retrofit.models.GetCalendarDiariesResponse
import kr.co.app.recordOfMemory.src.main.calendar.retrofit.models.Record
import kr.co.app.recordOfMemory.src.main.signUp.models.PostRefreshRequest
import kr.co.app.recordOfMemory.src.main.signUp.models.TokenResponse
import kr.co.app.recordOfMemory.src.main.signUp.retrofit.GetRefreshTokenInterface
import kr.co.app.recordOfMemory.src.main.signUp.retrofit.SignUpService
import kr.co.app.recordOfMemory.src.splash.SplashActivity
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

class CalendarFragment: BaseFragment<FragmentCalendarBinding>(FragmentCalendarBinding::bind, R.layout.fragment_calendar),
    CalendarInterface, GetRefreshTokenInterface {

    private val monthCalendarView: CalendarView get() = binding.calendarDays

    private var selectedDate :LocalDate ?= LocalDate.now()
//    lateinit var diary2RecyclerViewAdapter : CalendarRecyclerViewAdapter
    var statusCode = 1000
    var request : Any = ""

    inner class itemListAdapterToList {
        // 일기 open function
        fun getItemId(item: Record) {
//            openItem(item)
            println(item)
            startActivity(
                Intent(activity, DaybookActivity::class.java)
                .putExtra("item", item as java.io.Serializable)
                .putExtra("recordId", item.id))
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

        showLoadingDialog(requireContext())
        val getCalendarDiariesRequest = GetCalendarDiariesRequest(date = selectedDate.toString())
        CalendarService(this).tryGetRecordsDate(getCalendarDiariesRequest)
//<<<<<<< Updated upstream
//
//        // recycler view 연결
//        val items = itemListAdapterToList()
//        val diary2LayoutManager = LinearLayoutManager(context)
//
//        val itemList = ArrayList<GetMemberRecordResponse>()
//
//        diary2RecyclerViewAdapter = CalendarRecyclerViewAdapter(items, itemList)
//        binding.calendarRecyclerView.apply {
//            layoutManager = diary2LayoutManager
//            adapter = diary2RecyclerViewAdapter
//        }
//=======
//>>>>>>> Stashed changes
    }

//    private fun getSectionCallback(): StickyHeaderItemDecoration.SectionCallback {
//        return object : StickyHeaderItemDecoration.SectionCallback {
//            override fun isHeader(position: Int): Boolean {
//                return adapter.isHeader(position)
//            }
//
//            override fun getHeaderLayoutView(list: RecyclerView, position: Int): View? {
//                return adapter.getHeaderView(list, position)
//            }
//        }
//    }

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
            when (selectedDate) {
                date -> { //선택한 날
                    textView.setTextColorRes(R.color.colorPrimary)
                    textView.setBackgroundResource(R.drawable.calendar_selected_bg)
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
        if (selectedDate != date ) { //원래 선택했던 것이라면 취소
            selectedDate = date
            // LocalDate 파싱
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val selectedDateString = date.format(formatter)

            println("date: $selectedDateString")

            showLoadingDialog(requireContext())
            val getCalendarDiariesRequest = GetCalendarDiariesRequest(date = selectedDateString)
            CalendarService(this).tryGetRecordsDate(getCalendarDiariesRequest)

//            diary2RecyclerViewAdapter.filter.filter(selectedDateString)
//            binding.calendarRecyclerView.adapter = diary2RecyclerViewAdapter
//            Toast.makeText(context,"$date", Toast.LENGTH_SHORT).show()

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


    override fun onGetRecordsDateSuccess(getCalendarDiariesResponse: GetCalendarDiariesResponse) {
        dismissLoadingDialog()
        val calendarRecordsList = getCalendarDiariesResponse.information

        binding.calendarRecyclerView.isVisible = true
        binding.calendarEmpty.isVisible=false

        println("calendarRecordsList: $calendarRecordsList")

        // recycler view 연결
        val items = itemListAdapterToList()
        val calendarLayoutManager = LinearLayoutManager(context)

        val calendarOutRecyclerViewAdapter = CalendarOutRecyclerViewAdapter(items, calendarRecordsList)
        binding.calendarRecyclerView.adapter = calendarOutRecyclerViewAdapter
        binding.calendarRecyclerView.apply {
            layoutManager = calendarLayoutManager
            adapter = calendarOutRecyclerViewAdapter
        }
    }

    override fun onGetRecordsDateFailure(message: String) {
        dismissLoadingDialog()
        if (message == "refreshToken") {
            showCustomToast("로그인이 만료되었습니다.")
            val X_REFRESH_TOKEN = ApplicationClass.sSharedPreferences.getString(
                ApplicationClass.X_REFRESH_TOKEN, "").toString()
            SignUpService(this).tryPostRefresh(PostRefreshRequest(X_REFRESH_TOKEN))
        }
        // 토큰 갱신 문제가 아닐 경우 - 해당 날짜에 일기가 없음
        else {
            Log.d("실패",message)
            binding.calendarEmpty.isVisible=true
            binding.calendarRecyclerView.isVisible = false
        }
    }

    override fun onPostRefreshSuccess(response: TokenResponse) {
        val editor = ApplicationClass.sSharedPreferences.edit()
        editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.information.accessToken)
        editor.putString(ApplicationClass.X_REFRESH_TOKEN, response.information.refreshToken)
        editor.apply()

        when (statusCode) {
            1000 -> CalendarService(this).tryGetRecordsDate(request as GetCalendarDiariesRequest)
        }
    }

    override fun onPostRefreshFailure(message: String) {
        dismissLoadingDialog()
        val editor = ApplicationClass.sSharedPreferences.edit()
        editor.remove(ApplicationClass.X_ACCESS_TOKEN)
        editor.remove(ApplicationClass.X_REFRESH_TOKEN)
        editor.apply()

        val intent = Intent(context, SplashActivity::class.java)
        requireActivity().finishAffinity()
        startActivity(intent)
    }
}