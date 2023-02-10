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
import com.recordOfMemory.src.main.home.diary2.search.retrofit.Diary2SearchInterface
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetRecordResponse
import com.recordOfMemory.src.main.home.diary2.retrofit.models.GetRecordsResponse
import com.recordOfMemory.src.main.home.diary2.search.recycler.Diary2SearchRecyclerViewAdapter

class Diary2SearchFragment : BaseFragment<FragmentDiary2SearchBinding>(FragmentDiary2SearchBinding::bind, R.layout.fragment_diary2_search),
    Diary2SearchInterface {
    inner class itemListAdapterToList {
        // 일기 open function
        fun getItemId(item: GetRecordResponse) {
//            openItem(item)
            println(item)
            startActivity(Intent(activity, DaybookActivity::class.java)
                .putExtra("item",  item as java.io.Serializable))
        }
    }

    // 일기 open
    fun openItem(item: GetRecordResponse) {
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
        val listItemList = arguments?.parcelableArrayList<GetRecordResponse>("itemList")

        println(listItemList)


        val fm = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()

        binding.diary2SearchBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            transaction
                .replace(R.id.main_frm, Diary2Fragment())
                .addToBackStack(null)
                .commit()
            transaction.isAddToBackStackAllowed
        }

//        itemList.add(
//            GetRecordResponse(id = "1", title = "ss", content = "content", date = "23.01.01",user = "구리",
//            imgUrl = "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ",
//                cmtCnt = "1", likeCnt = "1", diary = "aa", status = "normal")
//        )
//
//        itemList.add(
//            GetRecordResponse(id = "1", title = "ss", content = "content", date = "23.01.01",user = "구리",
//            imgUrl = "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ",
//                cmtCnt = "1", likeCnt = "1", diary = "aa", status = "normal")
//        )
//
//        itemList.add(
//            GetRecordResponse(id = "1", title = "ss", content = "content", date = "23.01.01",user = "구리",
//            imgUrl = "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ",
//                cmtCnt = "1", likeCnt = "1", diary = "aa", status = "normal")
//        )
        val items = itemListAdapterToList()
        val diary2LayoutManager = LinearLayoutManager(context)
        val diary2RecyclerViewAdapter = Diary2SearchRecyclerViewAdapter(this, items, listItemList!!)
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

    fun <T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getParcelableArrayList(key)
        } else {
            @Suppress("DEPRECATION")
            getParcelableArrayList(key)
        }
    }
}