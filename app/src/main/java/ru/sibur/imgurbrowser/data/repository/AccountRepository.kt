package ru.sibur.imgurbrowser.data.repository

import ru.sibur.imgurbrowser.data.api.AccountApi
import ru.sibur.imgurbrowser.data.errors.ImgurCallHandler
import ru.sibur.imgurbrowser.data.model.mapToDomain
import ru.sibur.imgurbrowser.model.Image
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val accountApi: AccountApi,
    private val imgurCallHandler: ImgurCallHandler
) {

    fun getMyImages(): List<Image> {
        return imgurCallHandler.handleCallOrThrow(accountApi.getMyImages()) { this.data.map { it.mapToDomain() } }
    }
}
