package com.example.starterapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import com.example.starterapp.ViewModels.ToDoViewModel
import com.example.starterapp.db.ToDo


@Composable
fun ToDoListPage(viewModel: ToDoViewModel){

    val toDoList = viewModel.toDoList.observeAsState()
    var inputText by remember {
        mutableStateOf("")
    }
    Column (
        modifier = Modifier
            .fillMaxHeight()
            .background(Color(4285839871))
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        )
        {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                value = inputText,
                onValueChange = {
                    inputText = it
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.White
                ),
            )
            Button(
                onClick = {
                    if(inputText.isNotEmpty()){
                        viewModel.addToDo(inputText)
                        inputText = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF8A5C2),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.CenterVertically)
            )
            {
                Text("Add")
            }
        }

        toDoList.value?.let { list ->
            if(list.isNotEmpty()){
                LazyColumn(
                    modifier = Modifier.padding(10.dp),
                    content = {
                        itemsIndexed(list) { index: Int, item: ToDo ->
                            ToDoItem(item, onDelete = {
                                viewModel.deleteToDo(item.id)
                            })
                        }
                    },
                )
            }else{
                ShowEmptyMessage()
            }
        } ?: ShowEmptyMessage()
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
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8A5C2)),
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
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.title,
                    fontSize = 22.sp,
                    color = Color.Black
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    modifier = Modifier.width(20.dp),
                    painter = painterResource(id = R.drawable.delete_icon),
                    contentDescription = "delete button",
                    tint = Color.Black
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
        fontSize = 16.sp,
        color = Color.Black
    )
}