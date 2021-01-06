package com.sp.base.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
@Database(entities = {UserProfileDetailsModel.class}, version = 1, exportSchema = false)
@TypeConverters({JsonTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract DBDAO getDbDAO();
}
