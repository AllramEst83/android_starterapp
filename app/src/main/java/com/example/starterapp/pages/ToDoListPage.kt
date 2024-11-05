import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.starterapp.R
import com.example.starterapp.db.todo.ToDo
import com.example.starterapp.ui.theme.ToDoAppTheme
import com.example.starterapp.utils.contrastColor
import com.example.starterapp.viewModels.ThemeViewModel
import com.example.starterapp.viewModels.ToDoViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

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
                        HorizontalDivider(
                            color = Color.Gray,
                            thickness = 1.dp)
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
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.tertiary)
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
            // Content above the input
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
                        itemsIndexed(toDoList) { index, item ->
                            ToDoItem(item, onDelete = {
                                viewModel.deleteToDo(item.id)
                            })
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
fun AddButton(onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .height(TextFieldDefaults.MinHeight)
            .padding(4.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Add",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@Composable
fun ToDoItem(item: ToDo, onDelete: () -> Unit) {
    val formattedDate = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).format(item.createdAt)

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
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
            IconButton(onClick = onDelete) {
                Icon(
                    modifier = Modifier.width(20.dp),
                    painter = painterResource(id = R.drawable.delete_icon),
                    contentDescription = "delete button"
                )
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
