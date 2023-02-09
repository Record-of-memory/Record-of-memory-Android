package com.recordOfMemory.src.main.myPage

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentMyPageBinding
import com.recordOfMemory.src.main.myPage.retrofit.MyPageInterface
import com.recordOfMemory.src.main.myPage.retrofit.MyPageService
import com.recordOfMemory.src.main.myPage.retrofit.models.DeleteUsersResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.GetUsersResponse
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutRequest
import com.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutResponse
import com.recordOfMemory.src.splash.SplashActivity

class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page),MyPageInterface {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 마이페이지 정보 조회
        MyPageService(this).tryGetUsers()

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
            logoutDialog.dismiss()

            // 로그아웃
            val postSignOutRequest=PostSignOutRequest(refreshToken = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2NzcxNTkxNjF9.ExSfPwle9LtcEh5e4CQIv89Y3hDlwq-_Wib7qIogO1ZeirM9sOze7-eM9REAdCWzwyeJhE8FUlZ2oaZ52Egnng")
            MyPageService(this).tryPostSignOut(postSignOutRequest)
        }

        logoutDialog.show()
    }

    override fun onPostSignOutSuccess(response: PostSignOutResponse) {
        Log.d("성공","${response.information.message}")
        //로그아웃 성공 시 화면은 스플래시 화면으로
        startActivity(Intent(context,SplashActivity::class.java))
    }

    override fun onPostSignOutFailure(message: String) {
        Log.d("실패","$message")
        Toast.makeText(context,"로그아웃 실패",Toast.LENGTH_SHORT).show()
    }

    override fun onGetUsersSuccess(response: GetUsersResponse) {
        binding.mypageBoxName.text=response.information.nickname
        binding.mypageBoxAccount.text=response.information.email
        if(response.information.imageUrl.isNullOrEmpty()){
            binding.mypagePersonImg.setImageResource(R.drawable.icn_person)
        }else{
            Glide.with(this).load(response.information.imageUrl)
                .into(binding.mypagePersonImg)
        }
    }

    override fun onGetUsersFailure(message: String) {
        Log.d("실패","$message")
        Toast.makeText(context,"정보 가져오기 실패",Toast.LENGTH_SHORT).show()
    }
}