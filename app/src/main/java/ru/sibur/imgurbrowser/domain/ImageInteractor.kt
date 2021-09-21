package ru.sibur.imgurbrowser.domain

import kotlinx.coroutines.withContext
import ru.sibur.imgurbrowser.data.Dispatchers
import ru.sibur.imgurbrowser.data.repository.ImageRepository
import ru.sibur.imgurbrowser.data.repository.TagsRepository
import ru.sibur.imgurbrowser.model.Image
import ru.sibur.imgurbrowser.model.Tag
import javax.inject.Inject

class ImageInteractor @Inject constructor(
    private val imageRepository: ImageRepository,
    private val dispatchers: Dispatchers
) {

    suspend fun getImage(hash: String): Image = withContext(dispatchers.io) {
        return@withContext imageRepository.getImageUrl(hash)
    }
}
