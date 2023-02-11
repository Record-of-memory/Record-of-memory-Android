package com.recordOfMemory.src.main.home.diary2.member.invite

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentDiary2InviteMemberBinding
import com.recordOfMemory.src.main.home.diary2.Diary2Fragment
import com.recordOfMemory.src.main.home.diary2.member.invite.recycler.Diary2InviteMemberRecyclerViewAdapter
import com.recordOfMemory.src.main.home.diary2.member.invite.retrofit.models.PostDiary2InviteResponse
import com.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import com.recordOfMemory.src.main.home.diary2.member.invite.retrofit.Diary2InviteInterface
import com.recordOfMemory.src.main.home.diary2.retrofit.Diary2Service

class Diary2InviteMemberFragment:BaseFragment<FragmentDiary2InviteMemberBinding>(FragmentDiary2InviteMemberBinding::bind, R.layout.fragment_diary2_invite_member),
    Diary2InviteInterface {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var keyword = ""

//        var itemList = ArrayList<GetMemberResponse>()

        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

//        itemList.add(
//            GetMemberResponse(userId = "1", nickname = "택현", imageUrl = null, email = "taekhyun@naver.com")
//        )
//
//        itemList.add(
//            GetMemberResponse(userId = "2", nickname = "초은", imageUrl = null, email = "choeun@naver.com")
//        )
//
//        itemList.add(
//            GetMemberResponse(userId = "3", nickname = "나령", imageUrl = null, email = "naryeong@naver.com")
//        )
//
//        itemList.add(
//            GetMemberResponse(userId = "4", nickname = "진경", imageUrl = null, email = "doli@naver.com")
//        )
//
//        itemList.add(
//            GetMemberResponse(userId = "5", nickname = "재윤", imageUrl = null, email = "jycho@naver.com")
//        )

        val diary2LayoutManager = LinearLayoutManager(context)
//        val diary2RecyclerViewAdapter = Diary2InviteMemberRecyclerViewAdapter(this, itemList)

        binding.diary2InviteMemberRecyclerView.apply {
            layoutManager = diary2LayoutManager
        }

        binding.diary2InviteMemberIvBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            transaction
                .replace(R.id.main_frm, Diary2Fragment())
                .addToBackStack(null)
                .commit()
            transaction.isAddToBackStackAllowed
        }

//        binding.diary2InviteMemberEtInputEmail.addTextChangedListener(object: TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                keyword = if(binding.diary2InviteMemberEtInputEmail.text.isEmpty()) "" else binding.diary2InviteMemberEtInputEmail.text.toString()
////                println(keyword)
//                diary2RecyclerViewAdapter.filter.filter(keyword)
//                binding.diary2InviteMemberRecyclerView.adapter = diary2RecyclerViewAdapter
//            }
//            override fun afterTextChanged(s: Editable?) {
//            }
//        })

        binding.diary2InviteMemberEtInputEmail.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KEYCODE_ENTER) {
                // 엔터 눌렀을때 행동
                keyword = if(binding.diary2InviteMemberEtInputEmail.text.isEmpty()) "" else binding.diary2InviteMemberEtInputEmail.text.toString()
//                val postDiary2InviteRequest = GetUserEmailRequest(email = keyword)
//                showLoadingDialog(requireContext())
                Diary2Service(this).tryGetUserEmail(email = keyword)
//                println(keyword)
//                diary2RecyclerViewAdapter.filter.filter(keyword)
//                binding.diary2InviteMemberRecyclerView.adapter = diary2RecyclerViewAdapter
            }

            true
        }
    }

    override fun onGetItemSize(itemSize: Int) {
        println("item size: $itemSize")
        if(itemSize != 0) {
            binding.diary2InviteEmpty.isGone = true
        }
        else {
            binding.diary2InviteEmpty.isVisible = true
        }
    }

    override fun onPostDiary2InviteSuccess(response: PostDiary2InviteResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostDiary2InviteFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetUserEmailSuccess(response: GetUsersResponse) {
        Log.e("here", "SUCCESS")

//        dismissLoadingDialog()
//        val itemList = ArrayList<GetUsersResponse>()
//        itemList.add(response)
//        println(itemList)
        binding.diary2InviteEmpty.isGone = true
        val diary2RecyclerViewAdapter = Diary2InviteMemberRecyclerViewAdapter(this, response.information)
        binding.diary2InviteMemberRecyclerView.adapter = diary2RecyclerViewAdapter



    }

    override fun onGetUSerEmailFailure(message: String) {
        binding.diary2InviteEmpty.isVisible = true
    }
}