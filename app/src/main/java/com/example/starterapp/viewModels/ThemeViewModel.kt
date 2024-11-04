package com.example.starterapp.viewModels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.starterapp.managers.ThemeDataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    enum class ThemeMode { RED_LIGHT, RED_DARK, GREEN_LIGHT, GREEN_DARK, SYSTEM_DEFAULT_LIGHT, SYSTEM_DEFAULT_DARK }

    private val themeDataStoreManager = ThemeDataStoreManager(application)
    var themeMode by mutableStateOf(ThemeMode.SYSTEM_DEFAULT_LIGHT)
        private set

    init {
        loadThemeMode()
    }

    private fun loadThemeMode() {
        viewModelScope.launch {
            themeDataStoreManager.themeMode.collect { savedThemeMode ->
                themeMode = savedThemeMode // Update the UI when the theme mode changes
            }
        }
    }

    fun setTheme(themeMode: ThemeMode) {
        this.themeMode = themeMode
        viewModelScope.launch(Dispatchers.IO) {
            themeDataStoreManager.saveThemeMode(themeMode)
        }
    }
}
