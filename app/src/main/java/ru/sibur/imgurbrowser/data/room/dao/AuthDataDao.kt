package ru.sibur.imgurbrowser.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ru.sibur.imgurbrowser.data.room.entities.AuthDataDB

@Dao
interface AuthDataDao {
    @Query("SELECT * FROM auth_data")
    fun getAll(): List<AuthDataDB>

    @Insert
    fun insertAll(vararg authDataDB: AuthDataDB)

    @Delete
    fun delete(authDataDB: Array<out AuthDataDB>)

    @Query("DELETE FROM auth_data")
    fun deleteAll()

    @Transaction
    fun newAuthData(authDataDB: AuthDataDB) {
        deleteAll()
        insertAll(authDataDB)
    }
}
