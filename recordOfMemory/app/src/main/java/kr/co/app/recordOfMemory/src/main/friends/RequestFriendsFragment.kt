package kr.co.app.recordOfMemory.src.main.friends

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import kr.co.app.recordOfMemory.R
import kr.co.app.recordOfMemory.config.BaseFragment
import kr.co.app.recordOfMemory.databinding.FragmentFriendsRequestFriendsBinding

class RequestFriendsFragment : BaseFragment<FragmentFriendsRequestFriendsBinding>(FragmentFriendsRequestFriendsBinding::bind, R.layout.fragment_friends_request_friends) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

        binding.friendsBtnFriendsRequest.isSelected = true
        binding.friendsBtnFriendsList.isSelected = false

        //showLoadingDialog(requireContext()) friends_add_btn

        binding.friendsBtnFriendsList.setOnClickListener { //친구리스트로 전환
            transaction
                .replace(R.id.main_frm, fm.findFragmentByTag("FriendsListFragment") ?: FriendsListFragment(), "FriendsListFragment")
                .commit()
            transaction.isAddToBackStackAllowed
        }

        binding.friendsAddBtn.setOnClickListener { //함께하기로 전환
            transaction
                .replace(R.id.main_frm, fm.findFragmentByTag("BeingTogetherFragment") ?: BeingTogetherFragment(), "BeingTogetherFragment")
                .commit()
            transaction.isAddToBackStackAllowed
        }

    }
}