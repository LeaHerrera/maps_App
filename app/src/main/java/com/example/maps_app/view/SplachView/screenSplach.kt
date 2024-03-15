package com.example.maps_app.view.SplachView

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.maps_app.R
import com.example.maps_app.model.Navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun Splash(alphaAnim: Float) {
    Column(modifier = Modifier
        .paint( //fondo
            painterResource(id = R.drawable.ic_launcher_foreground),
            contentScale = ContentScale.FillBounds
        )
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "logo", alpha = alphaAnim
        )
        Text(text = "¡!", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

    }
}

@Composable
fun SplashScreen(navController: NavController) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 5000), label = ""
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(5500)
        navController.popBackStack()
        navController.navigate(Routes.PantallaLogin.route)
    }
    Splash(alphaAnim.value)
}

