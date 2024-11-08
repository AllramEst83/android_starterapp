package com.example.starterapp.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.starterapp.db.todo.ToDo
import com.example.starterapp.pages.ShowEmptyMessage
import com.example.starterapp.utils.blendWith
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
                            toDoViewModel.updateToDoDone(item.id, updatedDone)
                        },
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }

            // Completed Items Section
            if (completedItems.isNotEmpty()) {
                item {
                    // Pill-shaped container for "Completed" title and delete icon
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
                        shape = MaterialTheme.shapes.small,
                        color = MaterialTheme.colorScheme.surfaceVariant
                            .copy(alpha = 0.8f)
                            .blendWith(MaterialTheme.colorScheme.primaryContainer, 0.2f)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(
                                    horizontal = 10.dp,
                                    vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Completed",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.weight(1f),
                                color =  MaterialTheme.colorScheme.primary
                            )

                            IconButton(
                                onClick = {
                                    toDoViewModel.deleteAllToDo()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete all completed",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }

                itemsIndexed(completedItems) { _, item ->
                    ToDoItemComposable(
                        item = item,
                        onDelete = { toDoViewModel.deleteToDo(item.id) },
                        onUpdate = { updatedItem -> toDoViewModel.updateToDo(updatedItem) },
                        onDoneUpdate = { updatedDone ->
                            toDoViewModel.updateToDoDone(item.id, updatedDone)
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
