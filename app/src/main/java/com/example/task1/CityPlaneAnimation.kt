package com.example.task1

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun CityPlaneAnimation() {

    val coroutineScope = rememberCoroutineScope()


    val pointA = Pair(100f, 1000f)
    val pointB = Pair(600f, 200f)

    val planeOffsetX = remember { Animatable(pointA.first) }
    val planeOffsetY = remember { Animatable(pointA.second) }

    val controlPoint1 = Pair(200f, 1100f)
    val controlPoint2 = Pair(400f, 900f)

    val startX = pointA.first
    val startY = pointA.second

    val endX = pointB.first
    val endY = pointB.second

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            coroutineScope.launch {
                planeOffsetX.animateTo(endX, animationSpec = tween(2000))
                planeOffsetY.animateTo(endY, animationSpec = tween(2000))
            }
        }) {
            Text("Start Animation")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                drawSchematicMap()

                drawCircle(
                    color = Color.Red,
                    center = androidx.compose.ui.geometry.Offset(pointA.first, pointA.second),
                    radius = 20f
                )
                drawCircle(
                    color = Color.Blue,
                    center = androidx.compose.ui.geometry.Offset(pointB.first, pointB.second),
                    radius = 20f
                )
            }

            Text(
                text = "City A",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                modifier = Modifier
                    .offset(x = pointA.first.dp - 30.dp, y = pointA.second.dp - 30.dp)
            )
            Text(
                text = "City B",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                modifier = Modifier
                    .offset(x = pointB.first.dp - 30.dp, y = pointB.second.dp - 30.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Plane",
                modifier = Modifier
                    .size(40.dp)
                    .graphicsLayer {
                        val t = planeOffsetX.value / endX
                        val bezierX = cubicBezierValue(startX, controlPoint1.first, controlPoint2.first, endX, t)
                        val bezierY = cubicBezierValue(startY, controlPoint1.second, controlPoint2.second, endY, t)

                        translationX = bezierX
                        translationY = bezierY + 10
                    }
            )
        }
    }
}

private fun DrawScope.drawSchematicMap() {
    drawRect(
        color = Color(0xFF87CEEB),
        size = size
    )


    val landColor1 = Color(0xFFFFD700)
    val landColor2 = Color(0xFF32CD32)


}


private fun cubicBezierValue(p0: Float, p1: Float, p2: Float, p3: Float, t: Float): Float {
    val u = 1 - t
    return (u * u * u * p0) +
            (3 * u * u * t * p1) +
            (3 * u * t * t * p2) +
            (t * t * t * p3)
}
