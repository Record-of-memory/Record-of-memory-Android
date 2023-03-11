package com.recordOfMemory.src.main.friends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentBeingTogetherBinding


class BeingTogetherFragment : BaseFragment<FragmentBeingTogetherBinding>(FragmentBeingTogetherBinding::bind, R.layout.fragment_being_together) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()


        binding.friendsBeingTogetherIvBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

    }



}