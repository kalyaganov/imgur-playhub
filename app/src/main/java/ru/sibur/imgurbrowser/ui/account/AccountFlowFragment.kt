package ru.sibur.imgurbrowser.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import ru.sibur.imgurbrowser.R
import ru.sibur.imgurbrowser.launchAndRepeatWithViewLifecycle
import ru.sibur.imgurbrowser.ui.account.login.LoginFragment
import ru.sibur.imgurbrowser.ui.account.user.UserFragment

@AndroidEntryPoint
class AccountFlowFragment : Fragment(R.layout.fragment_flow_generic) {

    private val viewModel: AccountFlowViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchAndRepeatWithViewLifecycle {
            viewModel.showScreen.collect { screen ->
                when (screen) {
                    AccountFlowViewModel.Screen.User -> childFragmentManager.commit {
                        replace<UserFragment>(
                            R.id.flow_fragment_container_view
                        )
                    }
                    AccountFlowViewModel.Screen.Login -> childFragmentManager.commit {
                        replace<LoginFragment>(
                            R.id.flow_fragment_container_view
                        )
                    }
                }
            }
        }
    }
}
