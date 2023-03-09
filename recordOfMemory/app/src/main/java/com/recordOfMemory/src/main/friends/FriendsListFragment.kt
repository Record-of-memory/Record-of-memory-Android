package com.recordOfMemory.src.main.friends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentFriendsListBinding

class FriendsListFragment : BaseFragment<FragmentFriendsListBinding>(FragmentFriendsListBinding::bind, R.layout.fragment_friends_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

        binding.friendsBtnFriendsList.isSelected = true

        showLoadingDialog(requireContext())

        binding.friendsBtnFriendsRequest.setOnClickListener { //친구요청 으로 전환
            transaction
                .replace(R.id.main_frm, fm.findFragmentByTag("RequestFriendsFragment") ?: RequestFriendsFragment(), "RequestFriendsFragment")
                .commit()
            transaction.isAddToBackStackAllowed
        }
    }

}