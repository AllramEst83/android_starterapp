package com.example.starterapp.db.todo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ToDoDao {
    // Sorting and filter queries
    @Query("SELECT * FROM ToDo ORDER BY title ASC")
    fun getAllToDoTitleAsc(): LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo ORDER BY title DESC")
    fun getAllToDoTitleDesc(): LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo ORDER BY createdAt DESC")
    fun getAllToDoDesc(): LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo ORDER BY createdAt ASC")
    fun getAllToDoAsc(): LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo ORDER BY done DESC, createdAt DESC")
    fun getAllToDoStatusDoneFirst(): LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo ORDER BY done ASC, createdAt DESC")
    fun getAllToDoStatusNotDoneFirst(): LiveData<List<ToDo>>

    @Query("""
    SELECT * FROM ToDo
    WHERE (:doneStatus = 'ALL' OR done = (:doneStatus = 'DONE'))
    AND (
        :createdDateFilter = 'ALL' OR
        (:createdDateFilter = 'TODAY' AND date(createdAt / 1000, 'unixepoch') = date('now')) OR
        (:createdDateFilter = 'THIS_WEEK' AND strftime('%W', createdAt / 1000, 'unixepoch') = strftime('%W', 'now')) OR
        (:createdDateFilter = 'THIS_MONTH' AND strftime('%m', createdAt / 1000, 'unixepoch') = strftime('%m', 'now'))
    )
    ORDER BY 
        CASE 
            WHEN :sortOrder = 'CREATED_AT_ASCENDING' THEN createdAt 
        END ASC,
        CASE 
            WHEN :sortOrder = 'CREATED_AT_DESCENDING' THEN createdAt 
        END DESC,
        CASE 
            WHEN :sortOrder = 'TITLE_ASCENDING' THEN title 
        END ASC,
        CASE 
            WHEN :sortOrder = 'TITLE_DESCENDING' THEN title 
        END DESC
""")
    fun getFilteredToDo(
        doneStatus: String,
        createdDateFilter: String,
        sortOrder: String
    ): LiveData<List<ToDo>>



    // CRUD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTodo(todo: ToDo)

    @Query("UPDATE ToDo SET title = :title, content = :content WHERE id = :id")
    fun updateTodo(id: Int, title: String, content: String?)

    @Query("UPDATE ToDo SET done = :done WHERE id = :id")
    fun updateTodoDone(id: Int, done: Boolean)

    @Query("DELETE FROM ToDo WHERE id = :id")
    fun deleteTodo(id: Int)

    @Query("DELETE FROM ToDo")
    fun deleteAllToDos()
}
