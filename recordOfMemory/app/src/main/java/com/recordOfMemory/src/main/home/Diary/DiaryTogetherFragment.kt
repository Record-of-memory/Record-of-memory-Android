package com.recordOfMemory.src.main.home.Diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.recordOfMemory.R
import com.recordOfMemory.databinding.FragmentDiaryTogetherBinding

class DiaryTogetherFragment : Fragment() {
    private var _binding: FragmentDiaryTogetherBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDiaryTogetherBinding.inflate(inflater, container, false)
        val itemList = ArrayList<DiaryData>()

        itemList.add(DiaryData("우리 비밀일기장"))
        itemList.add(DiaryData("우정일기"))
        itemList.add(DiaryData("보라돌이와 함께"))
        itemList.add(DiaryData("우리 비밀일기장"))
        itemList.add(DiaryData("우정일기"))
        itemList.add(DiaryData("보라돌이와 함께"))
        itemList.add(DiaryData("우리 비밀일기장"))
        itemList.add(DiaryData("우정일기"))
        itemList.add(DiaryData("보라돌이와 함께"))

        val DiaryAdapter = DiaryAdapter(itemList)
        DiaryAdapter.notifyDataSetChanged()
        binding.diaryRv.adapter = DiaryAdapter

        val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        binding.diaryRv.layoutManager = manager
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}