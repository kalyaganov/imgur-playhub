package ru.sibur.imgurbrowser.data.room.entities

import ru.sibur.imgurbrowser.model.AuthData

fun AuthDataDB.mapToDomain(): AuthData = AuthData(
    accountId = accountId,
    username = username,
    accessToken = accessToken,
    refreshToken = refreshToken,
    expiresIn = expiresIn
)

fun AuthData.mapToDB(): AuthDataDB = AuthDataDB(
    accountId = accountId,
    username = username,
    accessToken = accessToken,
    refreshToken = refreshToken,
    expiresIn = expiresIn
)
