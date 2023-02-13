package com.recordOfMemory.src.main.signUp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.recordOfMemory.databinding.FragmentAcceptTermsBinding

class AcceptTermsFragment : Fragment() {
    private lateinit var viewBinding: FragmentAcceptTermsBinding

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
        viewBinding = FragmentAcceptTermsBinding.inflate(inflater, container, false)

        // 추가
        var myWebView: WebView = viewBinding.webView
        myWebView.webViewClient = WebViewClient()
        myWebView.loadUrl("https://admirable-gaufre-ec0b4e.netlify.app/")

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //뒤로가기 버튼 누르면
        viewBinding.backBtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        //다음 버튼 누르면
        viewBinding.nextBtn.setOnClickListener {
            signUpActivity!!.openFragmentSignUp(3)
        }
    }
}