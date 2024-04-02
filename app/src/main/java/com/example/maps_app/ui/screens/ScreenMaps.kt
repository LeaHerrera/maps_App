package com.example.maps_app.ui.screens

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.maps_app.ViewModel.MyViewModel
import com.example.maps_app.navigation.BottomNavigation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ScreenMaps(navigation: NavHostController, myViewModel: MyViewModel) {

    val bottomNavigationItem = listOf(
        BottomNavigation.Home,
        BottomNavigation.Favorite
    )
    val permissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )
    LaunchedEffect(Unit){
        permissionState.launchPermissionRequest()
    }

    val titulo = "Google Maps"
    Scaffold(
        topBar = { TopBarMap( titulo, myViewModel ) },
        bottomBar = { MyBottomBarNavigation( navigation , bottomNavigationItem)  }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
        ){
            if (permissionState.status.isGranted){
                Maps()
            } else {
                Maps()
            }
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
