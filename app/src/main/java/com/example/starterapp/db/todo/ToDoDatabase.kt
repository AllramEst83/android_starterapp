package com.example.starterapp.db.todo

import androidx.room.Database;
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ToDo::class], version = 1)
@TypeConverters(Converters::class)
abstract class ToDoDatabase : RoomDatabase() {
    companion object{
        const val NAME = "ToDo_DB"
    }
    abstract  fun getTodoDao() : ToDoDao
}