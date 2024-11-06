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
 fun EditableContentField(
    editModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    content: String,
    onContentChange: (String) -> Unit,
    isEditing: Boolean
 ){
    if (isEditing) {
        // Editable content with a TextField
        TextField(
            value = content ?: "",
            onValueChange = onContentChange,
            modifier = editModifier,
            placeholder = { Text("Edit content") }
        )
        Spacer(modifier = Modifier.height(8.dp))
    } else {
        Spacer(modifier = Modifier.height(4.dp))
        // Content text
        Text(
            modifier = textModifier,
            text = content ?: "",
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
 }