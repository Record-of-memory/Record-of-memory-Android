package com.recordOfMemory.src.main.home.diary2.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentDiary2SearchBinding
import com.recordOfMemory.src.daybook.DaybookActivity
import com.recordOfMemory.src.main.home.diary2.Diary2Fragment
import com.recordOfMemory.src.main.home.diary2.Diary2Interface
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetDiary2Response
import com.recordOfMemory.src.main.home.diary2.search.recycler.Diary2SearchRecyclerViewAdapter

class Diary2SearchFragment : BaseFragment<FragmentDiary2SearchBinding>(FragmentDiary2SearchBinding::bind, R.layout.fragment_diary2_search),
    Diary2Interface {
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
    fun openItem(item: GetDiary2Response) {
//        startActivity(Intent(activity, DaybookActivity(item)::class.java))

//        val fm = requireActivity().supportFragmentManager
//        val transaction: FragmentTransaction = fm.beginTransaction()
//
//        val email = ApplicationClass.sSharedPreferences.getString("email", "")
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
        var itemList = ArrayList<GetDiary2Response>()
        var keyword = ""

        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

        binding.diary2SearchBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            transaction
                .replace(R.id.main_frm, Diary2Fragment())
                .addToBackStack(null)
                .commit()
            transaction.isAddToBackStackAllowed
        }

        itemList.add(
            GetDiary2Response(itemId = "1", title = "ss", content = "content", date = "23.01.01",writer = "구리",
            imgUrl = "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ")
        )

        itemList.add(
            GetDiary2Response(itemId = "1", title = "ss", content = "content", date = "23.01.01",writer = "구리",
            imgUrl = "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ")
        )

        itemList.add(
            GetDiary2Response(itemId = "1", title = "ss", content = "content", date = "23.01.01",writer = "구리",
            imgUrl = "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ")
        )
        val items = itemListAdapterToList()
        val diary2LayoutManager = LinearLayoutManager(context)
        val diary2RecyclerViewAdapter = Diary2SearchRecyclerViewAdapter(this, items, itemList)
        binding.diary2SearchRecyclerView.apply {
            layoutManager = diary2LayoutManager
        }

        super.onViewCreated(view, savedInstanceState)

        binding.diary2SearchEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                keyword = if(binding.diary2SearchEt.text.isEmpty()) "" else binding.diary2SearchEt.text.toString()
                println(keyword)
                diary2RecyclerViewAdapter.filter.filter(keyword)
                binding.diary2SearchRecyclerView.adapter = diary2RecyclerViewAdapter
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

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

    override fun onGetItemSize(itemSize: Int) {
        println("item size: $itemSize")
        if(itemSize != 0) {
            binding.diary2SearchEmpty.isGone = true
        }
        else {
            binding.diary2SearchEmpty.isVisible = true
        }
    }
}