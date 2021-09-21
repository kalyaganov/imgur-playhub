package ru.sibur.imgurbrowser.domain

import kotlinx.coroutines.withContext
import ru.sibur.imgurbrowser.data.Dispatchers
import ru.sibur.imgurbrowser.data.repository.AccountRepository
import ru.sibur.imgurbrowser.model.Image
import javax.inject.Inject

class AccountInteractor @Inject constructor(
    private val accountRepository: AccountRepository,
    private val dispatchers: Dispatchers
) {

    suspend fun getMyImages(): List<Image> = withContext(dispatchers.io) {
        return@withContext accountRepository.getMyImages()
    }
}
