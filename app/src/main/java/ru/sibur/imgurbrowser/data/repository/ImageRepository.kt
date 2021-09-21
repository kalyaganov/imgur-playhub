package ru.sibur.imgurbrowser.data.repository

import ru.sibur.imgurbrowser.data.api.ImageApi
import ru.sibur.imgurbrowser.data.errors.ImgurCallHandler
import ru.sibur.imgurbrowser.data.model.mapToDomain
import ru.sibur.imgurbrowser.model.Image
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val imageApi: ImageApi,
    private val imgurCallHandler: ImgurCallHandler
) {

    fun getImageUrl(hash: String): Image {
        return imgurCallHandler.handleCallOrThrow(imageApi.getImage(hash)) {
            this.data.mapToDomain()
        }
    }
}
