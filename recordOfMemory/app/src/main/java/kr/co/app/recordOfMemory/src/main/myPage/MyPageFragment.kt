package kr.co.app.recordOfMemory.src.main.myPage

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
import kr.co.app.recordOfMemory.R
import kr.co.app.recordOfMemory.config.ApplicationClass
import kr.co.app.recordOfMemory.config.BaseFragment
import kr.co.app.recordOfMemory.databinding.FragmentMyPageBinding
import kr.co.app.recordOfMemory.src.main.myPage.retrofit.MyPageInterface
import kr.co.app.recordOfMemory.src.main.myPage.retrofit.MyPageService
import kr.co.app.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import kr.co.app.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutRequest
import kr.co.app.recordOfMemory.src.main.myPage.retrofit.models.PostSignOutResponse
import kr.co.app.recordOfMemory.src.main.signUp.models.PostRefreshRequest
import kr.co.app.recordOfMemory.src.main.signUp.models.TokenResponse
import kr.co.app.recordOfMemory.src.main.signUp.retrofit.GetRefreshTokenInterface
import kr.co.app.recordOfMemory.src.main.signUp.retrofit.SignUpService
import kr.co.app.recordOfMemory.src.splash.SplashActivity

class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page),MyPageInterface,
    GetRefreshTokenInterface {

    var statusCode = 1101
    var request : Any = ""
    var userImg:String=""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 마이페이지 정보 조회
        showLoadingDialog(requireContext())
        statusCode=1103
        MyPageService(this).tryGetUsers()

        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()


        // 마이페이지 인스턴스 1 -> 마이페이지에딧 인스턴스 1 -> 마이페이지 인스턴스 2
        binding.mypageEditBtn.setOnClickListener {
            var bundle = Bundle()
            bundle.putString("userImg",userImg)

            var fragment2 = MyPageEditFragment(this)
            fragment2.arguments=bundle

            transaction
                .replace(R.id.main_frm, fragment2)
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
            val refreshToken = ApplicationClass.sSharedPreferences.getString(
                ApplicationClass.X_REFRESH_TOKEN, null).toString()
            statusCode=1105
            val postSignOutRequest=PostSignOutRequest(refreshToken = "Bearer $refreshToken")

            request=postSignOutRequest
            showLoadingDialog(requireContext())
            MyPageService(this).tryPostSignOut(postSignOutRequest)
        }

        logoutDialog.show()
    }

    override fun onPostSignOutSuccess(response: PostSignOutResponse) {
        dismissLoadingDialog()
        Log.d("성공","${response.information.message}")
        //로그아웃 성공 시 화면은 스플래시 화면으로
        startActivity(Intent(context,SplashActivity::class.java))
        requireActivity().finishAffinity()
    }

    override fun onPostSignOutFailure(message: String) {
        dismissLoadingDialog()
        if(message == "refreshToken") {
            val X_REFRESH_TOKEN = ApplicationClass.sSharedPreferences.getString(
                ApplicationClass.X_REFRESH_TOKEN, "").toString()
            showLoadingDialog(requireContext())
            SignUpService(this).tryPostRefresh(PostRefreshRequest(X_REFRESH_TOKEN))
        }
        // 토큰 갱신 문제가 아닐 경우
        // 로그아웃 API이므로 그냥 로그아웃 해도 될 것 같음
        else {
            Log.d("실패",message)
//            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
            val editor = ApplicationClass.sSharedPreferences.edit()
            editor.remove(ApplicationClass.X_ACCESS_TOKEN)
            editor.remove(ApplicationClass.X_REFRESH_TOKEN)
            editor.apply()
            
            startActivity(Intent(context,SplashActivity::class.java))
            requireActivity().finishAffinity()
        }
    }

    override fun onGetUsersSuccess(response: GetUsersResponse) {
        dismissLoadingDialog()
        binding.mypageBoxName.text=response.information.nickname
        binding.mypageBoxAccount.text=response.information.email
        if(response.information.imageUrl.isNullOrEmpty()){
            binding.mypagePersonImg.setImageResource(R.drawable.icn_user)
        }else{
            Glide.with(this).load(response.information.imageUrl)
                .into(binding.mypagePersonImg)
            userImg=response.information.imageUrl
        }
    }

    override fun onGetUsersFailure(message: String) {
        dismissLoadingDialog()
        if(message == "refreshToken") {
            val X_REFRESH_TOKEN = ApplicationClass.sSharedPreferences.getString(
                ApplicationClass.X_REFRESH_TOKEN, "").toString()
            showLoadingDialog(requireContext())
            SignUpService(this).tryPostRefresh(PostRefreshRequest(X_REFRESH_TOKEN))
        }
        // 토큰 갱신 문제가 아닐 경우
        else {
            Log.d("실패",message)
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPostRefreshSuccess(response: TokenResponse) {
        dismissLoadingDialog()
        val editor = ApplicationClass.sSharedPreferences.edit()
        editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.information.accessToken)
        editor.putString(ApplicationClass.X_REFRESH_TOKEN, response.information.refreshToken)
        editor.apply()

        showLoadingDialog(requireContext())
        when(statusCode) {
            1103 -> MyPageService(this).tryGetUsers()
            1105 -> MyPageService(this).tryPostSignOut(request as PostSignOutRequest)
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