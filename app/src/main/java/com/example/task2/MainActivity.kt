package com.example.task2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AnimatedSplashScreen(navController)
        }
    }
}

@Composable
fun AnimatedSplashScreen(navController: NavHostController) {
    var isSplashVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(3000)
        isSplashVisible = false
    }

    if (isSplashVisible) {
        SplashScreen()
    } else {
        AnimatedSkyApp(navController)
    }
}

@Composable
fun SplashScreen() {
    val scale = remember { Animatable(0.5f) }
    val bounceAnim = tween<Float>(1000, easing = EaseOutBounce)

    val rotationAnim: InfiniteRepeatableSpec<Float> = infiniteRepeatable(
        animation = tween(durationMillis = 1500, easing = LinearEasing),
        repeatMode = RepeatMode.Restart
    )

    val rotationAngle by animateFloatAsState(
        targetValue = 360f,
        animationSpec = rotationAnim,
        label = "Rotation Animation"
    )

    LaunchedEffect(Unit) {
        scale.animateTo(1.2f, animationSpec = bounceAnim)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .scale(scale.value)
                .rotate(rotationAngle)
                .background(Color.Blue, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("App", color = Color.White, fontSize = 20.sp)
        }
    }
}

@Composable
fun AnimatedSkyApp(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("details") { DetailsScreen() }
    }
}


@Composable
fun HomeScreen(navController: NavController) {
    var showAnimation by remember { mutableStateOf(false) }
    var buttonExpanded by remember { mutableStateOf(false) }

    val airplaneComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.airplane))
    val airplaneProgress by animateLottieCompositionAsState(
        airplaneComposition,
        isPlaying = true,
        restartOnPlay = true,
        iterations = LottieConstants.IterateForever
    )

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        AnimatedSun()
        AnimatedMovingClouds()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sky Adventure",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            AnimatedVisibility(visible = showAnimation) {
                LottieAnimation(
                    composition = airplaneComposition,
                    progress = { airplaneProgress },
                    modifier = Modifier.size(200.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    showAnimation = !showAnimation
                    buttonExpanded = !buttonExpanded
                },
                modifier = Modifier.animateContentSize()
            ) {
                Text(
                    text = if (buttonExpanded) "Hide Airplane" else "Show Airplane",
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            AnimatedButton { navController.navigate("details") }
        }
    }
}

@Composable
fun AnimatedSun() {
    val sunComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.sun))
    val sunProgress by animateLottieCompositionAsState(
        sunComposition,
        isPlaying = true,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = sunComposition,
        progress = { sunProgress },
        modifier = Modifier
            .size(100.dp)
            .offset(y = 80.dp)
            .background(Color.Transparent)
    )
}

@Composable
fun AnimatedMovingClouds() {
    val infiniteTransition = rememberInfiniteTransition()
    val cloudX by infiniteTransition.animateFloat(
        initialValue = -200f,
        targetValue = 400f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        LottieAnimation(
            composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cloud)).value,
            progress = { 1f },
            modifier = Modifier.size(150.dp).offset(x = cloudX.dp, y = 120.dp)
        )
    }
}

@Composable
fun DetailsScreen() {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(3000)
        isLoading = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            val loadingComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
            val loadingProgress by animateLottieCompositionAsState(
                loadingComposition,
                isPlaying = true,
                iterations = LottieConstants.IterateForever
            )

            LottieAnimation(
                composition = loadingComposition,
                progress = { loadingProgress },
                modifier = Modifier.size(150.dp)
            )
        } else {
            Text(text = "Details Screen", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}


@Composable
fun AnimatedButton(onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (pressed) 1.2f else 1f, label = "ButtonScale")

    Button(
        onClick = {
            pressed = !pressed
            onClick()
        },
        modifier = Modifier.scale(scale)
    ) {
        Text(text = "Go to Details")
    }
}
