package ru.sibur.imgurbrowser.ui.account.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.sibur.imgurbrowser.di.AuthUrl
import ru.sibur.imgurbrowser.domain.AuthenticationInteractor
import ru.sibur.imgurbrowser.model.AuthData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    @AuthUrl private val authUrl: String,
    private val authenticationInteractor: AuthenticationInteractor
) : ViewModel() {

    private val _loadUrlRequest: Channel<String> = Channel()
    val loadUrlRequest = _loadUrlRequest.receiveAsFlow()

    private val _done: Channel<Unit> = Channel()
    val done = _done.receiveAsFlow()

    init {
        viewModelScope.launch {
            _loadUrlRequest.send(authUrl)
        }
    }

    fun handleRedirectUrl(url: String) {
        try {
            val fragmentedUrl = url.split("#")
            if (fragmentedUrl.size == 2) {
                val params = fragmentedUrl[1]
                    .split("&")
                    .map { it.split("=") }
                    .associateBy({ it[0] }, { it[1] })

                val authData = AuthData(
                    params["account_id"]!!.toLong(),
                    params["account_username"]!!,
                    params["access_token"]!!,
                    params["refresh_token"]!!,
                    params["expires_in"]!!.toLong()
                )
                viewModelScope.launch {
                    authenticationInteractor.authenticate(authData)
                    _done.send(Unit)
                }
            }
        } catch (t: Throwable) {
            Timber.d("Unhandled url $url")
        }
    }

    // https://imgur.com/?state=APPLICATION_STATE#
// access_token=05bb8761f559210cf4aa6e9aa64a35b9c282dfb6&
// expires_in=315360000&
// token_type=bearer&
// refresh_token=95400a3b663b0d8073fb0e526bd8a36d8d5758a1&
// account_username=TelegramBumbot&
// account_id=40971293
}
