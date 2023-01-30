package com.recordOfMemory.src.main.home.diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.recordOfMemory.databinding.FragmentDiaryAloneBinding

class DiaryAloneFragment : Fragment() {
    private var _binding: FragmentDiaryAloneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDiaryAloneBinding.inflate(inflater, container, false)
        val itemList = ArrayList<DiaryData>()

        itemList.add(DiaryData("나의 첫 다이어리"))
        itemList.add(DiaryData("비밀일기"))
        itemList.add(DiaryData("보라돌이와 함께"))
        itemList.add(DiaryData("나의 첫 다이어리"))
        itemList.add(DiaryData("비밀일기"))
        itemList.add(DiaryData("보라돌이와 함께"))
        itemList.add(DiaryData("나의 첫 다이어리"))
        itemList.add(DiaryData("비밀일기"))
        itemList.add(DiaryData("보라돌이와 함께"))

        val DiaryAdapter = DiaryAdapter(itemList)
        DiaryAdapter.notifyDataSetChanged()
        binding.diaryRv.adapter = DiaryAdapter

        val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        binding.diaryRv.layoutManager = manager
        // 3. 프래그먼트 레이아웃 뷰 반환
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


