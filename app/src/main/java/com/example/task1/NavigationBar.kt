package com.example.task1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NavigationBar(navController: NavController) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("home") },
            icon = { Icon(painterResource(id = R.drawable.ic_home), contentDescription = "Home", tint = Color.Black) },
            label = null
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("favorites") },
            icon = { Icon(painterResource(id = R.drawable.ic_favorite), contentDescription = "Favorites", tint = Color.Red) },
            label = null
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("profile") },
            icon = { Icon(painterResource(id = R.drawable.ic_profile), contentDescription = "Profile", tint = Color.Blue) },
            label = null
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("plane_animation") },
            icon = { Icon(Icons.Default.Flight, contentDescription = "Plane Animation") },
            label = null
        )

    }
}
