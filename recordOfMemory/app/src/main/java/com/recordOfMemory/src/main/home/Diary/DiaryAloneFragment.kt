package com.recordOfMemory.src.main.home.Diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.recordOfMemory.R
import com.recordOfMemory.databinding.FragmentDiaryAloneBinding

class DiaryAloneFragment : Fragment() {
    private var _binding: FragmentDiaryAloneBinding? = null
    private val binding get() = _binding!!
    lateinit var diaryAdapter: DiaryAdapter
    val datas = mutableListOf<DiaryData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        _binding = FragmentDiaryAloneBinding.inflate(inflater, container,false)

        diaryAdapter = DiaryAdapter(datas as ArrayList<DiaryData>)
        binding.diaryRv.adapter = diaryAdapter


        datas.apply {
            add(DiaryData(title = "나의 첫 다이어리"))
            add(DiaryData(title = "비밀일기"))
            add(DiaryData(title = "보라돌이와 함께"))
            add(DiaryData(title = "나의 첫 다이어리"))
            add(DiaryData(title = "비밀일기"))
            add(DiaryData(title = "보라돌이와 함께"))
            add(DiaryData(title = "나의 첫 다이어리"))
            add(DiaryData(title = "비밀일기"))
            add(DiaryData(title = "보라돌이와 함께"))
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


