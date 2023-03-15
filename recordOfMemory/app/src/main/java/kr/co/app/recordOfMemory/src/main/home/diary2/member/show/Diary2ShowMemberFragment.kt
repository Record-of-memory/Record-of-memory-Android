package kr.co.app.recordOfMemory.src.main.home.diary2.member.show

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.app.recordOfMemory.R
import kr.co.app.recordOfMemory.config.BaseFragment
import kr.co.app.recordOfMemory.databinding.FragmentDiary2ShowMemberBinding
import kr.co.app.recordOfMemory.src.main.home.diary2.Diary2Fragment
import kr.co.app.recordOfMemory.src.main.home.diary2.member.models.GetUserResponse
import kr.co.app.recordOfMemory.src.main.home.diary2.member.show.recycler.Diary2ShowMemberRecyclerViewAdapter

class Diary2ShowMemberFragment():
    BaseFragment<FragmentDiary2ShowMemberBinding>(FragmentDiary2ShowMemberBinding::bind, R.layout.fragment_diary2_show_member) {
    private lateinit var diary2Fragment: Diary2Fragment
    lateinit var userList : ArrayList<GetUserResponse>
    constructor(diary2Fragment: Diary2Fragment) : this() {
        this.diary2Fragment = diary2Fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            userList = arguments?.getParcelableArrayList("userList", GetUserResponse::class.java) as ArrayList<GetUserResponse>
        } else {
            userList = arguments?.getSerializable("userList") as ArrayList<GetUserResponse>
        }

        val diary2LayoutManager = LinearLayoutManager(context)
        val diary2RecyclerViewAdapter = Diary2ShowMemberRecyclerViewAdapter(userList)

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
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}