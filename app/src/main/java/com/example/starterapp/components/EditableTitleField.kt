package com.example.starterapp.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditableTitleField(
    editModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    title: String,
    onTitleChange: (String) -> Unit,
    isEditing: Boolean
){
    if (isEditing) {
        // Edit tile with a TextField
        TextField(
            value = title,
            onValueChange = onTitleChange,
            modifier = editModifier,
            placeholder = { Text("Edit title") }
        )
        Spacer(modifier = Modifier.height(8.dp))
    } else {
        // Title text
        Text(
            text = title,
            fontSize = 22.sp,
            modifier = textModifier,
        )
    }
}