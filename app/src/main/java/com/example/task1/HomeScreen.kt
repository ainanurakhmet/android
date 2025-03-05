package com.example.task1

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(cities: List<City>, onFavoriteToggle: (List<City>) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var updatedCities by remember { mutableStateOf(cities) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SearchBar(searchQuery) { query ->
            searchQuery = query
            updatedCities = cities.filter { it.name.contains(query, ignoreCase = true) }
        }
        Spacer(modifier = Modifier.height(16.dp))
        CityList(updatedCities) { city ->
            updatedCities = updatedCities.map {
                if (it.name == city.name) it.copy(isFavorite = !it.isFavorite) else it
            }
            onFavoriteToggle(updatedCities)
        }
    }
}
