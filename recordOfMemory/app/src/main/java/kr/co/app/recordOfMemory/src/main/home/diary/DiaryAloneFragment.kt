package kr.co.app.recordOfMemory.src.main.home.diary

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
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import kr.co.app.recordOfMemory.R
import kr.co.app.recordOfMemory.config.BaseFragment
import kr.co.app.recordOfMemory.databinding.FragmentDiaryAloneBinding
import kr.co.app.recordOfMemory.src.main.home.diary.retrofit.models.ResultDiaries
import kr.co.app.recordOfMemory.src.main.home.diary.retrofit.DiaryService
import kr.co.app.recordOfMemory.src.main.home.diary.retrofit.models.GetDiariesResponse
import kr.co.app.recordOfMemory.src.main.home.diary.retrofit.models.PostDiariesRequest
import kr.co.app.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import kr.co.app.recordOfMemory.src.main.signUp.models.PostRefreshRequest
import kr.co.app.recordOfMemory.src.main.signUp.models.TokenResponse
import kr.co.app.recordOfMemory.src.main.signUp.retrofit.GetRefreshTokenInterface
import kr.co.app.recordOfMemory.src.main.signUp.retrofit.SignUpService
import kr.co.app.recordOfMemory.src.splash.SplashActivity

class DiaryAloneFragment : BaseFragment<FragmentDiaryAloneBinding>(FragmentDiaryAloneBinding::bind, R.layout.fragment_diary_alone),
    DiaryFragmentInterface, GetRefreshTokenInterface {
    var statusCode = 1000

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()
        binding.diaryBtnAlone.isSelected = true

        binding.diaryRv.visibility=View.INVISIBLE
        binding.diaryIvNone.visibility=View.INVISIBLE
        binding.diaryTvNone.visibility=View.INVISIBLE

        showLoadingDialog(requireContext())
        statusCode = 1000
        DiaryService(this).tryGetUsers()

        binding.diaryBtnTogether.setOnClickListener { //같이쓰는 으로 전환
            transaction
                .replace(R.id.main_frm, fm.findFragmentByTag("diaryTogetherFragment") ?: DiaryTogetherFragment(), "diaryTogetherFragment")
                .commit()
            transaction.isAddToBackStackAllowed
        }

        binding.iconDiaryAdd.setOnClickListener {
            addNewDiaryFunction()
            binding.diaryTvNone.visibility=View.INVISIBLE
            binding.diaryIvNone.visibility=View.INVISIBLE
        }
    }

    private fun addNewDiaryFunction(){
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
            if (0 < newItemName.length && newItemName.length < 10){
                statusCode = 2000
                var newItem = PostDiariesRequest(name = newItemName, diaryType= "ALONE")
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
        //val userName = data[0].nickname -> 로그인 후 받은 닉네임 정보로
        //binding.diaryTvTitle.text=userName+"님의 기억을 기록할 다이어리를 골라보세요!"

        val filterData = data.filter { it.diaryType=="ALONE" }
        if (!filterData.isEmpty()) {
            binding.diaryRv.visibility=View.VISIBLE
            val diaryAdapter = DiaryAdapter(filterData as ArrayList<ResultDiaries>)
            binding.diaryRv.adapter = diaryAdapter
            diaryAdapter.notifyDataSetChanged()
            val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
            binding.diaryRv.layoutManager = manager
        } else{
            binding.diaryTvNone.visibility=View.VISIBLE
            binding.diaryIvNone.visibility=View.VISIBLE
        }
    }

    override fun onGetDiariesFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onPostDiariesSuccess(response: kr.co.app.recordOfMemory.config.BaseResponse) {
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
                kr.co.app.recordOfMemory.config.ApplicationClass.sSharedPreferences.getString(
                    kr.co.app.recordOfMemory.config.ApplicationClass.X_REFRESH_TOKEN, "")
                    .toString()

            SignUpService(this).tryPostRefresh(PostRefreshRequest(X_REFRESH_TOKEN))

        }
        // 토큰 갱신 문제가 아닐 경우
        else {
            //TODO
        }
    }

    override fun onPostRefreshSuccess(response: TokenResponse) {
        val editor = kr.co.app.recordOfMemory.config.ApplicationClass.sSharedPreferences.edit()
        editor.putString(kr.co.app.recordOfMemory.config.ApplicationClass.X_ACCESS_TOKEN, response.information.accessToken)
        editor.putString(kr.co.app.recordOfMemory.config.ApplicationClass.X_REFRESH_TOKEN, response.information.refreshToken)
        editor.apply()

        when(statusCode) {
            1000 -> DiaryService(this).tryGetUsers()
            2000 -> DiaryService(this).tryGetDiaries()
        }
    }

    override fun onPostRefreshFailure(message: String) {
        dismissLoadingDialog()
        val editor = kr.co.app.recordOfMemory.config.ApplicationClass.sSharedPreferences.edit()
        editor.remove(kr.co.app.recordOfMemory.config.ApplicationClass.X_ACCESS_TOKEN)
        editor.remove(kr.co.app.recordOfMemory.config.ApplicationClass.X_REFRESH_TOKEN)
        editor.apply()

        val intent = Intent(context, SplashActivity::class.java)
        requireActivity().finishAffinity()
        startActivity(intent)
    }
}


