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
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentDiary2Binding
import com.recordOfMemory.src.daybook.DaybookActivity
import com.recordOfMemory.src.daybook.DaybookWritingActivity
import com.recordOfMemory.src.main.home.diary.DiaryTogetherFragment
import com.recordOfMemory.src.main.home.diary2.member.invite.Diary2InviteMemberFragment
import com.recordOfMemory.src.main.home.diary2.member.show.Diary2ShowMemberFragment
import com.recordOfMemory.src.main.home.diary2.search.Diary2SearchFragment
import com.recordOfMemory.src.main.home.diary2.recycler.grid.Diary2GridRecyclerOutViewAdapter
import com.recordOfMemory.src.main.home.diary2.recycler.list.Diary2ListRecyclerViewAdapter
import com.recordOfMemory.src.main.home.diary2.recycler.grid.models.Diary2GridOutViewModel
import com.recordOfMemory.src.main.home.diary.retrofit.DiaryService
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetDiary2Response
import com.recordOfMemory.src.main.home.diary.retrofit.models.PostDiaryRequest

class Diary2Fragment : BaseFragment<FragmentDiary2Binding>(FragmentDiary2Binding::bind, R.layout.fragment_diary2){
    // true - list / false - grid
    var stateFlag = true

    inner class itemListAdapterToList {
        // 일기 open function
        fun getItemId(item: GetDiary2Response) {
//            openItem(item)
            println(item)
            startActivity(Intent(activity, DaybookActivity::class.java)
                .putExtra("item", item))
        }
    }

    // 일기 open
//    fun openItem(item: GetDiary2Response) {
//        println(item)
//        startActivity(Intent(activity, DaybookActivity(item)::class.java))

//        val fm = requireActivity().supportFragmentManager
//        val transaction: FragmentTransaction = fm.beginTransaction()
//
//        val email = sSharedPreferences.getString("email", "")
//        if(email == "NO") {
//            transaction
//                .replace(R.id.main_frm, LoginFragment())
//                .addToBackStack(null)
//                .commit()
//            transaction.isAddToBackStackAllowed
//        }
//        else {
//            transaction
//                .replace(R.id.main_frm, itemFragment)
//                .addToBackStack(null)
//                .commit()
//            transaction.isAddToBackStackAllowed
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val items = itemListAdapterToList()

        binding.diary2IvMore.setOnClickListener {

        }

        var itemList = ArrayList<GetDiary2Response>()
        var itemLists = ArrayList<Diary2GridOutViewModel>()


        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

        itemList.add(GetDiary2Response(itemId = "1", title = "ss", content = "content", date = "23.01.01",writer = "구리",
        imgUrl = "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ"))

        itemList.add(GetDiary2Response(itemId = "1", title = "ss", content = "content", date = "23.01.01",writer = "구리",
            imgUrl = "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ"))

        itemList.add(GetDiary2Response(itemId = "1", title = "ss", content = "content", date = "23.01.01",writer = "구리",
            imgUrl = "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ"))

        itemList.add(GetDiary2Response(itemId = "1", title = "ss", content = "content", date = "23.01.01",writer = "구리",
            imgUrl = "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ"))

        itemList.add(GetDiary2Response(itemId = "1", title = "ss", content = "가가가가가가", date = "23.01.01",writer = "구리",
            imgUrl = "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ"))

        itemLists.add(Diary2GridOutViewModel("구리", itemList))
        itemLists.add(Diary2GridOutViewModel("나나", itemList))

        super.onViewCreated(view, savedInstanceState)

        binding.diary2IvSearch.setOnClickListener {
            transaction
                .replace(R.id.main_frm, Diary2SearchFragment())
                .addToBackStack(null)
                .commit()
            transaction.isAddToBackStackAllowed
        }

        binding.diary2BtnWrite.setOnClickListener { //버튼 누르면 새로은 일기 쓰는 화면으로 전환
            val intent=Intent(context,DaybookWritingActivity::class.java)
            intent.putExtra("screen_type","create")
            startActivity(intent)
        }

        // list view
        binding.diary2IvList.setOnClickListener {
            Log.e("list isChecked", binding.diary2IvList.isChecked.toString())
            if(binding.diary2IvList.isChecked) {
                binding.diary2IvList.isChecked = true
                binding.diary2IvGrid.isChecked = false
                val diary2LayoutManager = LinearLayoutManager(context)
                val diary2RecyclerVIewAdapter = Diary2ListRecyclerViewAdapter(items, itemList)
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

            val postDiariesRequest = PostDiaryRequest(name = "", diaryType = "")
            DiaryService(this).tryPostDiaries(postDiariesRequest)

            Log.e("grid isChecked", binding.diary2IvGrid.isChecked.toString())
            if(binding.diary2IvGrid.isChecked) {
                binding.diary2IvGrid.isChecked = true
                binding.diary2IvList.isChecked = false
                val diary2LayoutManager = LinearLayoutManager(context)
                val diary2RecyclerVIewAdapter = Diary2GridRecyclerOutViewAdapter(items, itemLists)
                binding.diary2RecyclerView.apply {
                    layoutManager = diary2LayoutManager
                    adapter = diary2RecyclerVIewAdapter
                }
            }
            else {
                binding.diary2IvGrid.isChecked = true
            }
        }
        // list
        if(stateFlag) {
            val diary2LayoutManager = LinearLayoutManager(context)
            val diary2RecyclerVIewAdapter = Diary2ListRecyclerViewAdapter(items, itemList)
            binding.diary2RecyclerView.apply {
                layoutManager = diary2LayoutManager
                adapter = diary2RecyclerVIewAdapter
            }
        }

        // grid
        else {
            val diary2LayoutManager = LinearLayoutManager(context)
            val diary2RecyclerVIewAdapter = Diary2GridRecyclerOutViewAdapter(items, itemLists)
            binding.diary2RecyclerView.apply {
                layoutManager = diary2LayoutManager
                adapter = diary2RecyclerVIewAdapter
            }

        }

        binding.diary2IvMore.setOnClickListener {
            showPopup()
        }



//        binding.homeButtonTryGetJwt.setOnClickListener {
//            showLoadingDialog(requireContext())
//            HomeService(this).tryGetUsers()
//        }
//
//        binding.homeBtnTryPostHttpMethod.setOnClickListener {
//            val email = binding.homeEtId.text.toString()
//            val password = binding.homeEtPw.text.toString()
//            val postRequest = PostSignUpRequest(email = email, password = password,
//                    confirmPassword = password, nickname = "test", phoneNumber = "010-0000-0000")
//            showLoadingDialog(requireContext())
//            HomeService(this).tryPostSignUp(postRequest)
//        }
//    }
//
//    override fun onGetUserSuccess(response: UserResponse) {
//        dismissLoadingDialog()
//        for (User in response.result) {
//            Log.d("HomeFragment", User.toString())
//        }
//        binding.homeButtonTryGetJwt.text = response.message
//        showCustomToast("Get JWT 성공")
//    }
//
//    override fun onGetUserFailure(message: String) {
//        dismissLoadingDialog()
//        showCustomToast("오류 : $message")
//    }
//
//    override fun onPostSignUpSuccess(response: SignUpResponse) {
//        dismissLoadingDialog()
//        binding.homeBtnTryPostHttpMethod.text = response.message
//        response.message?.let { showCustomToast(it) }
//    }
//
//    override fun onPostSignUpFailure(message: String) {
//        dismissLoadingDialog()
//        showCustomToast("오류 : $message")
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

        mDialogView.findViewById<TextView>(R.id.dialog_diary2_more_tv_invite_member).setOnClickListener {
            val fm = requireActivity().supportFragmentManager
            val transaction: FragmentTransaction = fm.beginTransaction()

            transaction
                .replace(R.id.main_frm, Diary2InviteMemberFragment())
                .addToBackStack(null)
                .commit()
            transaction.isAddToBackStackAllowed
            mDialogView.dismiss()
        }
        mDialogView.findViewById<TextView>(R.id.dialog_diary2_more_tv_show_member).setOnClickListener {
            val fm = requireActivity().supportFragmentManager
            val transaction: FragmentTransaction = fm.beginTransaction()

            transaction
                .replace(R.id.main_frm, Diary2ShowMemberFragment())
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
            onGetLeaveSuccess()
            customDialog.dismiss()
        }
        customDialog.show()
    }

    fun onGetLeaveSuccess() {
        val customDialog= Dialog(requireContext())
        customDialog.setContentView(R.layout.dialog_custom2)
        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        customDialog.findViewById<TextView>(R.id.dialog2_title).text="다이어리가 삭제되었어요."


        val okBtn = customDialog.findViewById<TextView>(R.id.dialog2_btn_ok)
        okBtn.setOnClickListener {
            val fm = requireActivity().supportFragmentManager
            val transaction: FragmentTransaction = fm.beginTransaction()

            transaction
                .replace(R.id.main_frm, DiaryTogetherFragment())
                .addToBackStack(null)
                .commit()
            transaction.isAddToBackStackAllowed
            customDialog.dismiss()
        }
        customDialog.show()
    }
}