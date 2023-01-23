package com.recordOfMemory.src.main

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseActivity
import com.recordOfMemory.databinding.ActivityMainBinding
import com.recordOfMemory.src.main.calendar.CalendarFragment
import com.recordOfMemory.src.main.home.HomeFragment
import com.recordOfMemory.src.main.myPage.MyPageFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()

        binding.mainBtmNav.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_main_btm_nav_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, HomeFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_main_btm_nav_calendar -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, CalendarFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_main_btm_nav_mypage -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, MyPageFragment())
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
}