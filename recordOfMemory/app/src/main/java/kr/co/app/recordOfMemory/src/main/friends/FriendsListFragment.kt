package kr.co.app.recordOfMemory.src.main.friends

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import kr.co.app.recordOfMemory.R
import kr.co.app.recordOfMemory.config.BaseFragment
import kr.co.app.recordOfMemory.databinding.FragmentFriendsListBinding

class FriendsListFragment : BaseFragment<FragmentFriendsListBinding>(FragmentFriendsListBinding::bind, R.layout.fragment_friends_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

        binding.friendsBtnFriendsList.isSelected = true
        binding.friendsBtnFriendsRequest.isSelected = false

        //showLoadingDialog(requireContext()) friends_being_together_iv_back

        binding.friendsBtnFriendsRequest.setOnClickListener { //친구요청 으로 전환
            transaction
                .replace(R.id.main_frm, fm.findFragmentByTag("RequestFriendsFragment") ?: RequestFriendsFragment(), "RequestFriendsFragment")
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