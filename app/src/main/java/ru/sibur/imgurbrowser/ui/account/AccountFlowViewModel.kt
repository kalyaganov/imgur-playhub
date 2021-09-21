package ru.sibur.imgurbrowser.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.sibur.imgurbrowser.domain.AuthChangeListener
import ru.sibur.imgurbrowser.domain.AuthenticationInteractor
import javax.inject.Inject

@HiltViewModel
class AccountFlowViewModel @Inject constructor(
    private val authenticationInteractor: AuthenticationInteractor,
    private val authChangeListener: AuthChangeListener
) : ViewModel() {

    enum class Screen {
        User,
        Login
    }

    private var currentScreen: Screen? = null

    private val _showScreen: Channel<Screen> = Channel()
    val showScreen = _showScreen.receiveAsFlow()

    init {
        viewModelScope.launch {
            kotlin.runCatching { authenticationInteractor.getAuthData() }
                .onSuccess { authData ->
                    val screen = if (authData == null) Screen.Login else Screen.User
                    currentScreen = screen
                    _showScreen.send(screen)
                }
            authChangeListener.authDataState.collect { authData ->
                val screen = if (authData == null) Screen.Login else Screen.User
                if (screen != currentScreen) {
                    currentScreen = screen
                    _showScreen.send(screen)
                }
            }
        }
    }
}
