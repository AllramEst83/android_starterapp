package com.example.starterapp.ui.theme

import com.example.starterapp.models.ThemeOption
import com.example.starterapp.viewModels.ThemeViewModel
import com.example.starterapp.R

object ThemeData {
    val themeOptions = listOf(
        ThemeOption(ThemeViewModel.ThemeMode.LIGHT, R.string.light_theme),
        ThemeOption(ThemeViewModel.ThemeMode.HIGHCONTRASTLIGHT, R.string.high_contrast_light_theme),
        ThemeOption(ThemeViewModel.ThemeMode.DARK, R.string.dark_theme),
        ThemeOption(ThemeViewModel.ThemeMode.HIGHCONTRASTDARK, R.string.high_contrast_dark_theme),
        ThemeOption(ThemeViewModel.ThemeMode.SYSTEM_DEFAULT, R.string.system_default)
    )
}