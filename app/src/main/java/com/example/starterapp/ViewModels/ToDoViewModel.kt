package com.example.starterapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.starterapp.MainApplication
import com.example.starterapp.db.todo.ToDo
import com.example.starterapp.models.CreatedDateFilter
import com.example.starterapp.models.DoneStatusFilter
import com.example.starterapp.models.SortOrder
import com.example.starterapp.models.ToDoFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date
import java.time.Instant
import com.example.starterapp.BuildConfig


class ToDoViewModel: ViewModel() {

    /* Only use this when running on your computer
    init {
        if (BuildConfig.DEBUG) {
            populateDummyData()
        }
    }
    */

    private val toDoDao = MainApplication.toDoDatabase.getTodoDao()

    // LiveData to hold the current sort order
    private val _sortOrder = MutableLiveData(SortOrder.CREATED_AT_DESCENDING)
    val sortOrder: LiveData<SortOrder> = _sortOrder

    // LiveData to hold the current filter
    private val _filter = MutableLiveData(ToDoFilter())
    val filter: LiveData<ToDoFilter> = _filter

    // Combined LiveData to observe both sort and filter
    private val combinedSortFilter: LiveData<Pair<SortOrder, ToDoFilter>> = MediatorLiveData<Pair<SortOrder, ToDoFilter>>().apply {
        addSource(_sortOrder) { sort ->
            value = Pair(sort, _filter.value ?: ToDoFilter())
        }
        addSource(_filter) { filter ->
            value = Pair(_sortOrder.value ?: SortOrder.CREATED_AT_DESCENDING, filter)
        }
    }

    val toDoList: LiveData<List<ToDo>> = combinedSortFilter.switchMap { (sortOrder, filter) ->
        toDoDao.getFilteredToDo(
            doneStatus = when (filter.doneStatus) {
                DoneStatusFilter.ALL -> "ALL"
                DoneStatusFilter.DONE -> "DONE"
                DoneStatusFilter.NOT_DONE -> "NOT_DONE"
            },
            createdDateFilter = when (filter.createdDateFilter) {
                CreatedDateFilter.ALL -> "ALL"
                CreatedDateFilter.TODAY -> "TODAY"
                CreatedDateFilter.THIS_WEEK -> "THIS_WEEK"
                CreatedDateFilter.THIS_MONTH -> "THIS_MONTH"
            },
            sortOrder = sortOrder.name
        )
    }
    // Function to set a specific sort order
    fun setSortOrder(order: SortOrder) {
        _sortOrder.value = order
    }

    // Function to set filter
    fun setFilter(filter: ToDoFilter) {
        _filter.value = filter
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

    fun deleteAllToDo(){
        viewModelScope.launch(Dispatchers.IO){
            toDoDao.deleteAllToDos()
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


    fun populateDummyData() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentTime = Instant.now() // Base time for reference
            val todos = listOf(
                ToDo(title = "Buy groceries", content = "Buy milk, eggs, bread", createdAt = Date.from(currentTime.minusSeconds(0)), done = false),
                ToDo(title = "Morning exercise", content = "Run 5km", createdAt = Date.from(currentTime.minusSeconds(86400 + 60)), done = true),
                ToDo(title = "Read book", content = "Read 'Clean Code'", createdAt = Date.from(currentTime.minusSeconds(86400 * 7 + 120)), done = false),
                ToDo(title = "Finish project", content = "Complete the UI for project", createdAt = Date.from(currentTime.minusSeconds(86400 * 30 + 180)), done = true),
                ToDo(title = "Prepare presentation", content = "Slides for team meeting", createdAt = Date.from(currentTime.minusSeconds(3600 + 240)), done = true),
                ToDo(title = "Organize files", content = "Clean up desktop and folders", createdAt = Date.from(currentTime.minusSeconds(86400 * 2 + 300)), done = false),
                ToDo(title = "Plan vacation", content = "Research destinations", createdAt = Date.from(currentTime.minusSeconds(86400 * 14 + 360)), done = true),
                ToDo(title = "Schedule dentist appointment", content = "Book appointment for next week", createdAt = Date.from(currentTime.minusSeconds(86400 * 3 + 420)), done = false),
                ToDo(title = "Write blog post", content = "Topic on Android development", createdAt = Date.from(currentTime.minusSeconds(3600 * 5 + 480)), done = true),
                ToDo(title = "Check emails", content = "Respond to important emails", createdAt = Date.from(currentTime.minusSeconds(86400 * 5 + 540)), done = false)
            )

            // Clear existing data for a clean test environment
            toDoDao.deleteAllToDos()

            // Insert dummy todos
            todos.forEach { toDoDao.addTodo(it) }
        }
    }
}

