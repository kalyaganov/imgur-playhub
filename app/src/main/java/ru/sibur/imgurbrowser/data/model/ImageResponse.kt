package ru.sibur.imgurbrowser.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("link")
    val link: String,
    @SerialName("mp4")
    val mp4Link: String? = null,
    @SerialName("gifv")
    val gifvLink: String? = null,
    @SerialName("hls")
    val hlsLink: String? = null,
    @SerialName("type")
    val type: String,
    @SerialName("animated")
    val animated: Boolean
)