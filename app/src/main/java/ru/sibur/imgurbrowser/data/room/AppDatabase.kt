package ru.sibur.imgurbrowser.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.sibur.imgurbrowser.data.room.dao.AuthDataDao
import ru.sibur.imgurbrowser.data.room.entities.AuthDataDB

@Database(entities = [AuthDataDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authDataDao(): AuthDataDao
}
