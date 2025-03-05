package com.example.task2

import android.content.Context
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.*
import androidx.glance.unit.ColorProvider
import androidx.glance.background
import kotlin.random.Random
import androidx.compose.ui.graphics.Color
import androidx.glance.appwidget.action.actionRunCallback

class MyFlightWidget : GlanceAppWidget() {
    private val flightData = listOf(
        Triple("Алматы", "Дубай", "KC 123"),
        Triple("Нур-Султан", "Лондон", "BA 456"),
        Triple("Париж", "Нью-Йорк", "AF 789"),
        Triple("Токио", "Сингапур", "JL 012"),
        Triple("Берлин", "Мадрид", "LH 345")
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val (departure, arrival, flightNumber) = flightData[Random.nextInt(flightData.size)]
        val departureTime = "${Random.nextInt(0, 23)}:${Random.nextInt(10, 59)}"

        provideContent {
            GlanceTheme {
                Box(
                    modifier = GlanceModifier
                        .fillMaxSize()
                        .background(ColorProvider(Color(0xFFF5F5F5)))
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = GlanceModifier
                            .fillMaxWidth()
                            .background(ColorProvider(Color.White))
                            .padding(12.dp),
                        content = {
                            Text(
                                text = "✈️ Посадочный билет",
                                style = TextStyle(
                                    color = ColorProvider(Color(0xFF2C3E50)),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = GlanceModifier.padding(bottom = 8.dp)
                            )

                            Box(
                                modifier = GlanceModifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(ColorProvider(Color.Gray)),
                                content = {}
                            )

                            Spacer(modifier = GlanceModifier.height(6.dp))

                            Row(
                                modifier = GlanceModifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                content = {
                                    Column(
                                        content = {
                                            Text(
                                                text = "Отправление",
                                                style = TextStyle(color = ColorProvider(Color.Gray), fontSize = 12.sp)
                                            )
                                            Text(
                                                text = departure,
                                                style = TextStyle(color = ColorProvider(Color.Black), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                            )
                                        }
                                    )

                                    Spacer(modifier = GlanceModifier.width(20.dp))

                                    Text(text = "➝", style = TextStyle(fontSize = 20.sp))

                                    Spacer(modifier = GlanceModifier.width(20.dp))

                                    Column(
                                        content = {
                                            Text(
                                                text = "Прибытие",
                                                style = TextStyle(color = ColorProvider(Color.Gray), fontSize = 12.sp)
                                            )
                                            Text(
                                                text = arrival,
                                                style = TextStyle(color = ColorProvider(Color.Black), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                            )
                                        }
                                    )
                                }
                            )

                            Spacer(modifier = GlanceModifier.height(6.dp))

                            Row(
                                modifier = GlanceModifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                content = {
                                    Column(
                                        content = {
                                            Text(
                                                text = "Рейс",
                                                style = TextStyle(color = ColorProvider(Color.Gray), fontSize = 12.sp)
                                            )
                                            Text(
                                                text = flightNumber,
                                                style = TextStyle(color = ColorProvider(Color(0xFFE74C3C)), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                            )
                                        }
                                    )

                                    Spacer(modifier = GlanceModifier.width(20.dp))

                                    Column(
                                        content = {
                                            Text(
                                                text = "Вылет",
                                                style = TextStyle(color = ColorProvider(Color.Gray), fontSize = 12.sp)
                                            )
                                            Text(
                                                text = departureTime,
                                                style = TextStyle(color = ColorProvider(Color(0xFF27AE60)), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                            )
                                        }
                                    )
                                }
                            )

                            Spacer(modifier = GlanceModifier.height(12.dp))

                            Box(
                                modifier = GlanceModifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(ColorProvider(Color.Gray)),
                                content = {}
                            )

                            Spacer(modifier = GlanceModifier.height(8.dp))

                            Box(
                                modifier = GlanceModifier
                                    .fillMaxWidth()
                                    .height(20.dp)
                                    .background(ColorProvider(Color.Black)),
                                content = {}
                            )

                            Spacer(modifier = GlanceModifier.height(12.dp))

                            Button(
                                text = "🔄 Обновить рейс",
                                onClick = actionRunCallback<UpdateFlightAction>()
                            )
                        }
                    )
                }
            }
        }
    }
}
