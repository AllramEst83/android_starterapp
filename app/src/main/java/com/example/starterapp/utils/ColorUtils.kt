package com.example.starterapp.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

// Extension function on the Color class
fun Color.contrastColor(): Color {
    return if (this.luminance() > 0.5f) Color.Black else Color.White
}