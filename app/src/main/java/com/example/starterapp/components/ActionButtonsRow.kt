package com.example.starterapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ActionButtonsRow(
    isEditing: Boolean,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    rowModifier: Modifier = Modifier
) {
    Row(
        modifier = rowModifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Delete button
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    shape = CircleShape
                )
                .padding(2.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                modifier = Modifier.size(22.dp)
            )
        }

        // Edit button
        IconButton(
            onClick = onEditClick,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), // Slightly darker background
                    shape = CircleShape
                )
                .padding(2.dp)
        ) {
            Icon(
                imageVector = if (isEditing) Icons.Default.Save else Icons.Default.Edit,
                contentDescription = if (isEditing) "Save" else "Edit",
                modifier = Modifier.size(22.dp)
            )
        }
    }
}