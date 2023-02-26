package com.recordOfMemory.src.main.home.diary2

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.recordOfMemory.R
import com.recordOfMemory.config.ApplicationClass
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.databinding.FragmentDiary2Binding
import com.recordOfMemory.src.daybook.DaybookActivity
import com.recordOfMemory.src.daybook.DaybookWritingActivity
import com.recordOfMemory.src.main.signUp.retrofit.GetRefreshTokenInterface
import com.recordOfMemory.src.main.signUp.retrofit.SignUpService
import com.recordOfMemory.src.main.home.diary2.member.invite.Diary2InviteMemberFragment
import com.recordOfMemory.src.main.home.diary2.member.models.GetUserResponse
import com.recordOfMemory.src.main.home.diary2.member.show.Diary2ShowMemberFragment
import com.recordOfMemory.src.main.home.diary2.recycler.grid.Diary2GridRecyclerOutViewAdapter
import com.recordOfMemory.src.main.home.diary2.search.Diary2SearchFragment
import com.recordOfMemory.src.main.home.diary2.recycler.list.Diary2ListRecyclerViewAdapter
import com.recordOfMemory.src.main.home.diary2.retrofit.Diary2Interface

import com.recordOfMemory.src.main.home.diary2.retrofit.Diary2Service
import com.recordOfMemory.src.main.home.diary2.retrofit.models.*
import com.recordOfMemory.src.main.signUp.models.PostRefreshRequest
import com.recordOfMemory.src.main.signUp.models.TokenResponse
import com.recordOfMemory.src.splash.SplashActivity

class Diary2Fragment : BaseFragment<FragmentDiary2Binding>(FragmentDiary2Binding::bind, R.layout.fragment_diary2),
Diary2Interface, GetRefreshTokenInterface{
    // true - list / false - grid
    var stateFlag = true

    var listItemList : ArrayList<GetMemberRecordResponse> = ArrayList()
    var gridItemList :  ArrayList<GridUser> = ArrayList()
    var memberList = ArrayList<GetUserResponse>()
    val items = itemListAdapterToList()
    var request : Any = ""
    var name =""
    var diaryId = ""
    var diaryType = ""

    // 토큰 갱신 시 수행해야 할 통신을 알려준다.
    // 1000 -> tryGetRecords
    var statusCode = 1000


    inner class itemListAdapterToList {
        // 일기 open function
        fun getItemId(item: GridRecord) {
            println(item)
            startActivity(Intent(activity, DaybookActivity()::class.java)
                .putExtra("item", item as java.io.Serializable)
                .putExtra("screen_type","read")
                .putExtra("recordId", item.id)
            )
        }
        fun getItemId(item: GetMemberRecordResponse) {
            println(item)
            startActivity(Intent(activity, DaybookActivity()::class.java)
                .putExtra("item", item as java.io.Serializable)
                .putExtra("screen_type","read")
                .putExtra("recordId", item.id)
                .putExtra("writerImg",item.user.imageUrl)
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val items = itemListAdapterToList()
        Log.e("onViewCreated", "onViewCreated")

        name = requireArguments().getString("name").toString()
        diaryType = requireArguments().getString("diaryType").toString()
        diaryId = requireArguments().getString("diaryId").toString()
        binding.diary2TvTitle.text = name

        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

        super.onViewCreated(view, savedInstanceState)

        statusCode = 1000
        request = diaryId
        showLoadingDialog(requireContext())
        Diary2Service(this).tryGetMembers(diaryId)
        statusCode = 2000
        Diary2Service(this).tryGetGridMembers(diaryId)
        setList(stateFlag)

        binding.diary2IvSearch.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList("itemList", listItemList)
            val diary2SearchFragment = Diary2SearchFragment(this)
            diary2SearchFragment.arguments = bundle

            transaction
                .replace(R.id.main_frm, diary2SearchFragment)
                .addToBackStack(null)
                .commit()
            transaction.isAddToBackStackAllowed
        }
        binding.diary2BtnWrite.setOnClickListener { //버튼 누르면 새로은 일기 쓰는 화면으로 전환
            val intent=Intent(context,DaybookWritingActivity::class.java)
            intent.putExtra("screen_type","create")
            intent.putExtra("diaryTitle", name)
            intent.putExtra("diaryId", diaryId)
            startActivity(intent)
        }

        // list view
        binding.diary2IvList.setOnClickListener {
            if(!stateFlag) {
                stateFlag = true
                binding.diary2IvList.isChecked = stateFlag
                binding.diary2IvGrid.isChecked = !stateFlag
                setList(true)
            }
            else {
                binding.diary2IvList.isChecked = stateFlag
                binding.diary2IvGrid.isChecked = !stateFlag
            }
        }
        // grid view
        binding.diary2IvGrid.setOnClickListener {
            if(stateFlag) {
                stateFlag = false
                binding.diary2IvList.isChecked = stateFlag
                binding.diary2IvGrid.isChecked = !stateFlag
                setList(false)
            }
            else {
                binding.diary2IvList.isChecked = stateFlag
                binding.diary2IvGrid.isChecked = !stateFlag
            }
        }

        binding.diary2IvMore.setOnClickListener {
            showPopup()
        }
    }

    private fun showPopup() {
        val mDialogView = Dialog(requireContext())
        mDialogView.setContentView(R.layout.dialog_diary2_more)
        mDialogView.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window = mDialogView.window

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(window?.attributes)
        layoutParams.gravity = Gravity.TOP or Gravity.END
        layoutParams.x = 120
        layoutParams.y = 200
        window?.attributes = layoutParams

        mDialogView.show()

        if(diaryType == "ALONE") {
            mDialogView.findViewById<LinearLayout>(R.id.dialog_diary2_more_linear).background = requireContext().getDrawable(R.drawable.dialog_diary2_more_alone)
            mDialogView.findViewById<TextView>(R.id.dialog_diary2_more_tv_invite_member).isGone = true
            mDialogView.findViewById<TextView>(R.id.dialog_diary2_more_tv_show_member).isGone = true
        }

        mDialogView.findViewById<TextView>(R.id.dialog_diary2_more_tv_invite_member).setOnClickListener {
            val bundle = Bundle()
            bundle.putString("diaryId", diaryId)

            val fm = requireActivity().supportFragmentManager
            val transaction: FragmentTransaction = fm.beginTransaction()

            val diary2InviteMemberFragment = Diary2InviteMemberFragment(this)
            diary2InviteMemberFragment.arguments = bundle

            transaction
                .replace(R.id.main_frm, diary2InviteMemberFragment)
                .addToBackStack(null)
                .commit()
            transaction.isAddToBackStackAllowed
            mDialogView.dismiss()
        }
        mDialogView.findViewById<TextView>(R.id.dialog_diary2_more_tv_show_member).setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList("userList", memberList)

            val fm = requireActivity().supportFragmentManager
            val transaction: FragmentTransaction = fm.beginTransaction()

            val diary2ShowMemberFragment = Diary2ShowMemberFragment(this)
            diary2ShowMemberFragment.arguments = bundle


            transaction
                .replace(R.id.main_frm, diary2ShowMemberFragment)
                .addToBackStack(null)
                .commit()
            transaction.isAddToBackStackAllowed
            mDialogView.dismiss()
        }
        mDialogView.findViewById<TextView>(R.id.dialog_diary2_more_tv_exit_diary).setOnClickListener {
            showCustomDialog()
            mDialogView.dismiss()
        }
    }

    fun showCustomDialog() {
        val customDialog= Dialog(requireContext())
        customDialog.setContentView(R.layout.dialog_diary2_leave)
        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val cancelBtn = customDialog.findViewById<TextView>(R.id.dialog_diary2_leave_btn_cancel)
        cancelBtn.setOnClickListener {
            customDialog.dismiss()
        }
        val leaveBtn = customDialog.findViewById<TextView>(R.id.dialog_diary2_leave_btn_leave)
        leaveBtn.setOnClickListener {
            showLoadingDialog (requireContext())
            // 3000 -> leave diary
            statusCode = 3000
            request = diaryId
            Diary2Service(this).tryLeaveDiary(diaryId)
            customDialog.dismiss()
        }
        customDialog.show()
    }

    override fun onDeleteDiarySuccess(response: BaseResponse) {
        dismissLoadingDialog()
        onGetLeaveSuccess()
    }

    override fun onDeleteDiaryFailure(message: String) {
        dismissLoadingDialog()
        if(message == "refreshToken") {
            val X_REFRESH_TOKEN =
                ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_REFRESH_TOKEN, "")
                    .toString()
            showLoadingDialog(requireContext())
            SignUpService(this).tryPostRefresh(PostRefreshRequest(X_REFRESH_TOKEN))

        }
        // 토큰 갱신 문제가 아닐 경우
        else {
            showCustomToast("통신 오류입니다. 다시 시도해주세요.")
        }
    }

    override fun onGetMembersSuccess(response: GetMembersResponse) {
        dismissLoadingDialog()
        memberList = response.information.users
        listItemList = response.information.records
        if(stateFlag)
            setList(true)
    }

    override fun onGetMembersFailure(message: String) {
        dismissLoadingDialog()
        if(message == "refreshToken") {
            val X_REFRESH_TOKEN =
                ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_REFRESH_TOKEN, "")
                    .toString()

            showLoadingDialog(requireContext())
            SignUpService(this).tryPostRefresh(PostRefreshRequest(X_REFRESH_TOKEN))

        }
        // 토큰 갱신 문제가 아닐 경우
        else {
            showCustomToast("통신 오류입니다. 다시 시도해주세요.")
        }
    }

    override fun onGetGridMembersSuccess(response: GetGridMembersResponse) {
        dismissLoadingDialog()
        gridItemList = response.information.users
        if(!stateFlag)
            setList(false)
    }

    override fun onGetGridMembersFailure(message: String) {
        dismissLoadingDialog()
        if(message == "refreshToken") {
            val X_REFRESH_TOKEN =
                ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_REFRESH_TOKEN, "")
                    .toString()

            showLoadingDialog(requireContext())
            SignUpService(this).tryPostRefresh(PostRefreshRequest(X_REFRESH_TOKEN))

        }
        // 토큰 갱신 문제가 아닐 경우
        else {
            showCustomToast("통신 오류입니다. 다시 시도해주세요.")
        }
    }

    // 통신 시도 -> 토큰이 유효 X -> 토큰 갱신 O -> 통신 재시도

    override fun onPostRefreshSuccess(response: TokenResponse) {
        dismissLoadingDialog()
        val editor = ApplicationClass.sSharedPreferences.edit()
        editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.information.accessToken)
        editor.putString(ApplicationClass.X_REFRESH_TOKEN, response.information.refreshToken)
        editor.apply()

        showLoadingDialog(requireContext())
        when(statusCode) {
            1000 -> Diary2Service(this).tryGetMembers(request as String)
            2000 -> Diary2Service(this).tryGetGridMembers(request as String)
            3000 -> Diary2Service(this).tryLeaveDiary(request as String)
        }
    }

    // refreshToken 갱신 실패 로그인으로 이동한다.
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
    fun onGetLeaveSuccess() {
        dismissLoadingDialog()
        val customDialog= Dialog(requireContext())
        customDialog.setContentView(R.layout.dialog_custom2)
        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        customDialog.findViewById<TextView>(R.id.dialog2_title).text="다이어리가 삭제되었어요."


        val okBtn = customDialog.findViewById<TextView>(R.id.dialog2_btn_ok)
        okBtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            customDialog.dismiss()
        }
        customDialog.show()
    }

    private fun setList(flag : Boolean) {
        // flag : true -> list, false -> grid
        if(flag) {
            if(listItemList.isNotEmpty()) {
                binding.diary2LinearEmptyContent.isGone = true
                val diary2LayoutManager = LinearLayoutManager(context)
                val diary2RecyclerVIewAdapter = Diary2ListRecyclerViewAdapter(items, listItemList)
                binding.diary2RecyclerView.apply {
                    layoutManager = diary2LayoutManager
                    adapter = diary2RecyclerVIewAdapter
                }
            }
            else {
                binding.diary2LinearEmptyContent.isVisible = true
            }
        }
        else {
            if(gridItemList.isNotEmpty()) {
                binding.diary2LinearEmptyContent.isGone = true
                val diary2LayoutManager = LinearLayoutManager(context)
                val diary2RecyclerVIewAdapter =
                    Diary2GridRecyclerOutViewAdapter(items, gridItemList)
                binding.diary2RecyclerView.apply {
                    layoutManager = diary2LayoutManager
                    adapter = diary2RecyclerVIewAdapter
                }
            }
            else {
                binding.diary2LinearEmptyContent.isVisible = true
            }
        }
    }
}