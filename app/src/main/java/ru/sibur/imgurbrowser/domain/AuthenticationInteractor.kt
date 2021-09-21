package ru.sibur.imgurbrowser.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.sibur.imgurbrowser.data.repository.AuthenticationRepository
import ru.sibur.imgurbrowser.model.AuthData

class AuthenticationInteractor(
    private val authenticationRepository: AuthenticationRepository,
    private val authChangeListener: AuthChangeListener,
    coroutineScope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher
) {

    init {
        coroutineScope.launch(dispatcher) {
            kotlin.runCatching { authChangeListener.authChanged(authenticationRepository.getAuthData()) }
        }
    }

    suspend fun authenticate(authData: AuthData) = withContext(dispatcher) {
        authenticationRepository.updateAuthData(authData)
        authChangeListener.authChanged(authData)
    }

    suspend fun getAuthData(): AuthData? = withContext(dispatcher) {
        return@withContext authenticationRepository.getAuthData()
    }

    suspend fun logout() = withContext(dispatcher){
        authenticationRepository.clear()
        authChangeListener.authChanged(null)
    }
}
