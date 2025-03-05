package com.example.task1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier


@Composable
fun FavoritesScreen(cities: List<City>) {
    val favoriteCities = cities.filter { it.isFavorite }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Favorite Cities", fontSize = 24.sp)
        CityList(favoriteCities) {}
    }
}
