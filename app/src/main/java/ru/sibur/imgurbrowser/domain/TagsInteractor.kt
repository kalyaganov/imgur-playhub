package ru.sibur.imgurbrowser.domain

import kotlinx.coroutines.withContext
import ru.sibur.imgurbrowser.data.Dispatchers
import ru.sibur.imgurbrowser.data.repository.TagsRepository
import ru.sibur.imgurbrowser.model.Post
import ru.sibur.imgurbrowser.model.Tag
import javax.inject.Inject

class TagsInteractor @Inject constructor(
    private val tagsRepository: TagsRepository,
    private val dispatchers: Dispatchers
) {

    suspend fun getTags(): List<Tag> = withContext(dispatchers.io) {
        return@withContext tagsRepository.getTags()
    }

    suspend fun getTagPosts(tag: Tag, page: Int = 1): List<Post> = withContext(dispatchers.io) {
        return@withContext tagsRepository.getTagPosts(tag, page)
    }
}
