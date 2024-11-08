package com.example.starterapp.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.starterapp.models.CreatedDateFilter
import com.example.starterapp.models.DoneStatusFilter
import com.example.starterapp.models.ToDoFilter
import com.example.starterapp.viewModels.ToDoViewModel

@Composable
fun FilterMenu(toDoViewModel: ToDoViewModel) {
    var expanded by remember { mutableStateOf(false) }

    // Observe the current filter state
    val currentFilter by toDoViewModel.filter.observeAsState(ToDoFilter())

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Default.FilterList,
            contentDescription = "Filter",
            tint = MaterialTheme.colorScheme.secondary
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        // Section for Done Status Filter
        Text(
            text = "Done Status",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 4.dp)
        )
        DropdownMenuItem(
            text = { Text("All") },
            onClick = {
                toDoViewModel.setFilter(currentFilter.copy(doneStatus = DoneStatusFilter.ALL))
                expanded = false
            },
            trailingIcon = {
                if (currentFilter.doneStatus == DoneStatusFilter.ALL) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
        DropdownMenuItem(
            text = { Text("Done") },
            onClick = {
                toDoViewModel.setFilter(currentFilter.copy(doneStatus = DoneStatusFilter.DONE))
                expanded = false
            },
            trailingIcon = {
                if (currentFilter.doneStatus == DoneStatusFilter.DONE) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
        DropdownMenuItem(
            text = { Text("Not Done") },
            onClick = {
                toDoViewModel.setFilter(currentFilter.copy(doneStatus = DoneStatusFilter.NOT_DONE))
                expanded = false
            },
            trailingIcon = {
                if (currentFilter.doneStatus == DoneStatusFilter.NOT_DONE) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )

        // Divider between filter sections
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // Section for Created Date Filter
        Text(
            text = "Created Date",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 4.dp)
        )
        DropdownMenuItem(
            text = { Text("All") },
            onClick = {
                toDoViewModel.setFilter(currentFilter.copy(createdDateFilter = CreatedDateFilter.ALL))
                expanded = false
            },
            trailingIcon = {
                if (currentFilter.createdDateFilter == CreatedDateFilter.ALL) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
        DropdownMenuItem(
            text = { Text("Today") },
            onClick = {
                toDoViewModel.setFilter(currentFilter.copy(createdDateFilter = CreatedDateFilter.TODAY))
                expanded = false
            },
            trailingIcon = {
                if (currentFilter.createdDateFilter == CreatedDateFilter.TODAY) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
        DropdownMenuItem(
            text = { Text("This Week") },
            onClick = {
                toDoViewModel.setFilter(currentFilter.copy(createdDateFilter = CreatedDateFilter.THIS_WEEK))
                expanded = false
            },
            trailingIcon = {
                if (currentFilter.createdDateFilter == CreatedDateFilter.THIS_WEEK) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
        DropdownMenuItem(
            text = { Text("This Month") },
            onClick = {
                toDoViewModel.setFilter(currentFilter.copy(createdDateFilter = CreatedDateFilter.THIS_MONTH))
                expanded = false
            },
            trailingIcon = {
                if (currentFilter.createdDateFilter == CreatedDateFilter.THIS_MONTH) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
    }
}
