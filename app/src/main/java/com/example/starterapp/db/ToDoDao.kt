package com.example.starterapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.starterapp.ToDo

@Dao
interface ToDoDao {
    @Query("SELECT * FROM ToDo")
    fun getAllToDo(): LiveData<List<ToDo>>

    @Insert
    fun addTodo(todo: ToDo)

    @Query("DELETE FROM ToDo WHERE id = :id")
    fun deleteTodo(id: Int)
}