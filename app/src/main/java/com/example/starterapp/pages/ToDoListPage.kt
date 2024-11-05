import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
import androidx.compose.material.icons.filled.ExpandMore
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
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(
                    onNavigateToSettings = {
                        scope.launch { drawerState.close() }
                        onNavigateToSettings()
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
fun DrawerContent(onNavigateToSettings: () -> Unit) {
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
            Text(
                text = "Menu",
                color = MaterialTheme.colorScheme.primary.contrastColor(),
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
            HorizontalDivider() // Divider in the drawer content
            Spacer(modifier = Modifier.
            height(8.dp)
            )

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
                            ToDoItem(item,
                                onDelete = {
                                viewModel.deleteToDo(item.id)
                            },
                                onUpdate = { updatedItem ->
                                    viewModel.updateToDo(updatedItem)
                                }
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
fun ToDoItem(item: ToDo, onDelete: () -> Unit, onUpdate: (ToDo) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var editableContent by remember { mutableStateOf(item.content ?: "") }
    val formattedDate = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).format(item.createdAt)

    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "ExpandCollapseRotation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = formattedDate,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.title,
                        fontSize = 22.sp
                    )
                }
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Filled.ExpandLess,
                        contentDescription = if (expanded) "Collapse" else "Expand",
                        modifier = Modifier.rotate(rotation) // Apply rotation
                    )
                }
            }
            // Use AnimatedVisibility for smooth expand/collapse animations
            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))

                    if (isEditing) {
                        // Editable content with a TextField
                        TextField(
                            value = editableContent,
                            onValueChange = { editableContent = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Edit content") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    } else {
                        // Display content as text
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.onSurface,
                            thickness = 1.dp
                        )
                        Text(
                            modifier = Modifier.padding(top = 10.dp),
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
                        // Delete icon on the bottom left
                        IconButton(onClick = onDelete) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(id = R.drawable.delete_icon),
                                contentDescription = "Delete"
                            )
                        }

                        // Edit or Save icon on the bottom right
                        IconButton(onClick = {
                            if (isEditing) {
                                // Save changes
                                val updatedItem = item.copy(content = editableContent)
                                onUpdate(updatedItem)
                                isEditing = false // Exit editing mode
                            } else {
                                // Enter editing mode
                                isEditing = true
                            }
                        }) {
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
}


@Composable
fun ShowEmptyMessage() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = "Oops! no items here.",
        fontSize = 16.sp
    )
}
