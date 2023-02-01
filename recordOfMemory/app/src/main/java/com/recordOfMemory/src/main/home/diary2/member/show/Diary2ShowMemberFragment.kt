package com.recordOfMemory.src.main.home.diary2.member.show

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentDiary2ShowMemberBinding
import com.recordOfMemory.src.main.home.diary2.Diary2Fragment
import com.recordOfMemory.src.main.home.diary2.member.models.GetMemberResponse
import com.recordOfMemory.src.main.home.diary2.member.show.recycler.Diary2ShowMemberRecyclerViewAdapter

class Diary2ShowMemberFragment:
    BaseFragment<FragmentDiary2ShowMemberBinding>(FragmentDiary2ShowMemberBinding::bind, R.layout.fragment_diary2_show_member) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var itemList = ArrayList<GetMemberResponse>()

        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

        itemList.add(
            GetMemberResponse(
                userId = "1",
                nickname = "택현",
                imageUrl = null,
                email = "taekhyun@naver.com"
            )
        )

        val diary2LayoutManager = LinearLayoutManager(context)
        val diary2RecyclerViewAdapter = Diary2ShowMemberRecyclerViewAdapter(itemList)

        binding.diary2ShowMemberRecyclerView.apply {
            layoutManager = diary2LayoutManager
            if(diary2RecyclerViewAdapter.itemCount > 0) {
                binding.diary2ShowMemberEmpty.isGone = true
            }
            else {
                binding.diary2ShowMemberLinearRecyclerView.isGone = true
                binding.diary2ShowMemberRecyclerView.isGone = true
            }
            adapter = diary2RecyclerViewAdapter
        }

        binding.diary2ShowMemberBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            transaction
                .replace(R.id.main_frm, Diary2Fragment())
                .addToBackStack(null)
                .commit()
            transaction.isAddToBackStackAllowed
        }
    }
}