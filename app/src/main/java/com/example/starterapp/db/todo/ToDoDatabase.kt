package com.example.starterapp.db.todo

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [ToDo::class], version = 3)
@TypeConverters(Converters::class)
abstract class ToDoDatabase : RoomDatabase() {
    companion object {
        const val NAME = "ToDo_DB"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ToDo ADD COLUMN content TEXT")
            }
        }
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ToDo ADD COLUMN done INTEGER DEFAULT 0 NOT NULL")
            }
        }
    }
    abstract  fun getTodoDao() : ToDoDao
}