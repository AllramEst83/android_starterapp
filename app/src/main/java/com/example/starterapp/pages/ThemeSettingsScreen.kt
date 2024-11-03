package com.example.starterapp.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.starterapp.viewModels.ThemeViewModel

@Composable
fun ThemeSettingsScreen(themeViewModel: ThemeViewModel, onBack: () -> Unit) {
    val themeMode = themeViewModel.themeMode

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Select Theme")

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = themeMode == ThemeViewModel.ThemeMode.LIGHT,
                onClick = { themeViewModel.setTheme(ThemeViewModel.ThemeMode.LIGHT) }
            )
            Text("Light Theme", modifier = Modifier.padding(start = 8.dp))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = themeMode == ThemeViewModel.ThemeMode.HIGHCONTRASTLIGHT,
                onClick = { themeViewModel.setTheme(ThemeViewModel.ThemeMode.HIGHCONTRASTLIGHT) }
            )
            Text("High Contrast Light Theme", modifier = Modifier.padding(start = 8.dp))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = themeMode == ThemeViewModel.ThemeMode.DARK,
                onClick = { themeViewModel.setTheme(ThemeViewModel.ThemeMode.DARK) }
            )
            Text("Dark Theme", modifier = Modifier.padding(start = 8.dp))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = themeMode == ThemeViewModel.ThemeMode.HIGHCONTRASTDARK,
                onClick = { themeViewModel.setTheme(ThemeViewModel.ThemeMode.HIGHCONTRASTDARK) }
            )
            Text("High Contrast Dark Theme", modifier = Modifier.padding(start = 8.dp))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = themeMode == ThemeViewModel.ThemeMode.SYSTEM_DEFAULT,
                onClick = { themeViewModel.setTheme(ThemeViewModel.ThemeMode.SYSTEM_DEFAULT) }
            )
            Text("System Default", modifier = Modifier.padding(start = 8.dp))
        }

        // Back button for navigation
        Button(onClick = onBack, modifier = Modifier.padding(10.dp)) {
            Text("Back")
        }
    }
}

