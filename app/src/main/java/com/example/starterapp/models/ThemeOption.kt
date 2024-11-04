package com.example.starterapp.models

import androidx.annotation.StringRes
import com.example.starterapp.viewModels.ThemeViewModel

data class ThemeOption(
    val mode: ThemeViewModel.ThemeMode,
    @StringRes val displayNameRes: Int
)