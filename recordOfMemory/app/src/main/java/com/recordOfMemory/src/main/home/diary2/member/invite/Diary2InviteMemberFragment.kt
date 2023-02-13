package com.recordOfMemory.src.main.home.diary2.member.invite

import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.widget.EditText
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.databinding.FragmentDiary2InviteMemberBinding
import com.recordOfMemory.src.main.home.diary2.Diary2Fragment
import com.recordOfMemory.src.main.home.diary2.member.invite.recycler.Diary2InviteMemberRecyclerViewAdapter
import com.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import com.recordOfMemory.src.main.home.diary2.member.invite.retrofit.Diary2InviteInterface
import com.recordOfMemory.src.main.home.diary2.member.invite.retrofit.models.PostDiary2InviteRequest
import com.recordOfMemory.src.main.home.diary2.retrofit.Diary2Service

class Diary2InviteMemberFragment():BaseFragment<FragmentDiary2InviteMemberBinding>(FragmentDiary2InviteMemberBinding::bind, R.layout.fragment_diary2_invite_member),
    Diary2InviteInterface {
    private lateinit var diary2Fragment: Diary2Fragment
    constructor(diary2Fragment: Diary2Fragment) : this() {
        this.diary2Fragment = diary2Fragment
    }
    var diaryId = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var keyword = ""

        diaryId = arguments?.getString("diaryId").toString()



        val diary2LayoutManager = LinearLayoutManager(context)

        binding.diary2InviteMemberRecyclerView.apply {
            layoutManager = diary2LayoutManager
        }

        binding.diary2InviteMemberIvBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.diary2InviteMemberEtInputEmail.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                val editable = (v as EditText).text
                val start = v.selectionStart
                val end = v.selectionEnd
                if (start == end) {
                    if (start > 0) {
                        editable.delete(start - 1, start)
                    }
                } else {
                    editable.delete(start, end)
                }
            } else if (keyCode == KEYCODE_ENTER) {
                // 엔터 눌렀을때 행동
                keyword = if(binding.diary2InviteMemberEtInputEmail.text.isEmpty()) "" else binding.diary2InviteMemberEtInputEmail.text.toString()
                Diary2Service(this).tryGetUserEmail(email = keyword)
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

    override fun onPostDiary2Invite(email : String) {
        val postDiary2InviteRequest = PostDiary2InviteRequest(email = email, diaryId = diaryId)
        Diary2Service(this).tryPostDiaryInvite(postDiary2InviteRequest)
    }

    override fun onPostDiary2InviteSuccess(response: BaseResponse) {
        showCustomToast(response.information.message)
    }

    override fun onPostDiary2InviteFailure(message: String) {
        showCustomToast("초대 실패")
    }

    override fun onGetUserEmailSuccess(response: GetUsersResponse) {

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