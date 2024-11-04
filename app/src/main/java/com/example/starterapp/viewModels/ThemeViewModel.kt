package com.example.starterapp.viewModels

import android.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ThemeViewModel : ViewModel() {
    enum class ThemeMode { LIGHT, HIGHCONTRASTLIGHT, DARK, HIGHCONTRASTDARK, SYSTEM_DEFAULT }

    var themeMode by mutableStateOf(ThemeMode.SYSTEM_DEFAULT)
        private set

    fun setTheme(themeMode: ThemeMode) {
        this.themeMode = themeMode
    }
}
