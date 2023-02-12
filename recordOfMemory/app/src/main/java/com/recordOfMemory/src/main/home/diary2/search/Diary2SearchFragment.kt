package com.recordOfMemory.src.main.home.diary2.search

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.recordOfMemory.R
import com.recordOfMemory.config.BaseFragment
import com.recordOfMemory.databinding.FragmentDiary2SearchBinding
import com.recordOfMemory.src.daybook.DaybookActivity
import com.recordOfMemory.src.main.home.diary2.Diary2Fragment
import com.recordOfMemory.src.main.home.diary2.member.models.GetUserResponse
import com.recordOfMemory.src.main.home.diary2.search.retrofit.Diary2SearchInterface
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetMemberRecordResponse
import com.recordOfMemory.src.main.home.diary2.search.recycler.Diary2SearchRecyclerViewAdapter

class Diary2SearchFragment() : BaseFragment<FragmentDiary2SearchBinding>(FragmentDiary2SearchBinding::bind, R.layout.fragment_diary2_search),
    Diary2SearchInterface {

    private lateinit var diary2Fragment: Diary2Fragment
    constructor(diary2Fragment: Diary2Fragment) : this() {
        this.diary2Fragment = diary2Fragment
    }
    inner class itemListAdapterToList {
        // 일기 open function
        fun getItemId(item: GetMemberRecordResponse) {
//            openItem(item)
            println(item)
            startActivity(Intent(activity, DaybookActivity::class.java)
                .putExtra("item",  item as java.io.Serializable)
                .putExtra("screen_type","read"))
        }
    }

    // 일기 open
    fun openItem(item: GetMemberRecordResponse) {
//        startActivity(Intent(activity, DaybookActivity(item)::class.java))

//        val fm = requireActivity().supportFragmentManager
//        val transaction: FragmentTransaction = fm.beginTransaction()
//
//        val email = ApplicationClass.sSharedPreferences.getString("email", "")
//        if(email == "NO") {
//            transaction
//                .replace(R.id.main_frm, LoginFragment())
//                .addToBackStack(null)
//                .commit()
//            transaction.isAddToBackStackAllowed
//        }
//        else {
//            transaction
//                .replace(R.id.main_frm, itemFragment)
//                .addToBackStack(null)
//                .commit()
//            transaction.isAddToBackStackAllowed
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var keyword = ""
        val listItemList =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelableArrayList("itemList", GetMemberRecordResponse::class.java) as ArrayList<GetMemberRecordResponse>
        } else {
            arguments?.getSerializable("itemList") as ArrayList<GetMemberRecordResponse>
        }

        println(listItemList)

        binding.diary2SearchBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        val items = itemListAdapterToList()
        val diary2LayoutManager = LinearLayoutManager(context)
        val diary2RecyclerViewAdapter = Diary2SearchRecyclerViewAdapter(this, items, listItemList)
        binding.diary2SearchRecyclerView.apply {
            layoutManager = diary2LayoutManager
        }

        super.onViewCreated(view, savedInstanceState)

        binding.diary2SearchEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                keyword = if(binding.diary2SearchEt.text.isEmpty()) "" else binding.diary2SearchEt.text.toString()
                println(keyword)
                diary2RecyclerViewAdapter.filter.filter(keyword)
                binding.diary2SearchRecyclerView.adapter = diary2RecyclerViewAdapter
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    override fun onGetItemSize(itemSize: Int) {
        println("item size: $itemSize")
        if(itemSize != 0) {
            binding.diary2SearchEmpty.isGone = true
        }
        else {
            binding.diary2SearchEmpty.isVisible = true
        }
    }
}