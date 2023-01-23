package com.recordOfMemory.src.main.myPage

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentMyPageBinding
import com.recordOfMemory.src.main.MainActivity
import com.recordOfMemory.src.main.calendar.CalendarFragment

class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mypageEditBtn.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, MyPageEditFragment())
                .commitAllowingStateLoss()
        }

        binding.mypageLogout.setOnClickListener {
            logoutDialogFunction()
        }
    }

    private fun logoutDialogFunction(){
        val logoutDialog = Dialog(requireContext())
        logoutDialog.setContentView(R.layout.dialog_custom)

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
        }

        logoutDialog.show()
    }
}