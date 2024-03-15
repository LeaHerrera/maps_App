package com.example.maps_app.view.MapaView

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.maps_app.ViewModel.MyViewModel
import com.example.maps_app.model.BotomNavigation.BottomNavigation
import com.example.maps_app.view.MyBottomBar
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenMaps(navigation: NavHostController, myViewModel: MyViewModel) {

    val bottomNavigationItem = listOf(
        BottomNavigation.Home,
        BottomNavigation.Favorite
    )

    val titulo = "Google Maps"
    Scaffold(
        topBar = { TopBarMap( titulo, myViewModel ) },
        bottomBar = { MyBottomBar( navigation , bottomNavigationItem)  }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
        ){
            Maps()
        }
    }

}

@Composable
fun Maps(){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),

    ){

        val itb = LatLng(41.4534265, 2.1837151)
        val cameraPositionState = rememberCameraPositionState{
            position = CameraPosition.fromLatLngZoom(itb,10f)
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState =  cameraPositionState,
            onMapClick = {

            }
        )
    }
}