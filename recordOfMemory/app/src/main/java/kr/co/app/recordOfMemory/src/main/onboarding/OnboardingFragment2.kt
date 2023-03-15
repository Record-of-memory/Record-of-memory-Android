package kr.co.app.recordOfMemory.src.main.onboarding

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.co.app.recordOfMemory.databinding.FragmentOnboarding2Binding
import kr.co.app.recordOfMemory.src.main.MainActivity

class OnboardingFragment2() : Fragment() {
    private lateinit var viewBinding: FragmentOnboarding2Binding

    var mainActivity: MainActivity? = null
    private lateinit var key: String
    private lateinit var value: String

    constructor(key: String, value: String) : this() {
        this.key = key
        this.value = value
    }
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
        //시작할 땐 다음 버튼 비활성화
        viewBinding.nextBtn.isEnabled = false

        viewBinding.editRecordTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            //값 변경 시 실행되는 함수
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //editText에 값이 있다면 true, 없으면 false 반환
                viewBinding.nextBtn.isEnabled = viewBinding.editRecordTitle.text.toString().isNotEmpty()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        //뒤로가기 누르면
        viewBinding.backBtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        //다음 누르면
        viewBinding.nextBtn.setOnClickListener {
            mainActivity!!.setString("title", viewBinding.editRecordTitle.text.toString())
            mainActivity!!.openFragmentOnOnboarding(3)
        }
    }
}