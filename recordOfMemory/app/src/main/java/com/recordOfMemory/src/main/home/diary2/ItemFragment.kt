package com.recordOfMemory.src.main.home.diary2

import android.os.Bundle
import android.view.View
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentItemBinding
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetDiary2Response

class ItemActivity(val item: GetDiary2Response): BaseFragment<FragmentItemBinding>(FragmentItemBinding::bind, R.layout.fragment_item) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        val toolbar = binding.toolbar
//        (activity as AppCompatActivity).setSupportActionBar(toolbar)
//        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
//
//        Glide.with(view).load("https://s3-alpha-sig.figma.com/img/9eaa/3523/b72e89689756d61764d40e5bd8fd131c?Expires=1653264000&Signature=DJniLR-QbDjVZv-mQE0Vizn4EAy3TrgzJmcGm~NADl3duEOqzTXhOWrQ9dVSMiMG5WOPMKrxW31LVpk1GOG6AHI5o3T7p7K7Llb8bh2d3-s9qpfsOsweBc4z8JhaZPFJ0dzxLljbLWwprJVSlLhMZ7cAQHKoOB4POZ2yIERag9dZ1bhaBKA9zKLC~Y1d79e0kVElYvZusHDkzfSd1ky0ljByq8jMfGRWT0rcyGnDQN4xX7DaYU6-TwdWNMHWhS1pQG-ut8Sz7egpq4oa8ZoER2m8j7Q05j6WDRhOHpfbjBjvEyhOV7v1xARLjUkkMbOB9XbT3xJvf73Tiwdvo2XH5w__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA").into(binding.itemMainImg)
//        binding.shopName.text = item.name
//        binding.toolbar.title = item.name
//        binding.itemTitleName.text = item.name
//        binding.artistName.text = item.artist
//        binding.period.text = item.period.toString() + " 일"
//        binding.price.text = item.price.toString()
//        binding.itemDetail.text = item.description
//
//        binding.shopName.setOnClickListener {
//            val fm = requireActivity().supportFragmentManager
//            val transaction: FragmentTransaction = fm.beginTransaction()
//
//            transaction
//                .replace(R.id.main_frm, ShopFragment())
//                .addToBackStack(null)
//                .commit()
//            transaction.isAddToBackStackAllowed
//        }
//
//        binding.btnOrder.setOnClickListener {
//            val fm = requireActivity().supportFragmentManager
//            val transaction: FragmentTransaction = fm.beginTransaction()
//
//            transaction
//                .replace(R.id.main_frm, OrderFragment(item))
//                .addToBackStack(null)
//                .commit()
//            transaction.isAddToBackStackAllowed
//        }

    }

}