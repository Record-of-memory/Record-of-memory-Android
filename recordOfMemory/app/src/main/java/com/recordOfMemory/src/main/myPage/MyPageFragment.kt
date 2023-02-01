package com.recordOfMemory.src.main.myPage

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentMyPageBinding
import com.recordOfMemory.src.main.MainActivity
import com.recordOfMemory.src.main.calendar.CalendarFragment
import com.recordOfMemory.src.main.home.diary2.Diary2SearchFragment
import com.recordOfMemory.src.splash.SplashActivity

class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

        binding.mypageEditBtn.setOnClickListener {
            transaction
                .replace(R.id.main_frm, MyPageEditFragment())
                .addToBackStack(null)
                .commit()
            transaction.isAddToBackStackAllowed
        }

        binding.mypageLogout.setOnClickListener {
            logoutDialogFunction()
        }
    }

    private fun logoutDialogFunction(){
        val logoutDialog = Dialog(requireContext())
        logoutDialog.setContentView(R.layout.dialog_custom)
        logoutDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        logoutDialog.findViewById<TextView>(R.id.dialog1_title).text = "로그아웃하시겠어요?"

        logoutDialog.findViewById<TextView>(R.id.dialog1_btn_cancel).setOnClickListener {
            // dialog 내림
            logoutDialog.dismiss()
        }

        val logoutBtn=logoutDialog.findViewById<TextView>(R.id.dialog1_btn_delete)
        logoutBtn.text="로그아웃"
        logoutBtn.setOnClickListener {
            //로그아웃
            Toast.makeText(context, "로그아웃", Toast.LENGTH_SHORT).show()
            logoutDialog.dismiss()

            // 로그아웃 하고

            //화면은 스플래시 화면으로
            startActivity(Intent(context,SplashActivity::class.java))
        }

        logoutDialog.show()
    }
}