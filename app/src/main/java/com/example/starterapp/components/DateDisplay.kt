package com.example.starterapp.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun DisplayDate(
    modifier: Modifier = Modifier,
    formattedDate: String,
    fontSize: TextUnit = 11.sp){
    Text(
        modifier = modifier,
        text = formattedDate,
        fontSize = fontSize
    )
}