package ru.sibur.imgurbrowser.model

data class AuthData(
    val accountId: Long,
    val username: String,
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long
)
