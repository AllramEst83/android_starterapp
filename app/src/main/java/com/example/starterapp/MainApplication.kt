package com.example.starterapp

import android.app.Application
import androidx.room.Room
import com.example.starterapp.db.todo.ToDoDatabase

class MainApplication : Application() {
    companion object{
        lateinit var toDoDatabase: ToDoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        toDoDatabase = Room.databaseBuilder(
            applicationContext,
            ToDoDatabase::class.java,
            ToDoDatabase.NAME
        ).addMigrations(ToDoDatabase.MIGRATION_1_2)
            .build()
    }
}