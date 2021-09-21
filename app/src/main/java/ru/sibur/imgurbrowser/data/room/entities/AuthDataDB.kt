package ru.sibur.imgurbrowser.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth_data")
data class AuthDataDB(
    @PrimaryKey
    @ColumnInfo(name = "account_id")
    val accountId: Long,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "access_token")
    val accessToken: String,
    @ColumnInfo(name = "refresh_token")
    val refreshToken: String,
    @ColumnInfo(name = "expires_in")
    val expiresIn: Long
)
