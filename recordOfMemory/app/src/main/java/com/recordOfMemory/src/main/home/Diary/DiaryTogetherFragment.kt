package com.recordOfMemory.src.main.home.Diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.recordOfMemory.R
import com.recordOfMemory.databinding.FragmentDiaryAloneBinding
import com.recordOfMemory.databinding.FragmentDiaryTogetherBinding

class DiaryTogetherFragment : Fragment() {
    private var _binding: FragmentDiaryTogetherBinding? = null
    private val binding get() = _binding!!
    lateinit var diaryAdapter: DiaryAdapter
    val datas = mutableListOf<DiaryData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        _binding = FragmentDiaryTogetherBinding.inflate(inflater, container,false)

        diaryAdapter = DiaryAdapter(datas as ArrayList<DiaryData>)
        binding.diaryRv.adapter = diaryAdapter


        datas.apply {
            add(DiaryData(title = "우정일기"))
            add(DiaryData(title = "우리끼리"))
            add(DiaryData(title = "여행일기"))
            add(DiaryData(title = "우정일기"))
            add(DiaryData(title = "우리끼리"))
            add(DiaryData(title = "여행일기"))
            add(DiaryData(title = "우정일기"))
            add(DiaryData(title = "우리끼리"))
            add(DiaryData(title = "여행일기"))
        }

        diaryAdapter.datas = datas
        diaryAdapter.notifyDataSetChanged()

        val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        binding.diaryRv.layoutManager = manager
        return binding.root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}