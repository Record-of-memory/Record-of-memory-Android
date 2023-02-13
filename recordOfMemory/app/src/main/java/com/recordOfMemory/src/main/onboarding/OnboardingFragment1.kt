package com.recordOfMemory.src.main.onboarding

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.recordOfMemory.config.BaseResponse
import com.recordOfMemory.databinding.FragmentOnboarding1Binding
import com.recordOfMemory.src.main.MainActivity
import com.recordOfMemory.src.main.home.diary.DiaryFragmentInterface
import com.recordOfMemory.src.main.home.diary.DiaryRetrofitInterface
import com.recordOfMemory.src.main.home.diary.retrofit.models.GetDiariesResponse
import com.recordOfMemory.src.main.home.diary.retrofit.models.PostDiariesRequest
import com.recordOfMemory.src.main.home.diary2.member.models.GetUsersResponse
import retrofit2.Call

class OnboardingFragment1 : Fragment(), DiaryFragmentInterface {
    private lateinit var viewBinding: FragmentOnboarding1Binding

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
        viewBinding = FragmentOnboarding1Binding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //시작할 땐 다음 버튼 비활성화
        viewBinding.nextBtn.isEnabled = false

        viewBinding.editRecordContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            //값 변경 시 실행되는 함수
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //editText에 값이 있다면 true, 없으면 false 반환
                viewBinding.nextBtn.isEnabled = viewBinding.editRecordContent.text.toString().isNotEmpty()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        //다음 누르면
        viewBinding.nextBtn.setOnClickListener {
            mainActivity!!.openFragmentOnOnboarding(2)
        }
        //건너뛰기 누르면
        viewBinding.skipBtn.setOnClickListener {
            mainActivity!!.hideMainFragment(false)
        }
    }

    override fun onGetDiariesSuccess(response: GetDiariesResponse) {}

    override fun onGetDiariesFailure(message: String) {}

    override fun onPostDiariesSuccess(response: BaseResponse) {}

    override fun onPostDiariesFailure(message: String) {}

    override fun onGetUsersSuccess(response: GetUsersResponse) {}

    override fun onGetUsersFailure(message: String) {}


}