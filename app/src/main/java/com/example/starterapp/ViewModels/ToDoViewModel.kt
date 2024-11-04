package com.example.starterapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starterapp.MainApplication
import com.example.starterapp.db.todo.ToDo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date
import java.time.Instant

class ToDoViewModel: ViewModel() {

    val toDoDao = MainApplication.Companion.toDoDatabase.getTodoDao()

    val toDoList: LiveData<List<ToDo>> = toDoDao.getAllToDo()

    fun addToDo(title: String) {
        viewModelScope.launch(Dispatchers.IO){
            toDoDao.addTodo(ToDo(title = title, createdAt = Date.from(Instant.now())))
        }
    }

    fun deleteToDo(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            toDoDao.deleteTodo(id = id)
        }
    }
}

