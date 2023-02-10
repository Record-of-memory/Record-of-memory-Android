package com.recordOfMemory.src.main.home.Diary

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentDiaryEmptyBinding

class DiaryEmptyFragment : BaseFragment<FragmentDiaryEmptyBinding>(FragmentDiaryEmptyBinding::bind, R.layout.fragment_diary_together) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diary_empty, container, false)
    }

}