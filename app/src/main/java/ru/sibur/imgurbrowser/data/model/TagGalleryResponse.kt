package ru.sibur.imgurbrowser.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagGalleryResponse(
    @SerialName("data")
    val data: TagGalleryDataResponse
)

@Serializable
data class TagGalleryDataResponse(
    @SerialName("items")
    val items: List<PostResponse>
)
