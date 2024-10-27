package com.example.starterapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.starterapp.ToDo

@Database(entities = [ToDo::class], version = 1)
@TypeConverters(Converters::class)
abstract class ToDoDatabase : RoomDatabase() {
    companion object{
        const val NAME = "ToDo_DB"
    }
    abstract  fun getTodoDao() : ToDoDao
}