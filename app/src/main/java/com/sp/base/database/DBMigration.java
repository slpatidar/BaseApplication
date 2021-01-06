package com.sp.base.database;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

public class DBMigration {

    public static final Migration MIGRATION_2_3 = new Migration(5, 6) {
        @Override
        public void migrate(@NotNull SupportSQLiteDatabase database) {
            /*database.execSQL("ALTER TABLE virtual_card " + " ADD COLUMN custId TEXT");*/
        }
    };
}
