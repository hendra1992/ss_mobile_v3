package com.softwaresekolah.inosoft.data.core.local

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.softwaresekolah.inosoft.domain.core.models.User

@Database(entities = [User::class], version = 1)
abstract class SoftwareSekolahDatabse: RoomDatabase() {
    abstract val userDao: UserDao
}