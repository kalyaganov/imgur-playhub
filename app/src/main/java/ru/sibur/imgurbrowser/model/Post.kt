package ru.sibur.imgurbrowser.model

data class Post(
    val id: String,
    val title: String?,
    val description: String?,
//    val coverWidth: Int,
//    val coverHeight: Int,
//    val coverHash: ImgurImageHash,
    val images: List<Image>
)
