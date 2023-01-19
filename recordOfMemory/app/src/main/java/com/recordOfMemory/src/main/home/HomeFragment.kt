package com.recordOfMemory.src.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.recordOfMemory.R
import com.recordOfMemory.config.ApplicationClass.Companion.sSharedPreferences
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentDiary2Binding
import com.recordOfMemory.src.main.home.diary2.recycler.Diary2RecyclerVIewAdapter

class HomeFragment : BaseFragment<FragmentDiary2Binding>(FragmentDiary2Binding::bind, R.layout.fragment_diary2_empty){

    lateinit var itemList : ArrayList<ItemData>
    inner class itemListAdapterToList {
        fun getItemId(item: ItemData) {
            openItem(item)
        }
    }

    fun openItem(item: ItemData) {
        val itemFragment = ItemFragment(item)

        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

        val email = sSharedPreferences.getString("email", "")
        if(email == "NO") {
            transaction
                .replace(R.id.main_frm, LoginFragment())
                .addToBackStack(null)
                .commit()
            transaction.isAddToBackStackAllowed
        }
        else {
            transaction
                .replace(R.id.main_frm, itemFragment)
                .addToBackStack(null)
                .commit()
            transaction.isAddToBackStackAllowed
        }
    }

    lateinit var diary2RecyclerVIewAdapter : Diary2RecyclerVIewAdapter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.diary2IvList.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.diary2IvGrid.isChecked = !isChecked
        }
        binding.diary2IvGrid.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.diary2IvList.isChecked = !isChecked
        }

        val items = itemListAdapterToList()

        val homeLayoutManager = GridLayoutManager(context, 2)

        homeRecyclerViewAdapter = HomeRecyclerViewAdapter(items, itemList)

        binding.homeRecycler.apply {
            layoutManager = homeLayoutManager
            adapter = homeRecyclerViewAdapter
            homeRecyclerViewAdapter.filter.filter("wood")
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