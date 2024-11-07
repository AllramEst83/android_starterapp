package com.example.starterapp.db.todo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ToDoDao {
    @Query("SELECT * FROM ToDo ORDER BY createdAt DESC")
    fun getAllToDo(): LiveData<List<ToDo>>

    @Insert
    fun addTodo(todo: ToDo)

    @Query("DELETE FROM ToDo WHERE id = :id")
    fun deleteTodo(id: Int)

    @Query("UPDATE ToDo SET title = :title, content = :content WHERE id = :id")
    fun updateTodo(id: Int, title: String, content: String?)

    @Query("UPDATE ToDo SET done = :done WHERE id = :id")
    fun updateTodoDone(id: Int, done: Boolean)

}