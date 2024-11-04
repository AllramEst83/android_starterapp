package com.example.starterapp.ui.theme

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.starterapp.viewModels.ThemeViewModel
import com.example.starterapp.viewModels.ThemeViewModel.ThemeMode

@Composable
fun ToDoAppTheme(
    themeViewModel: ThemeViewModel,
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Take theme from phone settings
    content: @Composable () -> Unit
) {
    val themeMode = themeViewModel.themeMode

    val colorScheme = when (themeMode) {
        ThemeMode.RED_LIGHT -> lightSchemeRed
        ThemeMode.RED_DARK -> darkSchemeRed
        ThemeMode.GREEN_LIGHT -> lightSchemeGreen
        ThemeMode.GREEN_DARK -> darkSchemeGreen
        ThemeMode.SYSTEM_DEFAULT_DARK -> if (isSystemInDarkTheme()) darkSchemeDefaultDark else lightSchemeDefaultLight
        else -> lightSchemeDefaultLight
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        val navigationBarColor = when {
            Build.VERSION.SDK_INT >= 29 -> colorScheme.background.toArgb()
            Build.VERSION.SDK_INT >= 26 -> colorScheme.background.toArgb()
            else -> Color(0x00, 0x00, 0x00, 0x50).toArgb()
        }

        SideEffect {
            setUpEdgeToEdge(view, darkTheme, navigationBarColor)
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


private fun setUpEdgeToEdge(view: View, darkTheme: Boolean,navigationBarColor: Int) {
    val window = (view.context as Activity).window
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.navigationBarColor = navigationBarColor
    window.statusBarColor = navigationBarColor
    val controller = WindowCompat.getInsetsController(window, view)
    controller.isAppearanceLightStatusBars = !darkTheme
    controller.isAppearanceLightNavigationBars = !darkTheme
}