package ru.sibur.imgurbrowser.data.model

import ru.sibur.imgurbrowser.model.Image
import ru.sibur.imgurbrowser.model.ImgurImageHash
import ru.sibur.imgurbrowser.model.Post
import ru.sibur.imgurbrowser.model.Tag

fun TagResponse.mapToDomain(): Tag = Tag(
    name = this.displayName,
    accentColor = this.accentColor,
    coverImage = this.backgroundHash?.let { hash -> ImgurImageHash(hash) }
)

fun ImageResponse.mapToDomain(): Image = Image(
    title = title ?: "",
    description = description ?: "",
    link = link,
    width = width,
    height = height
)

fun PostResponse.mapToDomain(): Post = Post(
    id = id,
    title = title,
    description = description,
//    coverWidth = coverWidth,
//    coverHeight = coverHeight,
//    coverHash = ImgurImageHash(coverHash),
    images = images.map { it.mapToDomain() }
)
