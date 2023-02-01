package com.recordOfMemory.src.main.home

import android.content.Intent
import android.os.Bundle
import android.system.Os.bind
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentDiary2Binding
import com.recordOfMemory.src.daybook.DaybookActivity
import com.recordOfMemory.src.daybook.DaybookWritingActivity
import com.recordOfMemory.src.main.home.diary2.Diary2SearchFragment
import com.recordOfMemory.src.main.home.diary2.recycler.Diary2GridRecyclerOutViewAdapter
import com.recordOfMemory.src.main.home.diary2.recycler.Diary2ListRecyclerViewAdapter
import com.recordOfMemory.src.main.home.diary2.recycler.models.Diary2GridOutViewModel
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetDiary2Response

class Diary2Fragment : BaseFragment<FragmentDiary2Binding>(FragmentDiary2Binding::bind, R.layout.fragment_diary2){
    // true - list / false - grid
    var stateFlag = true

    inner class itemListAdapterToList {
        // 일기 open function
        fun getItemId(item: GetDiary2Response) {
//            openItem(item)
            println(item)
            var intent=Intent(activity, DaybookActivity::class.java)
            intent.putExtra("item",item)
//            intent.putExtra("imageExist","glide")
            intent.putExtra("screen_type","read")
            startActivity(intent)

        }
    }

    // 일기 open
    fun openItem(item: GetDiary2Response) {
        println(item)
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
    }

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
}