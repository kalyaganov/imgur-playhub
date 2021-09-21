package ru.sibur.imgurbrowser.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String?,
//    @SerialName("cover_width")
//    val coverWidth: Int,
//    @SerialName("cover_height")
//    val coverHeight: Int,
//    @SerialName("cover")
//    val coverHash: String,
    @SerialName("images")
    val images: List<ImageResponse> = emptyList()
)
