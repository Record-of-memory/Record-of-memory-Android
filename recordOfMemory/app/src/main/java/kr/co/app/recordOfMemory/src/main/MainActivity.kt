package kr.co.app.recordOfMemory.src.main

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.isGone
import androidx.core.view.isVisible
import kr.co.app.recordOfMemory.R
import kr.co.app.recordOfMemory.config.ApplicationClass
import kr.co.app.recordOfMemory.config.BaseActivity
import kr.co.app.recordOfMemory.config.BaseResponse
import kr.co.app.recordOfMemory.databinding.ActivityMainBinding
import kr.co.app.recordOfMemory.src.daybook.retrofit.DaybookService
import kr.co.app.recordOfMemory.src.daybook.retrofit.DaybookWritingInterface
import kr.co.app.recordOfMemory.src.main.calendar.CalendarFragment
import kr.co.app.recordOfMemory.src.main.home.diary.DiaryFragmentInterface
import kr.co.app.recordOfMemory.src.main.home.diary.DiaryTogetherFragment
import kr.co.app.recordOfMemory.src.main.home.diary.retrofit.DiaryService
import kr.co.app.recordOfMemory.src.main.home.diary.retrofit.models.GetDiariesResponse
import kr.co.app.recordOfMemory.src.main.home.diary.retrofit.models.PostDiariesRequest
import kr.co.app.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import kr.co.app.recordOfMemory.src.main.myPage.MyPageFragment
import kr.co.app.recordOfMemory.src.main.onboarding.OnboardingFragment1
import kr.co.app.recordOfMemory.src.main.onboarding.OnboardingFragment2
import kr.co.app.recordOfMemory.src.main.onboarding.OnboardingFragment3
import kr.co.app.recordOfMemory.src.main.onboarding.OnboardingFragment4
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.time.LocalDate

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    DiaryFragmentInterface, DaybookWritingInterface {

    //https://sbe03005dev.tistory.com/entry/Android-Kotlin-%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%BD%94%ED%8B%80%EB%A6%B0-Fragment-%EC%83%81%ED%83%9C-%EC%9C%A0%EC%A7%80
    //replace 대신 add, show, hide

    private var fragment1 = OnboardingFragment1()
    private var fragment2 = OnboardingFragment2()
    private var fragment3 = OnboardingFragment3()
    private var fragment4 = OnboardingFragment4()
    var title = ""
    var content = ""
    var diaryName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isFirst = ApplicationClass.sSharedPreferences.getString("isFirst", null)
//        val isFirst = null


        if(isFirst == null) {
            hideMainFragment(true)
            // 온보딩 레이아웃 보이게 하기
            val transaction = supportFragmentManager.beginTransaction()
                .replace(binding.onboardingFrm.id, fragment1)
            transaction.commit()
            val edit = ApplicationClass.sSharedPreferences.edit()
            edit.putString("isFirst", "YES")
            edit.apply()
        }
        else {
            hideMainFragment(false)
            startMainFrame()
        }
    }

    // 온보딩 fragment 바꾸는 메소드
    fun openFragmentOnOnboarding(int: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        when(int){
            1 -> transaction.replace(binding.onboardingFrm.id, fragment1).addToBackStack(null)
            2 -> transaction.replace(binding.onboardingFrm.id, fragment2).addToBackStack(null)
            3 -> transaction.replace(binding.onboardingFrm.id, fragment3).addToBackStack(null)
            4 -> transaction.replace(binding.onboardingFrm.id, fragment4).addToBackStack(null)
        }
        transaction.commit()
    }

    //메인 레이아웃 안보이게 하기
    fun hideMainFragment(bool: Boolean) {
        if (bool)  {
            // 안보이게
            binding.mainFrm.isGone = true
            binding.mainBtmNav.isGone = true
        } else {
            // 보이게
            binding.onboardingFrm.isGone = true
            binding.mainBtmNav.isVisible = true
            binding.mainFrm.isVisible = true
        }
    }

    fun startMainFrame() {
        hideMainFragment(false)
        val diaryTogetherFragment = DiaryTogetherFragment()
        val calendarFragment = CalendarFragment()
        val myPageFragment = MyPageFragment()

        binding.mainBtmNav.run {
            println("MAIN BOTTOM NAVIGATION")
            setOnItemSelectedListener { item ->
//                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                when (item.itemId) {
                    R.id.menu_main_btm_nav_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, diaryTogetherFragment)
//                            .replace(R.id.main_frm, Diary2Fragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_main_btm_nav_calendar -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, calendarFragment)
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_main_btm_nav_mypage -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, myPageFragment)
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            selectedItemId = R.id.menu_main_btm_nav_home
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
    fun setString(key : String, value : String) {
        if(key == "title") {
            title = value
        }
        else if(key == "content") {
            content = value
        }
        else if(key == "diaryName") {
            diaryName = value
        }
    }
    fun makeDiaryAndRecord() {
        var newItem = PostDiariesRequest(name = diaryName, diaryType= "ALONE")
        showLoadingDialog(this)
        DiaryService(this).tryPostDiaries(newItem)
    }

    override fun onGetDiariesSuccess(response: GetDiariesResponse) {
        val diaryId = response.information[0].id
        val jsonObject = JSONObject(
            "{" +
                    "\"diaryId\":\"${diaryId}\"," +
                    "\"date\":\"${LocalDate.now()}\"," +
                    "\"title\":\"$title\"," +
                    "\"content\":\"$content\"" +
                    "}").toString()
        val jsonBody = jsonObject.toRequestBody("application/json".toMediaTypeOrNull())

        DaybookService(daybookWritingInterface = this).tryPostRecord(writeRecordReq = jsonBody, imgUrl = null)
    }

    override fun onGetDiariesFailure(message: String) {
    }

    override fun onPostDiariesSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        DiaryService(this).tryGetDiaries()
    }

    override fun onPostDiariesFailure(message: String) {
    }

    override fun onGetUsersSuccess(response: GetUsersResponse) {
    }

    override fun onGetUsersFailure(message: String) {
    }

    override fun onPatchRecordSuccess(response: BaseResponse) {
    }

    override fun onPatchRecordFailure(message: String) {
    }

    override fun onPostRecordSuccess(response: BaseResponse) {
        startMainFrame()
    }

    override fun onPostRecordFailure(response: String) {
    }
}
