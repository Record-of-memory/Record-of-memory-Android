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
import com.recordOfMemory.src.main.home.diary2.recycler.grid.Diary2GridRecyclerOutViewAdapter
import com.recordOfMemory.src.main.home.diary2.search.Diary2SearchFragment
import com.recordOfMemory.src.main.home.diary2.recycler.list.Diary2ListRecyclerViewAdapter
import com.recordOfMemory.src.main.home.diary2.recycler.grid.models.Diary2GridOutViewModel
import com.recordOfMemory.src.main.home.diary2.retrofit.Diary2Interface
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetRecordResponse
import com.recordOfMemory.src.main.home.diary2.retrofit.Diary2Service
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetRecordsResponse

class Diary2Fragment : BaseFragment<FragmentDiary2Binding>(FragmentDiary2Binding::bind, R.layout.fragment_diary2),
Diary2Interface{
    // true - list / false - grid
    var stateFlag = true

    var listItemList = ArrayList<GetRecordResponse>()
    var gridItemList = ArrayList<Diary2GridOutViewModel>()
    val items = itemListAdapterToList()


    inner class itemListAdapterToList {
        // 일기 open function
        fun getItemId(item: GetRecordResponse) {
//            openItem(item)
            println(item)
            startActivity(Intent(activity, DaybookActivity::class.java)
                .putExtra("item", item as java.io.Serializable))
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

    override fun onResume() {
        super.onResume()

        showLoadingDialog(requireContext())
        Diary2Service(this).tryGetRecords("52")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val items = itemListAdapterToList()

        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

//        val jsonObject = JSONObject("{\"userId\":\"${id}\",\"category\":\"${category}\",\"title\":\"${title}\",\"contents\":\"${contents}\",\"hashTags\":${jsonArray}}").toString()
//        val jsonBody = RequestBody.create(parse("application/json"),jsonObject)

//        itemList.add(GetRecordResponse(itemId = "1", title = "ss", content = "content", date = "23.01.01",writer = "구리",
//        imgUrl = "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ"))
//
//        itemList.add(GetRecordResponse(itemId = "1", title = "ss", content = "content", date = "23.01.01",writer = "구리",
//            imgUrl = "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ"))
//
//        itemList.add(GetRecordResponse(itemId = "1", title = "ss", content = "content", date = "23.01.01",writer = "구리",
//            imgUrl = "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ"))
//
//        itemList.add(GetRecordResponse(itemId = "1", title = "ss", content = "content", date = "23.01.01",writer = "구리",
//            imgUrl = "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ"))
//
//        itemList.add(GetRecordResponse(itemId = "1", title = "ss", content = "가가가가가가", date = "23.01.01",writer = "구리",
//            imgUrl = "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ"))

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
            val diary2SearchFragment = Diary2SearchFragment()
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

    override fun onGetRecordsFailure(message: String) {
        TODO("Not yet implemented")
    }
}