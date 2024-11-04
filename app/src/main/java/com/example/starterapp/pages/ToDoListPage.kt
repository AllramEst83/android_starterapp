import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.starterapp.R
import com.example.starterapp.viewModels.ThemeViewModel
import com.example.starterapp.viewModels.ToDoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListPage(viewModel: ToDoViewModel, themeViewModel: ThemeViewModel, onNavigateToSettings: () -> Unit) {
    ToDoAppTheme(themeViewModel) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(onNavigateToSettings = {
                    scope.launch { drawerState.close() }
                    onNavigateToSettings()
                })
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "To-Do List") },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch { drawerState.open() }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_menu), // Use a hamburger menu icon
                                    contentDescription = "Menu"
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    // Content of the To-Do List Page
                    Content(viewModel = viewModel, themeViewModel = themeViewModel)
                }
            }
        }
    }
}

@Composable
fun DrawerContent(onNavigateToSettings: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Navigation", fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))
        Divider()
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

@Composable
fun Content(viewModel: ToDoViewModel, themeViewModel: ThemeViewModel) {
    val toDoList = viewModel.toDoList.observeAsState()
    var inputText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Rest of the ToDoListPage content goes here

        toDoList.value?.let { list ->
            if (list.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f)
                ) {
                    itemsIndexed(list) { index: Int, item: ToDo ->
                        ToDoItem(item, onDelete = {
                            viewModel.deleteToDo(item.id)
                        })
                    }
                }
            } else {
                ShowEmptyMessage()
            }
        } ?: ShowEmptyMessage()
    }
}
