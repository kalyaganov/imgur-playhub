package ru.sibur.imgurbrowser.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountImagesResponse(
    @SerialName("data")
    val data: List<ImageResponse>
)
