package kr.co.app.recordOfMemory.src.main.signUp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.co.app.recordOfMemory.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private lateinit var viewBinding: FragmentStartBinding

    var signUpActivity: SignUpActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpActivity = context as SignUpActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentStartBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //이메일로 로그인하기 버튼 누르면
        viewBinding.loginEmailBtn.setOnClickListener {
            signUpActivity!!.openFragmentSignUp(1)
        }
        //아직 회원이 아니신가요(회원가입하기) 버튼 누르면
        viewBinding.joinMembershipBtn.setOnClickListener {
            signUpActivity!!.openFragmentSignUp(2)
        }
    }

}