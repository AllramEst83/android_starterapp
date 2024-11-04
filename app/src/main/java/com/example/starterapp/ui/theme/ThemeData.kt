package com.example.starterapp.ui.theme

import com.example.starterapp.models.ThemeOption
import com.example.starterapp.viewModels.ThemeViewModel
import com.example.starterapp.R

object ThemeData {
    val themeOptions = listOf(
        ThemeOption(ThemeViewModel.ThemeMode.RED_LIGHT, R.string.red_light_theme),
        ThemeOption(ThemeViewModel.ThemeMode.RED_DARK, R.string.red_dark_theme),
        ThemeOption(ThemeViewModel.ThemeMode.GREEN_LIGHT, R.string.green_light_theme),
        ThemeOption(ThemeViewModel.ThemeMode.GREEN_DARK, R.string.green_dark_theme),
        ThemeOption(ThemeViewModel.ThemeMode.SYSTEM_DEFAULT_LIGHT, R.string.system_default_light),
        ThemeOption(ThemeViewModel.ThemeMode.SYSTEM_DEFAULT_DARK, R.string.system_default_dark)
    )
}