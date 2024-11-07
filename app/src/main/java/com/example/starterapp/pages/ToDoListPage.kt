package com.example.starterapp.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import com.example.starterapp.components.ToDoItemComposable
import com.example.starterapp.ui.theme.ToDoAppTheme
import com.example.starterapp.utils.blendWithWhite
import com.example.starterapp.utils.contrastColor
import com.example.starterapp.viewModels.ThemeViewModel
import com.example.starterapp.viewModels.ToDoViewModel
import kotlinx.coroutines.launch

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
                            title = { Text(text = "Notes") },
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
                            ToDoItemComposable(
                                item,
                                onDelete = {
                                viewModel.deleteToDo(item.id)
                                },
                                onUpdate = { updatedItem ->
                                    viewModel.updateToDo(updatedItem)
                                },
                                onDoneUpdate = { updatedDone ->
                                    viewModel.updateToDoDone(item.id, updatedDone)
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
                    label = { Text("Add note") },
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