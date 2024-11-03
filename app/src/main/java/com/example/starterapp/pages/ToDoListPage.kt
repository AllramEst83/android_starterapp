package com.example.starterapp.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.starterapp.R
import com.example.starterapp.viewModels.ToDoViewModel
import com.example.starterapp.db.ToDo
import com.example.starterapp.ui.theme.ToDoAppTheme
import com.example.starterapp.viewModels.ThemeViewModel

@Composable
fun ToDoListPage(viewModel: ToDoViewModel, themeViewModel : ThemeViewModel, onNavigateToSettings: () -> Unit){
    ToDoAppTheme(themeViewModel){
        val toDoList = viewModel.toDoList.observeAsState()
        var inputText by remember {
            mutableStateOf("")
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 80.dp) // Adjust if needed for input field spacing
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.todo_page_name),
                        fontSize = 22.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 15.dp, end = 5.dp)
                    )
                    Button(
                        onClick = onNavigateToSettings,
                        modifier = Modifier
                            .padding(start = 5.dp, end = 15.dp)
                    ) {
                        Text("Go to Theme Settings")
                    }
                }
                toDoList.value?.let { list ->
                    if(list.isNotEmpty()){
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

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp),
                    value = inputText,
                    onValueChange = {
                        inputText = it
                    },
                )
                Button(
                    onClick = {
                        if(inputText.isNotEmpty()){
                            viewModel.addToDo(inputText)
                            inputText = ""
                        }
                    },
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text("Add")
                }
            }
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
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (
                modifier = Modifier.weight(1f)
            ){
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
fun ShowEmptyMessage(){
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = "Oops! no items here.",
        fontSize = 16.sp
    )
}