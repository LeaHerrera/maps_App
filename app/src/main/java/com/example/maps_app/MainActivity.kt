package com.example.maps_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.maps_app.ViewModel.MyViewModel
import com.example.maps_app.navigation.Routes
import com.example.maps_app.ui.screens.ScreenListMarker
import com.example.maps_app.ui.screens.ScreenLogin
import com.example.maps_app.ui.screens.ScreenMaps
import com.example.maps_app.ui.screens.ScreenAddMarcadores
import com.example.maps_app.ui.screens.ScreenMenu
import com.example.maps_app.ui.screens.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val myViewModel by viewModels<MyViewModel>()
            val navigation = rememberNavController()

            NavHost(
                navController = navigation,
                startDestination = Routes.PantallaSplash.route
            ) {
                composable(Routes.PantallaSplash.route) { SplashScreen(navigation) }
                composable(Routes.PantallaLogin.route) { ScreenLogin(navigation, myViewModel) }
                composable(Routes.PantallaMenu.route) { ScreenMenu(navigation, myViewModel) }
                composable(Routes.PantallaMapa.route) { ScreenMaps(navigation, myViewModel) }
                composable(Routes.PantallaAddMarcadores.route) { ScreenAddMarcadores(navigation, myViewModel) }
                composable(Routes.PantallaListaMarcadores.route) { ScreenListMarker(navigation, myViewModel) }
            }

        }
    }
}
