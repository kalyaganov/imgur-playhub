package ru.sibur.imgurbrowser.ui.account.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.os.bundleOf
import androidx.core.view.doOnLayout
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import ru.sibur.imgurbrowser.R
import ru.sibur.imgurbrowser.databinding.FragmentDialogAuthBinding
import ru.sibur.imgurbrowser.launchAndRepeatWithViewLifecycle

@AndroidEntryPoint
class AuthBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel: AuthViewModel by viewModels()

    private val binding: FragmentDialogAuthBinding by viewBinding(FragmentDialogAuthBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.doOnLayout {
            val behavior = BottomSheetBehavior.from(binding.bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.peekHeight = requireContext().resources.displayMetrics.heightPixels

            binding.authWebView.layoutParams = binding.authWebView.layoutParams.apply {
                height = requireContext().resources.displayMetrics.heightPixels
            }
        }

        binding.authWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                request?.url?.toString()?.let { url -> viewModel.handleRedirectUrl(url) }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        binding.authWebView.isVerticalScrollBarEnabled = true
        binding.authWebView.settings.javaScriptEnabled = true

        launchAndRepeatWithViewLifecycle {
            viewModel.loadUrlRequest.collect { url -> binding.authWebView.loadUrl(url) }
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.done.collect { dismiss() }
        }
    }

    companion object {
        private const val ARG_REQUEST_KEY = "arg-request-key"

        fun create(requestKey: String): AuthBottomSheetFragment {
            return AuthBottomSheetFragment().apply {
                arguments = bundleOf(ARG_REQUEST_KEY to requestKey)
            }
        }

        fun parseResult(bundle: Bundle): String = ""
    }
}
