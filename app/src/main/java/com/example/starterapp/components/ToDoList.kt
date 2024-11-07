package com.example.starterapp.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.starterapp.db.todo.ToDo
import com.example.starterapp.pages.ShowEmptyMessage
import com.example.starterapp.viewModels.ToDoViewModel

@Composable
fun ToDoList(
    toDoList: List<ToDo>,
    toDoViewModel: ToDoViewModel
) {
    if (toDoList.isNotEmpty()) {
        // Separate the to-do items into completed and pending lists
        val pendingItems = toDoList.filter { !it.done }
        val completedItems = toDoList.filter { it.done }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp)
        ) {
            // Pending Items Section
            if (pendingItems.isNotEmpty()) {
                itemsIndexed(pendingItems) { _, item ->
                    ToDoItemComposable(
                        item = item,
                        onDelete = { toDoViewModel.deleteToDo(item.id) },
                        onUpdate = { updatedItem -> toDoViewModel.updateToDo(updatedItem) },
                        onDoneUpdate = { updatedDone ->
                            toDoViewModel.updateToDoDone(
                                item.id,
                                updatedDone
                            )
                        },
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            // Completed Items Section
            if (completedItems.isNotEmpty()) {
                item {
                    Text(
                        text = "Completed",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                itemsIndexed(completedItems) { _, item ->
                    ToDoItemComposable(
                        item = item,
                        onDelete = { toDoViewModel.deleteToDo(item.id) },
                        onUpdate = { updatedItem -> toDoViewModel.updateToDo(updatedItem) },
                        onDoneUpdate = { updatedDone ->
                            toDoViewModel.updateToDoDone(
                                item.id,
                                updatedDone
                            )
                        },
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    } else {
        ShowEmptyMessage()
    }
}