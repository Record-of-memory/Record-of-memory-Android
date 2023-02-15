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
import com.recordOfMemory.src.main.home.diary.DiaryTogetherFragment
import com.recordOfMemory.src.main.signUp.retrofit.GetRefreshTokenInterface
import com.recordOfMemory.src.main.signUp.retrofit.SignUpService
import com.recordOfMemory.src.main.home.diary2.member.invite.Diary2InviteMemberFragment
import com.recordOfMemory.src.main.home.diary2.member.models.GetUserResponse
import com.recordOfMemory.src.main.home.diary2.member.show.Diary2ShowMemberFragment
import com.recordOfMemory.src.main.home.diary2.recycler.grid.Diary2GridRecyclerOutViewAdapter
import com.recordOfMemory.src.main.home.diary2.search.Diary2SearchFragment
import com.recordOfMemory.src.main.home.diary2.recycler.list.Diary2ListRecyclerViewAdapter
import com.recordOfMemory.src.main.home.diary2.recycler.grid.models.Diary2GridOutViewModel
import com.recordOfMemory.src.main.home.diary2.retrofit.Diary2Interface

import com.recordOfMemory.src.main.home.diary2.retrofit.Diary2Service
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetMemberRecordResponse
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetMembersResponse
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetRecordsResponse
import com.recordOfMemory.src.main.signUp.models.PostRefreshRequest
import com.recordOfMemory.src.main.signUp.models.TokenResponse
import com.recordOfMemory.src.splash.SplashActivity

class Diary2Fragment : BaseFragment<FragmentDiary2Binding>(FragmentDiary2Binding::bind, R.layout.fragment_diary2),
Diary2Interface, GetRefreshTokenInterface{
    // true - list / false - grid
    var stateFlag = true

    lateinit var listItemList : ArrayList<GetMemberRecordResponse>
    lateinit var gridItemList :  ArrayList<Diary2GridOutViewModel>
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
        fun getItemId(item: GetMemberRecordResponse) {
//            openItem(item)
            println(item)
            startActivity(Intent(activity, DaybookActivity()::class.java)
                .putExtra("item", item as java.io.Serializable)
                .putExtra("screen_type","read")
                .putExtra("recordId", item.id)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        gridItemList = ArrayList()
        statusCode = 1000
        showLoadingDialog(requireContext())
        request = diaryId
        if(stateFlag) {
            binding.diary2IvList.isChecked = true
            binding.diary2IvGrid.isChecked = false
        }
        else {
            binding.diary2IvList.isChecked = false
            binding.diary2IvGrid.isChecked = true
        }
//        Diary2Service(this).tryGetRecords(diaryId)
        Diary2Service(this).tryGetMembers(diaryId)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val items = itemListAdapterToList()
        name = requireArguments().getString("name").toString()
        diaryType = requireArguments().getString("diaryType").toString()
        diaryId = requireArguments().getString("diaryId").toString()
        binding.diary2TvTitle.text = name

        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

        super.onViewCreated(view, savedInstanceState)

//        // 토큰 저장
//        val editor = ApplicationClass.sSharedPreferences.edit()
//        editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.accessToken)
//        editor.putString(ApplicationClass.X_REFRESH_TOKEN, response.refreshToken)
//        editor.apply()
//
//        // 토큰 불러오기
//        val accessToken = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN)

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
            Log.e("list isChecked", binding.diary2IvList.isChecked.toString())
            if(binding.diary2IvList.isChecked) {
                binding.diary2IvList.isChecked = true
                binding.diary2IvGrid.isChecked = false
                val diary2LayoutManager = LinearLayoutManager(context)
                val diary2RecyclerVIewAdapter = Diary2ListRecyclerViewAdapter(items, listItemList)
                binding.diary2RecyclerView.apply {
                    layoutManager = diary2LayoutManager
                    adapter = diary2RecyclerVIewAdapter
                }
            }
            else {
                binding.diary2IvList.isChecked = true
            }
        }
        binding.diary2IvGrid.setOnClickListener {
            gridItemList.add(Diary2GridOutViewModel("구리", listItemList))
            gridItemList.add(Diary2GridOutViewModel("나나", listItemList))

            Log.e("grid isChecked", binding.diary2IvGrid.isChecked.toString())
            if(binding.diary2IvGrid.isChecked) {
                binding.diary2IvGrid.isChecked = true
                binding.diary2IvList.isChecked = false
                val diary2LayoutManager = LinearLayoutManager(context)
                val diary2RecyclerVIewAdapter = Diary2GridRecyclerOutViewAdapter(items, gridItemList)
                binding.diary2RecyclerView.apply {
                    layoutManager = diary2LayoutManager
                    adapter = diary2RecyclerVIewAdapter
                }
            }
            else {
                binding.diary2IvGrid.isChecked = true
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

//        val params = mDialogView.window!!.attributes
//        params.width = WindowManager.LayoutParams.WRAP_CONTENT
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT
//        mDialogView.window?.attributes = params
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
            showLoadingDialog(requireContext())
            Diary2Service(this).tryLeaveDiary(diaryId)
            customDialog.dismiss()
        }
        customDialog.show()
    }

    override fun onGetRecordsSuccess(response: GetRecordsResponse) {
    }

    // 토큰 갱신 필요 시 토큰 갱신으로 이동
    override fun onGetRecordsFailure(message: String) {
        if(message == "refreshToken") {
            val X_REFRESH_TOKEN =
                ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_REFRESH_TOKEN, "")
                    .toString()

            SignUpService(this).tryPostRefresh(PostRefreshRequest(X_REFRESH_TOKEN))

        }
        // 토큰 갱신 문제가 아닐 경우
        else {
            //TODO
            showCustomToast("다시 시도해주세요.")
        }
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

            SignUpService(this).tryPostRefresh(PostRefreshRequest(X_REFRESH_TOKEN))

        }
        // 토큰 갱신 문제가 아닐 경우
        else {
            //TODO
        }
    }

    override fun onGetMembersSuccess(response: GetMembersResponse) {
        memberList = response.information.users
        listItemList = response.information.records

        if(diaryType == "WITH") {
            val tempList = ArrayList<GetMemberRecordResponse>()
            if(listItemList.size > 0) {
                for (listItem in listItemList) {
                    // listItem의 이메일이 memberList에 있는지 확인
                    if (memberList.find { it.email == listItem.user.email } != null) {
                        tempList.add(listItem)
                    }
                    // gridItemList에 tempList를 넣어준다.
                }
                gridItemList.add(Diary2GridOutViewModel(tempList[0].user.nickname, tempList))
            }
        }
        else {
            if(listItemList.size > 0) {
                gridItemList.add(
                    Diary2GridOutViewModel(
                        listItemList[0].user.nickname,
                        listItemList
                    )
                )
            }
        }

        // list
        if(stateFlag) {
            if(listItemList.size != 0) {
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

        // grid
        else {
            if (listItemList.size != 0) {
                binding.diary2LinearEmptyContent.isGone = true
                val diary2LayoutManager = LinearLayoutManager(context)
                val diary2RecyclerVIewAdapter =
                    Diary2GridRecyclerOutViewAdapter(items, gridItemList)
                binding.diary2RecyclerView.apply {
                    layoutManager = diary2LayoutManager
                    adapter = diary2RecyclerVIewAdapter
                }
            } else {
                binding.diary2LinearEmptyContent.isVisible = true
            }
        }
        dismissLoadingDialog()
    }

    override fun onGetMembersFailure(message: String) {
        dismissLoadingDialog()
        if(message == "refreshToken") {
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

    // 통신 시도 -> 토큰이 유효 X -> 토큰 갱신 O -> 통신 재시도

    override fun onPostRefreshSuccess(response: TokenResponse) {
        val editor = ApplicationClass.sSharedPreferences.edit()
        editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.information.accessToken)
        editor.putString(ApplicationClass.X_REFRESH_TOKEN, response.information.refreshToken)
        editor.apply()

        when(statusCode) {
            1000 -> Diary2Service(this).tryGetMembers(request as String)
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
    override fun onGetRecordsSuccess(response: GetRecordsResponse) {
        dismissLoadingDialog()
        listItemList = response.information
        // list
        if(stateFlag) {
            val diary2LayoutManager = LinearLayoutManager(context)
            val diary2RecyclerVIewAdapter = Diary2ListRecyclerViewAdapter(items, listItemList)
            binding.diary2RecyclerView.apply {
                layoutManager = diary2LayoutManager
                adapter = diary2RecyclerVIewAdapter
            }
        }

        // grid
        else {
            val diary2LayoutManager = LinearLayoutManager(context)
            val diary2RecyclerVIewAdapter = Diary2GridRecyclerOutViewAdapter(items, gridItemList)
            binding.diary2RecyclerView.apply {
                layoutManager = diary2LayoutManager
                adapter = diary2RecyclerVIewAdapter
            }

        }
    }

    // 토큰 갱신 필요 시 토큰 갱신으로 이동
    override fun onGetRecordsFailure(message: String) {
        if(message == "refreshToken") {
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

    // 통신 시도 -> 토큰이 유효 X -> 토큰 갱신 O -> 통신 재시도

    override fun onPostRefreshSuccess(response: TokenResponse) {
        val editor = ApplicationClass.sSharedPreferences.edit()
        editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.information.accessToken)
        editor.putString(ApplicationClass.X_REFRESH_TOKEN, response.information.refreshToken)
        editor.apply()

        when(statusCode) {
            1000 -> Diary2Service(this).tryGetRecords(request as String)
        }
    }

    // refreshToken 갱신 실패 로그인으로 이동한다.
    override fun onPostRefreshFailure(message: String) {
        TODO("Not yet implemented")
    }
}