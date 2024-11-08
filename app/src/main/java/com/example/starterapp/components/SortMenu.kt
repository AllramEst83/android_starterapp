package com.example.starterapp.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.starterapp.models.SortOrder
import com.example.starterapp.viewModels.ToDoViewModel

@Composable
fun SortMenu(toDoViewModel: ToDoViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val sortOrder by toDoViewModel.sortOrder.observeAsState(SortOrder.CREATED_AT_DESCENDING)

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons. AutoMirrored.Filled.Sort,
            contentDescription = "Sort",
            tint = MaterialTheme.colorScheme.secondary
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text("Created At (Newest First)") },
            onClick = {
                toDoViewModel.setSortOrder(SortOrder.CREATED_AT_DESCENDING)
                expanded = false
            },
            trailingIcon = {
                if (sortOrder == SortOrder.CREATED_AT_DESCENDING) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
        DropdownMenuItem(
            text = { Text("Created At (Oldest First)") },
            onClick = {
                toDoViewModel.setSortOrder(SortOrder.CREATED_AT_ASCENDING)
                expanded = false
            },
            trailingIcon = {
                if (sortOrder == SortOrder.CREATED_AT_ASCENDING) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
        DropdownMenuItem(
            text = { Text("Title (A-Z)") },
            onClick = {
                toDoViewModel.setSortOrder(SortOrder.TITLE_ASCENDING)
                expanded = false
            },
            trailingIcon = {
                if (sortOrder == SortOrder.TITLE_ASCENDING) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
        DropdownMenuItem(
            text = { Text("Title (Z-A)") },
            onClick = {
                toDoViewModel.setSortOrder(SortOrder.TITLE_DESCENDING)
                expanded = false
            },
            trailingIcon = {
                if (sortOrder == SortOrder.TITLE_DESCENDING) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
        // Add more DropdownMenuItems here if you implement additional sort options
    }
}