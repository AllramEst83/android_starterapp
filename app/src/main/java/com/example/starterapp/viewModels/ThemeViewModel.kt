package com.example.starterapp.viewModels

import android.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ThemeViewModel : ViewModel() {
    enum class ThemeMode { RED_LIGHT,RED_DARK,GREEN_LIGHT, GREEN_DARK,SYSTEM_DEFAULT_LIGHT,SYSTEM_DEFAULT_DARK }

    var themeMode by mutableStateOf(ThemeMode.SYSTEM_DEFAULT_LIGHT)
        private set

    fun setTheme(themeMode: ThemeMode) {
        this.themeMode = themeMode
    }
}
