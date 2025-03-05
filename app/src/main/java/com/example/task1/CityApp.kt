package com.example.task1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun CityApp(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        val navController = rememberNavController()
        var cities by remember {
            mutableStateOf(
                listOf(
                    City("Paris", "France", R.drawable.paris),
                    City("Tokyo", "Japan", R.drawable.tokyo),
                    City("New York", "USA", R.drawable.newyork),
                    City("London", "UK", R.drawable.london)
                )
            )
        }

        Scaffold(
            bottomBar = { NavigationBar(navController) }
        ) { innerPadding ->
            NavHost(
                navController,
                startDestination = "home",
                modifier = Modifier.padding(paddingValues = innerPadding)
            ) {
                composable("home") {
                    HomeScreen(cities) { updatedCities ->
                        cities = updatedCities
                    }
                }
                composable("favorites") { FavoritesScreen(cities) }
                composable("profile") { ProfileScreen(navController) }
                composable("settings") { SettingsScreen(isDarkTheme, onThemeToggle) }
                composable("plane_animation") { CityPlaneAnimation() }
            }
        }
    }
}
