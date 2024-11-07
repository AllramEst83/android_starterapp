package com.example.starterapp.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.starterapp.components.ToDoList
import com.example.starterapp.viewModels.ToDoViewModel

@Composable
fun ToDoPage(toDoViewModel: ToDoViewModel) {
    val toDoList by toDoViewModel.toDoList.observeAsState(emptyList())
    var inputText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f, fill = true) // Add weight to push the input Row to the bottom
                    .fillMaxWidth()
            ) {
                ToDoList(
                    toDoList = toDoList,
                    toDoViewModel = toDoViewModel)
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
                        toDoViewModel.addToDo(inputText.trim())
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