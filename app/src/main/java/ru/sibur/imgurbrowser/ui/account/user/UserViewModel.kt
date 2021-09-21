package ru.sibur.imgurbrowser.ui.account.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.sibur.imgurbrowser.domain.AccountInteractor
import ru.sibur.imgurbrowser.domain.AuthenticationInteractor
import ru.sibur.imgurbrowser.model.Image
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val authenticationInteractor: AuthenticationInteractor,
    private val accountInteractor: AccountInteractor
) :
    ViewModel() {
    sealed class ViewState {
        object Loading : ViewState()
        class Data(val images: List<Image>) : ViewState()
        class Error(val throwable: Throwable) : ViewState()
    }

    private val _viewStateFlow = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewStateFlow: StateFlow<ViewState> get() = _viewStateFlow

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            kotlin.runCatching { accountInteractor.getMyImages() }
                .onFailure { _viewStateFlow.emit(ViewState.Error(it)) }
                .onSuccess { _viewStateFlow.emit(ViewState.Data(it)) }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authenticationInteractor.logout()
        }
    }
}
