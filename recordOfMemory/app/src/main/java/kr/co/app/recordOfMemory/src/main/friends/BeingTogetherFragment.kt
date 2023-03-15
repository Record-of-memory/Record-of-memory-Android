package kr.co.app.recordOfMemory.src.main.friends

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import kr.co.app.recordOfMemory.R
import kr.co.app.recordOfMemory.config.BaseFragment
import kr.co.app.recordOfMemory.databinding.FragmentBeingTogetherBinding


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