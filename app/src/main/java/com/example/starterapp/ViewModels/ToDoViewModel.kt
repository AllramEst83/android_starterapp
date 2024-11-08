package com.example.starterapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.starterapp.MainApplication
import com.example.starterapp.db.todo.ToDo
import com.example.starterapp.models.SortOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date
import java.time.Instant

class ToDoViewModel: ViewModel() {

    private val toDoDao = MainApplication.toDoDatabase.getTodoDao()

    // LiveData to hold the current sort order
    private val _sortOrder = MutableLiveData(SortOrder.CREATED_AT_DESCENDING)
    val sortOrder: LiveData<SortOrder> = _sortOrder

    // LiveData to hold the sorted list based on sortOrder
    val toDoList: LiveData<List<ToDo>> = _sortOrder.switchMap { order ->
        when (order) {
            SortOrder.CREATED_AT_ASCENDING -> toDoDao.getAllToDoAsc()
            SortOrder.CREATED_AT_DESCENDING -> toDoDao.getAllToDoDesc()
            SortOrder.TITLE_ASCENDING -> toDoDao.getAllToDoTitleAsc()
            SortOrder.TITLE_DESCENDING -> toDoDao.getAllToDoTitleDesc()
        }
    }

    // Function to set a specific sort order
    fun setSortOrder(order: SortOrder) {
        _sortOrder.value = order
    }

    // Function to toggle sort order
    fun toggleSortOrder() {
        _sortOrder.value = when (_sortOrder.value) {
            SortOrder.CREATED_AT_ASCENDING -> SortOrder.CREATED_AT_DESCENDING
            SortOrder.CREATED_AT_DESCENDING -> SortOrder.CREATED_AT_ASCENDING
            SortOrder.TITLE_DESCENDING -> SortOrder.TITLE_ASCENDING
            SortOrder.TITLE_ASCENDING -> SortOrder.TITLE_DESCENDING
            else -> SortOrder.CREATED_AT_DESCENDING

        }
    }

    fun addToDo(title: String, content: String = "") {
        viewModelScope.launch(Dispatchers.IO){
            toDoDao.addTodo(
                ToDo(
                    title = title,
                    content = content,
                    createdAt = Date.from(Instant.now()),
                    done = false)
            )
        }
    }

    fun deleteToDo(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            toDoDao.deleteTodo(id = id)
        }
    }

    fun updateToDo(todo: ToDo){
        viewModelScope.launch(Dispatchers.IO){
            toDoDao.updateTodo(todo.id, todo.title, todo.content)
        }
    }

    fun updateToDoDone(id: Int, done: Boolean){
        viewModelScope.launch(Dispatchers.IO){
            toDoDao.updateTodoDone(id, done)
        }

    }
}

