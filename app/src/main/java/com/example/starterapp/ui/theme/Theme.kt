package com.example.starterapp.ui.theme

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.starterapp.viewModels.ThemeViewModel
import com.example.starterapp.viewModels.ThemeViewModel.ThemeMode

private val lightSchemeRed = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkSchemeRed = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val lightSchemeGreen = lightColorScheme(
    primary = primaryLightrgreen,
    onPrimary = onPrimaryLightgreen,
    primaryContainer = primaryContainerLightgreen,
    onPrimaryContainer = onPrimaryContainerLightgreen,
    secondary = secondaryLightgreen,
    onSecondary = onSecondaryLightgreen,
    secondaryContainer = secondaryContainerLightgreen,
    onSecondaryContainer = onSecondaryContainerLightgreen,
    tertiary = tertiaryLightgreen,
    onTertiary = onTertiaryLightgreen,
    tertiaryContainer = tertiaryContainerLightgreen,
    onTertiaryContainer = onTertiaryContainerLightgreen,
    error = errorLightgreen,
    onError = onErrorLightgreen,
    errorContainer = errorContainerLightgreen,
    onErrorContainer = onErrorContainerLightgreen,
    background = backgroundLightgreen,
    onBackground = onBackgroundLightgreen,
    surface = surfaceLightgreen,
    onSurface = onSurfaceLightgreen,
    surfaceVariant = surfaceVariantLightgreen,
    onSurfaceVariant = onSurfaceVariantLightgreen,
    outline = outlineLightgreen,
    outlineVariant = outlineVariantLightgreen,
    scrim = scrimLightgreen,
    inverseSurface = inverseSurfaceLightgreen,
    inverseOnSurface = inverseOnSurfaceLightgreen,
    inversePrimary = inversePrimaryLightgreen,
    surfaceDim = surfaceDimLightgreen,
    surfaceBright = surfaceBrightLightgreen,
    surfaceContainerLowest = surfaceContainerLowestLightgreen,
    surfaceContainerLow = surfaceContainerLowLightgreen,
    surfaceContainer = surfaceContainerLightgreen,
    surfaceContainerHigh = surfaceContainerHighLightgreen,
    surfaceContainerHighest = surfaceContainerHighestLightgreen
)

private val darkSchemeGreen = darkColorScheme(
    primary = primaryDarkGreen,
    onPrimary = onPrimaryDarkGreen,
    primaryContainer = primaryContainerDarkGreen,
    onPrimaryContainer = onPrimaryContainerDarkGreen,
    secondary = secondaryDarkGreen,
    onSecondary = onSecondaryDarkGreen,
    secondaryContainer = secondaryContainerDarkGreen,
    onSecondaryContainer = onSecondaryContainerDarkGreen,
    tertiary = tertiaryDarkGreen,
    onTertiary = onTertiaryDarkGreen,
    tertiaryContainer = tertiaryContainerDarkGreen,
    onTertiaryContainer = onTertiaryContainerDarkGreen,
    error = errorDarkGreen,
    onError = onErrorDarkGreen,
    errorContainer = errorContainerDarkGreen,
    onErrorContainer = onErrorContainerDarkGreen,
    background = backgroundDarkGreen,
    onBackground = onBackgroundDarkGreen,
    surface = surfaceDarkGreen,
    onSurface = onSurfaceDarkGreen,
    surfaceVariant = surfaceVariantDarkGreen,
    onSurfaceVariant = onSurfaceVariantDarkGreen,
    outline = outlineDarkGreen,
    outlineVariant = outlineVariantDarkGreen,
    scrim = scrimDarkGreen,
    inverseSurface = inverseSurfaceDarkGreen,
    inverseOnSurface = inverseOnSurfaceDarkGreen,
    inversePrimary = inversePrimaryDarkGreen,
    surfaceDim = surfaceDimDarkGreen,
    surfaceBright = surfaceBrightDarkGreen,
    surfaceContainerLowest = surfaceContainerLowestDarkGreen,
    surfaceContainerLow = surfaceContainerLowDarkGreen,
    surfaceContainer = surfaceContainerDarkGreen,
    surfaceContainerHigh = surfaceContainerHighDarkGreen,
    surfaceContainerHighest = surfaceContainerHighestDarkGreen
)

private val lightSchemeDefaultLight = lightColorScheme(
    primary = primaryLightDefault,
    onPrimary = onPrimaryLightDefault,
    primaryContainer = primaryContainerLightDefault,
    onPrimaryContainer = onPrimaryContainerLightDefault,
    secondary = secondaryLightDefault,
    onSecondary = onSecondaryLightDefault,
    secondaryContainer = secondaryContainerLightDefault,
    onSecondaryContainer = onSecondaryContainerLightDefault,
    tertiary = tertiaryLightDefault,
    onTertiary = onTertiaryLightDefault,
    tertiaryContainer = tertiaryContainerLightDefault,
    onTertiaryContainer = onTertiaryContainerLightDefault,
    error = errorLightDefault,
    onError = onErrorLightDefault,
    errorContainer = errorContainerLightDefault,
    onErrorContainer = onErrorContainerLightDefault,
    background = backgroundLightDefault,
    onBackground = onBackgroundLightDefault,
    surface = surfaceLightDefault,
    onSurface = onSurfaceLightDefault,
    surfaceVariant = surfaceVariantLightDefault,
    onSurfaceVariant = onSurfaceVariantLightDefault,
    outline = outlineLightDefault,
    outlineVariant = outlineVariantLightDefault,
    scrim = scrimLightDefault,
    inverseSurface = inverseSurfaceLightDefault,
    inverseOnSurface = inverseOnSurfaceLightDefault,
    inversePrimary = inversePrimaryLightDefault,
    surfaceDim = surfaceDimLightDefault,
    surfaceBright = surfaceBrightLightDefault,
    surfaceContainerLowest = surfaceContainerLowestLightDefault,
    surfaceContainerLow = surfaceContainerLowLightDefault,
    surfaceContainer = surfaceContainerLightDefault,
    surfaceContainerHigh = surfaceContainerHighLightDefault,
    surfaceContainerHighest = surfaceContainerHighestLightDefault

)

private val darkSchemeDefaultDark = darkColorScheme(
    primary = primaryDarkDefault,
    onPrimary = onPrimaryDarkDefault,
    primaryContainer = primaryContainerDarkDefault,
    onPrimaryContainer = onPrimaryContainerDarkDefault,
    secondary = secondaryDarkDefault,
    onSecondary = onSecondaryDarkDefault,
    secondaryContainer = secondaryContainerDarkDefault,
    onSecondaryContainer = onSecondaryContainerDarkDefault,
    tertiary = tertiaryDarkDefault,
    onTertiary = onTertiaryDarkDefault,
    tertiaryContainer = tertiaryContainerDarkDefault,
    onTertiaryContainer = onTertiaryContainerDarkDefault,
    error = errorDarkDefault,
    onError = onErrorDarkDefault,
    errorContainer = errorContainerDarkDefault,
    onErrorContainer = onErrorContainerDarkDefault,
    background = backgroundDarkDefault,
    onBackground = onBackgroundDarkDefault,
    surface = surfaceDarkDefault,
    onSurface = onSurfaceDarkDefault,
    surfaceVariant = surfaceVariantDarkDefault,
    onSurfaceVariant = onSurfaceVariantDarkDefault,
    outline = outlineDarkDefault,
    outlineVariant = outlineVariantDarkDefault,
    scrim = scrimDarkDefault,
    inverseSurface = inverseSurfaceDarkDefault,
    inverseOnSurface = inverseOnSurfaceDarkDefault,
    inversePrimary = inversePrimaryDarkDefault,
    surfaceDim = surfaceDimDarkDefault,
    surfaceBright = surfaceBrightDarkDefault,
    surfaceContainerLowest = surfaceContainerLowestDarkDefault,
    surfaceContainerLow = surfaceContainerLowDarkDefault,
    surfaceContainer = surfaceContainerDarkDefault,
    surfaceContainerHigh = surfaceContainerHighDarkDefault,
    surfaceContainerHighest = surfaceContainerHighestDarkDefault

)

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