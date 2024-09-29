package com.baseapplication.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DBMigration {
    val MIGRATION_2_3: Migration = object : Migration(5, 6) {
        public override fun migrate(database: SupportSQLiteDatabase) {
            /*database.execSQL("ALTER TABLE virtual_card " + " ADD COLUMN custId TEXT");*/
        }
    }
}
