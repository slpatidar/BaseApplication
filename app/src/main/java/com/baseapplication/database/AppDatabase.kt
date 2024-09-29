package com.baseapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [UserProfileDetailsModel::class], version = 1, exportSchema = false)
@TypeConverters(*[JsonTypeConverter::class])
abstract class AppDatabase() : RoomDatabase() {
    abstract val dbDAO: DBDAO
}
