package com.example.starterapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.starterapp.pages.ThemeSettingsScreen
import com.example.starterapp.pages.ToDoListPage
import com.example.starterapp.ui.theme.ToDoAppTheme
import com.example.starterapp.viewModels.ThemeViewModel
import com.example.starterapp.viewModels.ToDoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val toDoViewModel = ViewModelProvider(this)[ToDoViewModel::class.java]
        val themeViewModel = ViewModelProvider(this)[ThemeViewModel::class.java]

        setContent {
            ToDoAppTheme (themeViewModel){
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                ) {
                    NavHost(navController = navController, startDestination = "todo_list") {
                        composable(route = "todo_list") {
                            ToDoListPage(
                                viewModel = toDoViewModel,
                                themeViewModel = themeViewModel,
                                onNavigateToSettings = {
                                    navController.navigate("theme_settings")
                                }
                            )
                        }
                        composable(route = "theme_settings") {
                            ThemeSettingsScreen(
                                themeViewModel = themeViewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(0, 0)
        }
    }
}
