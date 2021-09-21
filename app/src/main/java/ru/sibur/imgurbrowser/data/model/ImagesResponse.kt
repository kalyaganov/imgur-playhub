package ru.sibur.imgurbrowser.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesResponse(
    @SerialName("data")
    val data: ImageResponse
)
