package ru.sibur.imgurbrowser.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.sibur.imgurbrowser.data.serializers.ColorSerializer

@Serializable
data class TagsResponse(
    @SerialName("data")
    val data: TagsDataResponse
)

@Serializable
data class TagsDataResponse(
    @SerialName("tags")
    val tags: List<TagResponse>
)

@Serializable
data class TagResponse(
    @SerialName("name")
    val name: String,
    @SerialName("display_name")
    val displayName: String,
    @SerialName("background_hash")
    val backgroundHash: String?,
    @SerialName("accent")
    @Serializable(with = ColorSerializer::class)
    val accentColor: Long?
)
