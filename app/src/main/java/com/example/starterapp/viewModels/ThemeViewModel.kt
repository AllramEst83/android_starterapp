package com.example.starterapp.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ThemeViewModel : ViewModel() {
    // Enum for different theme modes
    enum class ThemeMode { LIGHT, HIGHCONTRASTLIGHT, DARK, HIGHCONTRASTDARK, SYSTEM_DEFAULT }

    // Use mutableStateOf directly
    var themeMode by mutableStateOf(ThemeMode.SYSTEM_DEFAULT)
        private set

    // Function to update the theme mode
    fun setTheme(themeMode: ThemeMode) {
        this.themeMode = themeMode
    }
}