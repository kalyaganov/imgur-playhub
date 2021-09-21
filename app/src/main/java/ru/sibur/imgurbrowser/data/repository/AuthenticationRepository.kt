package ru.sibur.imgurbrowser.data.repository

import ru.sibur.imgurbrowser.data.room.dao.AuthDataDao
import ru.sibur.imgurbrowser.data.room.entities.mapToDB
import ru.sibur.imgurbrowser.data.room.entities.mapToDomain
import ru.sibur.imgurbrowser.model.AuthData

class AuthenticationRepository(private val authDataDao: AuthDataDao) {

    fun getAuthData(): AuthData? {
        return authDataDao.getAll().firstOrNull()?.mapToDomain()
    }

    fun updateAuthData(authData: AuthData) {
        authDataDao.newAuthData(authData.mapToDB())
    }

    fun clear() {
        authDataDao.deleteAll()
    }
}
