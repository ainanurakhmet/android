package com.example.task1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    var textState by remember { mutableStateOf(TextFieldValue(query)) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(8.dp, RoundedCornerShape(24.dp))
            .background(Color.White, RoundedCornerShape(24.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = textState,
                onValueChange = {
                    textState = it
                    onQueryChange(it.text)
                },
                textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CityList(cities: List<City>, onFavoriteToggle: (City) -> Unit) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        cities.forEach { city ->
            CityCard(city, onFavoriteToggle)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun CityCard(city: City, onFavoriteToggle: (City) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(16.dp))
            .background(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.height(180.dp)) {
            val image: Painter = painterResource(id = city.imageRes)
            Image(
                painter = image,
                contentDescription = city.name,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)),
                            startY = 100f
                        )
                    )
            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = city.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = city.country,
                    fontSize = 16.sp,
                    color = Color.LightGray
                )
            }


            IconButton(
                onClick = { onFavoriteToggle(city) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(Color.White.copy(alpha = 0.7f), shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = if (city.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border),
                    contentDescription = "Favorite",
                    tint = Color.Red
                )
            }
        }
    }
}
