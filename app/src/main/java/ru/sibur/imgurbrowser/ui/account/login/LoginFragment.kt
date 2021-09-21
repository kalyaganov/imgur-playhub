package ru.sibur.imgurbrowser.ui.account.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.sibur.imgurbrowser.R
import ru.sibur.imgurbrowser.databinding.FragmentLoginBinding
import ru.sibur.imgurbrowser.ui.account.auth.AuthBottomSheetFragment

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(ARG_AUTH_REQUEST) { _, bundle ->
            val result = AuthBottomSheetFragment.parseResult(bundle)
        }

        binding.loginButton.setOnClickListener {
            // Show bottom sheet with login
            AuthBottomSheetFragment.create(ARG_AUTH_REQUEST).show(
                parentFragmentManager,
                AuthBottomSheetFragment::class.qualifiedName
            )
        }
    }

    companion object {
        private const val ARG_AUTH_REQUEST = "arg-auth-request"
    }
}
