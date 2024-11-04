package com.example.starterapp.pages

import android.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.starterapp.models.ThemeOption
import com.example.starterapp.ui.theme.ThemeData
import com.example.starterapp.viewModels.ThemeViewModel

@Composable
fun ThemeSettingsScreen(themeViewModel: ThemeViewModel, onBack: () -> Unit) {
    val themeMode = themeViewModel.themeMode

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Select Theme")

        ThemeData.themeOptions.forEach { themeOption ->
            ThemeItem(
                currentTheme = themeMode,
                themeOption = themeOption,
                onThemeSelected = { selectedTheme ->
                    themeViewModel.setTheme(selectedTheme)
                }
            )
        }

        // Back button for navigation
        Button(onClick = onBack, modifier = Modifier.padding(10.dp)) {
            Text("Back")
        }
    }
}


@Composable
fun ThemeItem(
    currentTheme: ThemeViewModel.ThemeMode,
    themeOption: ThemeOption,
    onThemeSelected: (ThemeViewModel.ThemeMode) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = currentTheme == themeOption.mode,
            onClick = { onThemeSelected(themeOption.mode) }
        )
        Text(stringResource(id = themeOption.displayNameRes), modifier = Modifier.padding(start = 8.dp))
    }
}


