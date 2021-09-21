package ru.sibur.imgurbrowser.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import ru.sibur.imgurbrowser.model.AuthData

class AuthChangeListener(private val globalScope: CoroutineScope) {

    private val _authDataState: MutableStateFlow<AuthData?> = MutableStateFlow(null)
    val authDataState: Flow<AuthData?>
        get() = _authDataState.shareIn(
            globalScope,
            SharingStarted.Eagerly,
            1
        )

    fun authChanged(authData: AuthData?) {
        globalScope.launch { _authDataState.emit(authData) }
    }
}