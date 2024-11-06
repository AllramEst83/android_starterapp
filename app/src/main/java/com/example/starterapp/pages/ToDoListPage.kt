package com.example.starterapp.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.starterapp.R
import com.example.starterapp.db.todo.ToDo
import com.example.starterapp.ui.theme.ToDoAppTheme
import com.example.starterapp.utils.blendWithWhite
import com.example.starterapp.utils.contrastColor
import com.example.starterapp.viewModels.ThemeViewModel
import com.example.starterapp.viewModels.ToDoViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.Save
import androidx.compose.ui.draw.rotate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListPage(
    viewModel: ToDoViewModel,
    themeViewModel: ThemeViewModel,
    onNavigateToSettings: () -> Unit) {

    ToDoAppTheme(themeViewModel) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            gesturesEnabled = false,
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(
                    onNavigateToSettings = {
                        scope.launch { drawerState.close() }
                        onNavigateToSettings()
                    },
                    onCloseDrawer = {
                        scope.launch { drawerState.close() }
                    }
                )
            }
        ) {
            Scaffold(
                topBar = {
                    Column {
                        TopAppBar(
                            title = { Text(text = "To-Do List") },
                            navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch { drawerState.open() }
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_menu),
                                        contentDescription = "Menu"
                                    )
                                }
                            }
                        )
                        // Divider under TopAppBar spanning the entire width
                        //HorizontalDivider(
                          //  color = Color.Gray,
                            //thickness = 1.dp)
                    }
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    // Content of the To-Do List Page
                    Content(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun DrawerContent(onNavigateToSettings: () -> Unit, onCloseDrawer: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.blendWithWhite(0.3f, 0.1f),
                        MaterialTheme.colorScheme.primary.blendWithWhite(0.3f, 0.1f),
                        MaterialTheme.colorScheme.secondary.blendWithWhite(0.3f, 0.1f),
                        MaterialTheme.colorScheme.secondary.blendWithWhite(0.3f, 0.1f),
                        MaterialTheme.colorScheme.tertiary.blendWithWhite(0.3f, 0.1f),
                        MaterialTheme.colorScheme.tertiary.blendWithWhite(0.3f, 0.1f)
                    ),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Menu",
                    color = MaterialTheme.colorScheme.primary.contrastColor(),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                IconButton(onClick = onCloseDrawer) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Drawer",
                        tint = MaterialTheme.colorScheme.primary.contrastColor()
                    )
                }
            }
            HorizontalDivider() // Divider in the drawer content
            Spacer(modifier = Modifier.height(8.dp))

            // Navigation item for Theme Settings
            NavigationDrawerItem(
                label = { Text("Theme Settings") },
                selected = false,
                onClick = onNavigateToSettings
            )
            // Add more navigation items here if needed
        }
    }
}


@Composable
fun Content(viewModel: ToDoViewModel) {
    val toDoList by viewModel.toDoList.observeAsState(emptyList())
    var inputText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp),
            verticalArrangement = Arrangement.SpaceBetween // Add this line
        ) {
            Box(
                modifier = Modifier
                    .weight(1f, fill = true) // Add weight to push the input Row to the bottom
                    .fillMaxWidth()
            ) {
                if (toDoList.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(1.dp)
                    ) {
                        itemsIndexed(toDoList) { _, item ->
                            ToDoItemVersion2(item,
                                onDelete = {
                                viewModel.deleteToDo(item.id)
                                },
                                onUpdate = { updatedItem ->
                                    viewModel.updateToDo(updatedItem)
                                },
                                Modifier.padding(10.dp)
                            )
                        }
                    }
                } else {
                    ShowEmptyMessage()
                }
            }

            // Input Row at the bottom of the Column
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text("Add todo") },
                    shape = RoundedCornerShape(15.dp),
                )
                Button(
                    onClick = {
                        viewModel.addToDo(inputText.trim())
                        inputText = ""
                    },
                    modifier = Modifier
                        .padding(start = 2.dp, end = 2.dp, top = 9.dp, bottom = 2.dp),
                    enabled = inputText.isNotEmpty()
                ) {
                    Text("Add")
                }
            }
        }
    }
}

@Composable
fun ToDoItemVersion2(
    item: ToDo,
    onDelete: () -> Unit,
    onUpdate: (ToDo) -> Unit,
    modifier: Modifier = Modifier
) {
    val formattedDate = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).format(item.createdAt)
    var isEditing by remember { mutableStateOf(false) }
    var editableToDo by remember { mutableStateOf(item) }
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 0f else 180f,
        label = "ExpandCollapseRotation"
    )

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box { // Use a Box here to allow TopEnd alignment for IconButton
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
                        modifier = Modifier.weight(1f) // Takes up available space in Row
                    ) {
                        // Date
                        Text(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                                    shape = CircleShape
                                )
                                .padding(horizontal = 6.dp, vertical = 1.dp),
                            text = formattedDate,
                            fontSize = 11.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        if (isEditing) {
                            // Edit tile with a TextField
                            TextField(
                                value = editableToDo.title,
                                onValueChange = { editableToDo = editableToDo.copy(title = it) },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Edit title") }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        } else {
                            // Title text
                            Text(
                                text = item.title,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp),
                            )
                        }
                        if (expanded) {

                            Spacer(modifier = Modifier.height(4.dp))

                            if (isEditing) {
                                // Editable content with a TextField
                                TextField(
                                    value = editableToDo.content ?: "",
                                    onValueChange = { editableToDo = editableToDo.copy(content = it) },
                                    modifier = Modifier.fillMaxWidth(),
                                    placeholder = { Text("Edit content") }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            } else {
                                Spacer(modifier = Modifier.height(4.dp))
                                // Content text
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp),
                                    text = item.content ?: "",
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            // Button row
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                // Delete button
                                IconButton(
                                    onClick = onDelete,
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), // Slightly darker background
                                            shape = CircleShape
                                        )
                                        .padding(2.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete"
                                    )
                                }

                                // Edit button
                                IconButton(
                                    onClick = {
                                        if (isEditing) {
                                            val updatedItem = item.copy(title = editableToDo.title, content = editableToDo.content)
                                            onUpdate(updatedItem)
                                            isEditing = false
                                        } else {
                                            isEditing = true
                                        }
                                    },
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), // Slightly darker background
                                            shape = CircleShape
                                        )
                                        .padding(2.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isEditing) Icons.Default.Save else Icons.Default.Edit,
                                        contentDescription = if (isEditing) "Save" else "Edit"
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Chevron icon to expand/collapse
            IconButton(
                onClick = { if (!isEditing) expanded = !expanded },
                enabled = !isEditing,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            shape = CircleShape
                        )
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ExpandLess,
                        contentDescription = if (expanded) "Collapse" else "Expand",
                        modifier = Modifier.rotate(rotation)
                    )
                }
            }
        }
    }
}


@Composable
fun ToDoItem(item: ToDo, onDelete: () -> Unit, onUpdate: (ToDo) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var editableToDo by remember { mutableStateOf(item) }
    val formattedDate = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).format(item.createdAt)

    val rotation by animateFloatAsState(
        targetValue = if (expanded) 0f else 180f,
        label = "ExpandCollapseRotation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            shape = CircleShape)
                        .padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 2.dp),

                    text = formattedDate,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))

                if (isEditing) {
                    TextField(
                        value = editableToDo.title,
                        onValueChange = { editableToDo = editableToDo.copy(title = it) },
                        modifier = Modifier.fillMaxWidth(), // Ensuring TextField takes full width
                        placeholder = { Text("Edit title") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                } else {
                    Text(
                        text = item.title,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)// Ensuring text takes full width
                    )
                }

                AnimatedVisibility(visible = expanded) {
                    Column {
                        Spacer(modifier = Modifier.height(8.dp))

                        if (isEditing) {
                            // Editable content with a TextField
                            TextField(
                                value = editableToDo.content ?: "",
                                onValueChange = { editableToDo = editableToDo.copy(content = it) },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Edit content") }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        } else {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp),
                                text = item.content ?: "",
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(
                                onClick = onDelete,
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), // Slightly darker background
                                        shape = CircleShape
                                    )
                                    .padding(2.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete"
                                )
                            }
                            IconButton(
                                onClick = {
                                    if (isEditing) {
                                        val updatedItem = item.copy(title = editableToDo.title, content = editableToDo.content)
                                        onUpdate(updatedItem)
                                        isEditing = false
                                    } else {
                                        isEditing = true
                                    }
                                },
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), // Slightly darker background
                                        shape = CircleShape
                                    )
                                    .padding(2.dp)
                            ) {
                                Icon(
                                    imageVector = if (isEditing) Icons.Default.Save else Icons.Default.Edit,
                                    contentDescription = if (isEditing) "Save" else "Edit"
                                )
                            }
                        }
                    }
                }
            }

            // Chevron icon in the top right corner
            IconButton(
                onClick = { if (!isEditing) expanded = !expanded },
                enabled = !isEditing,
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), // Slightly darker background
                            shape = CircleShape // Rounded background around the icon
                        )
                        .padding(4.dp) // Padding to create space around the icon within the rounded background
                ) {
                    Icon(
                        imageVector = Icons.Filled.ExpandLess,
                        contentDescription = if (expanded) "Collapse" else "Expand",
                        modifier = Modifier.rotate(rotation)
                    )
                }
            }
        }
    }
}