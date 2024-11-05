package com.example.starterapp.db.todo

import androidx.room.Database;
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [ToDo::class], version = 2)
@TypeConverters(Converters::class)
abstract class ToDoDatabase : RoomDatabase() {
    companion object{
        const val NAME = "ToDo_DB"
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Define any changes made to the schema here, if needed
                // For example: Alter table, add new column, etc.
                database.execSQL("ALTER TABLE ToDo ADD COLUMN content TEXT")
            }
        }
    }
    abstract  fun getTodoDao() : ToDoDao
}