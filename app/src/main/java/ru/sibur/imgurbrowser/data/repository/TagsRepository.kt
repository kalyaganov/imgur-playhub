package ru.sibur.imgurbrowser.data.repository

import ru.sibur.imgurbrowser.data.api.TagGalleryApi
import ru.sibur.imgurbrowser.data.api.TagsApi
import ru.sibur.imgurbrowser.data.errors.ImgurCallHandler
import ru.sibur.imgurbrowser.data.model.mapToDomain
import ru.sibur.imgurbrowser.model.Post
import ru.sibur.imgurbrowser.model.Tag
import javax.inject.Inject

class TagsRepository @Inject constructor(
    private val tagsApi: TagsApi,
    private val tagGalleryApi: TagGalleryApi,
    private val imgurCallHandler: ImgurCallHandler
) {

    fun getTags(): List<Tag> {
        return imgurCallHandler.handleCallOrThrow(tagsApi.getTags()) {
            data.tags.map { it.mapToDomain() }
        }
    }

    fun getTagPosts(tag: Tag, page: Int): List<Post> {
        return imgurCallHandler.handleCallOrThrow(
            tagGalleryApi.getItems(
                tag.name,
                "top",
                "all",
                page
            )
        ) {
            data.items.filter { it.images.isNotEmpty() }.map { it.mapToDomain() }
        }
    }
}
