package com.recordOfMemory.src.main.home.diary

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.recordOfMemory.R
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.databinding.FragmentDiaryTogetherBinding
import com.recordOfMemory.src.main.friends.FriendsListFragment
import com.recordOfMemory.src.main.home.diary.retrofit.models.GetDiariesResponse
import com.recordOfMemory.src.main.home.diary.retrofit.models.PostDiariesRequest
import com.recordOfMemory.src.main.home.diary.retrofit.DiaryService
import com.recordOfMemory.src.main.home.diary.retrofit.models.ResultDiaries
import com.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import com.recordOfMemory.src.main.signUp.models.PostRefreshRequest
import com.recordOfMemory.src.main.signUp.models.TokenResponse
import com.recordOfMemory.src.main.signUp.retrofit.GetRefreshTokenInterface
import com.recordOfMemory.src.main.signUp.retrofit.SignUpService
import com.recordOfMemory.src.splash.SplashActivity

class DiaryTogetherFragment : BaseFragment<FragmentDiaryTogetherBinding>(FragmentDiaryTogetherBinding::bind, R.layout.fragment_diary_together),
    DiaryFragmentInterface, GetRefreshTokenInterface {

    var statusCode = 1000
    var request : Any = ""

    override fun onPause() {
        super.onPause()
        Log.e("onPause", "onPause")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.diaryBtnTogether.isSelected = true
        binding.diaryBtnAlone.isSelected = false

        binding.diaryRv.isGone = true
        binding.diaryIvNone.isGone = true
        binding.diaryTvNone.isGone= true


        showLoadingDialog(requireContext())
        statusCode = 1000
        DiaryService(this).tryGetUsers()

        binding.diaryBtnAlone.setOnClickListener { //혼자쓰는 으로 전환
            val fm = requireActivity().supportFragmentManager
            val transaction: FragmentTransaction = fm.beginTransaction()
            transaction
                .replace(R.id.main_frm, fm.findFragmentByTag("diaryAloneFragment") ?: DiaryAloneFragment(), "diaryAloneFragment")
                .commit()
            transaction.isAddToBackStackAllowed
        }

        binding.iconDiaryAdd.setOnClickListener { //새 다이어리 추가 버튼 누를시
            addNewDiaryFunction()
            binding.diaryIvNone.isGone = true
            binding.diaryTvNone.isGone= true
        }
    }

    private fun addNewDiaryFunction() {
//        val fm = requireActivity().supportFragmentManager //친구 리스트 테스트용
//        val transaction: FragmentTransaction = fm.beginTransaction()
//        transaction
//            .replace(R.id.main_frm, fm.findFragmentByTag("FriendsListFragment") ?: FriendsListFragment(), "FriendsListFragment")
//            .commit()
//        transaction.isAddToBackStackAllowed
        val mDialogView = Dialog(requireContext())
        mDialogView.setContentView(R.layout.fragment_pop_diary)
        mDialogView.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialogView.show()

        val noButton = mDialogView.findViewById<Button>(R.id.pop_btn_close)
        noButton.setOnClickListener {
            mDialogView.dismiss()
        }

        val confirm = mDialogView.findViewById<Button>(R.id.pop_btn_confirm)

        confirm.setOnClickListener() {
            val newItemName = mDialogView.findViewById<EditText>(R.id.pop_et_name).text.toString()
            if (newItemName.length in 1..9){
                statusCode = 2000
                var newItem = PostDiariesRequest(name = newItemName, diaryType= "WITH")
                DiaryService(this).tryPostDiaries(newItem)
                mDialogView.dismiss()
            } else{
                val alarm = mDialogView.findViewById<TextView>(R.id.pop_tv_alarm)
                alarm.text = "10자 이내로 설정해주세요"
            }
        }
    }

    private fun diariesRefresh(){
        DiaryService(this).tryGetDiaries()
    }
    override fun onGetDiariesSuccess(response: GetDiariesResponse) {
        dismissLoadingDialog()
        val data = response.information

        val filterData = data.filter { it.diaryType=="WITH" }
        if (!filterData.isEmpty()) {
            binding.diaryRv.visibility=View.VISIBLE
            val diaryAdapter = DiaryAdapter(filterData as ArrayList<ResultDiaries>)
            binding.diaryRv.adapter = diaryAdapter
            diaryAdapter.notifyDataSetChanged()
            val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
            binding.diaryRv.layoutManager = manager
        } else{
            binding.diaryTvNone.isVisible = true
            binding.diaryIvNone.isVisible= true
        }
    }

    override fun onGetDiariesFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onPostDiariesSuccess(response: BaseResponse) {
        diariesRefresh()
    }

    override fun onPostDiariesFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onGetUsersSuccess(response: GetUsersResponse) {
        Log.e("onGetUsersSuccess", response.toString())
        dismissLoadingDialog()
        val nickname = response.information.nickname
        binding.diaryTvTitle.text = nickname + "님의 기억을 기록할\n다이어리를 골라보세요!"

        statusCode = 2000
        showLoadingDialog(requireContext())
        diariesRefresh()
    }

    override fun onGetUsersFailure(message: String) {
        if(message == "refreshToken") {
            statusCode = 1000
            val X_REFRESH_TOKEN =
                ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_REFRESH_TOKEN, "")
                    .toString()

            SignUpService(this).tryPostRefresh(PostRefreshRequest(X_REFRESH_TOKEN))

        }
        // 토큰 갱신 문제가 아닐 경우
        else {
            //TODO
        }
    }
    override fun onPostRefreshSuccess(response: TokenResponse) {
        val editor = ApplicationClass.sSharedPreferences.edit()
        editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.information.accessToken)
        editor.putString(ApplicationClass.X_REFRESH_TOKEN, response.information.refreshToken)
        editor.apply()

        when(statusCode) {
            1000 -> DiaryService(this).tryGetUsers()
            2000 -> DiaryService(this).tryGetDiaries()
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