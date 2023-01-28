package com.recordOfMemory.src.main.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.recordOfMemory.databinding.FragmentOnboarding2Binding
import com.recordOfMemory.src.main.MainActivity

class OnboardingFragment2 : Fragment() {
    private lateinit var viewBinding: FragmentOnboarding2Binding

    var mainActivity: MainActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentOnboarding2Binding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //뒤로가기 누르면
        viewBinding.backBtn.setOnClickListener {
            mainActivity!!.openFragmentOnOnboarding(1)
        }
        //다음 누르면
        viewBinding.nextBtn.setOnClickListener {
            mainActivity!!.openFragmentOnOnboarding(3)
        }
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        viewBinding = null
//    }
}