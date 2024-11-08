package com.example.starterapp.db.todo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ToDoDao {
    // Title sorting queries
    @Query("SELECT * FROM ToDo ORDER BY title ASC")
    fun getAllToDoTitleAsc(): LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo ORDER BY title DESC")
    fun getAllToDoTitleDesc(): LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo ORDER BY createdAt DESC")
    fun getAllToDoDesc(): LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo ORDER BY createdAt ASC")
    fun getAllToDoAsc(): LiveData<List<ToDo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTodo(todo: ToDo)

    @Query("DELETE FROM ToDo WHERE id = :id")
    fun deleteTodo(id: Int)

    @Query("UPDATE ToDo SET title = :title, content = :content WHERE id = :id")
    fun updateTodo(id: Int, title: String, content: String?)

    @Query("UPDATE ToDo SET done = :done WHERE id = :id")
    fun updateTodoDone(id: Int, done: Boolean)

}