package com.example.starterapp.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.starterapp.db.todo.ToDo
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ToDoItemComposable(
    item: ToDo,
    onDelete: () -> Unit,
    onUpdate: (ToDo) -> Unit,
    onDoneUpdate: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val formattedDate = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).format(item.createdAt)
    var isEditing by remember { mutableStateOf(false) }
    var title by remember(item.id) { mutableStateOf(item.title) }
    var content by remember(item.id) { mutableStateOf(item.content ?: "") }
    var isChecked by remember(item.id) { mutableStateOf(item.done) }
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 0f else 180f,
        label = "ExpandCollapseRotation"
    )

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box {
            Column(
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RoundCheckbox(
                                isChecked = isChecked,
                                onCheckedChange = { newCheckedState ->
                                    isChecked = newCheckedState
                                    onDoneUpdate(isChecked)
                                },
                                modifier = Modifier


                            )
                            DisplayDate(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                                        shape = CircleShape
                                    )
                                    .padding(horizontal = 8.dp, vertical = 1.dp)
                                    .alpha(if (isChecked) 0.5f else 1f),
                                formattedDate = formattedDate,
                                isChecked = isChecked
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        EditableTitleField(
                            editModifier = Modifier.fillMaxWidth()

                                .alpha(if (isChecked) 0.5f else 1f),
                            textModifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp)

                                .alpha(if (isChecked) 0.5f else 1f),
                            title = title,
                            onTitleChange = { title = it },
                            isEditing = isEditing,
                            isChecked = isChecked

                        )

                        if (expanded) {

                            Spacer(modifier = Modifier.height(4.dp))

                            EditableContentField(
                                editModifier = Modifier.fillMaxWidth()
                                    .alpha(if (isChecked) 0.5f else 1f),
                                textModifier = Modifier
                                    .alpha(if (isChecked) 0.5f else 1f)
                                    .fillMaxWidth()
                                    .padding(start = 8.dp),
                                content = content,
                                onContentChange = { content = it },
                                isEditing = isEditing,
                                isChecked = isChecked
                            )

                            // Button row
                            ActionButtonsRow(
                                isEditing = isEditing,
                                onDeleteClick = onDelete,
                                onEditClick = {
                                    if (isEditing) {
                                        val updatedItem = item.copy(title = title, content = content,done = isChecked)
                                        onUpdate(updatedItem)
                                        isEditing = false
                                    } else {
                                        isEditing = true
                                    }
                                },
                                rowModifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                                    .alpha(if (isChecked) 0.5f else 1f)
                            )
                        }
                    }
                }
            }

            ExpandCollapseButton(
                expanded = expanded,
                onClick = { expanded = !expanded },
                rotation = rotation,
                iconModifier = Modifier.align(Alignment.TopEnd),
                boxModifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                        shape = CircleShape)
                    .padding(4.dp)
                    .alpha(if (isChecked) 0.5f else 1f)
            )
        }
    }
}