package com.example.maps_app.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.maps_app.MainActivity
import com.example.maps_app.ViewModel.MyViewModel
import com.example.maps_app.model.DataMarker
import com.example.maps_app.navigation.BottomNavigation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@RequiresApi(34)
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
                Maps(myViewModel)
            } else {
                Maps(myViewModel)
            }
        }
    }

}

@RequiresApi(34)
@SuppressLint("MissingPermission")
@Composable
fun Maps( myViewModel: MyViewModel ){

    val context = LocalContext.current
    val fusedLocationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    var lastKnownLocation by remember {
        mutableStateOf<Location?>(null)
    }
    var deviceLatLng by remember {
        mutableStateOf(LatLng(0.0,0.0))
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(deviceLatLng, 2f)
    }
    val locationResult = fusedLocationProviderClient.getCurrentLocation(100, null)

    locationResult.addOnCompleteListener(context as MainActivity){task ->
        if (task.isSuccessful){
            lastKnownLocation = task.result
            deviceLatLng = LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
            cameraPositionState.position = CameraPosition.fromLatLngZoom(deviceLatLng, 15f)

        } else {
            Log.e("Error","Exeption %s", task.exception)
        }
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ){

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState =  cameraPositionState,
            onMapLongClick = {
                myViewModel.showMarker(true)
                myViewModel.setCordenadasByMarker(it)
            }
        ){
            Marker(
                state = MarkerState(),
                title = "",
                snippet = ""
            )
        }
        CrearMarker( myViewModel = myViewModel)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearMarker( myViewModel: MyViewModel){
    val show: Boolean by myViewModel.showMarker.observeAsState(initial = false)

    if (show){
        Dialog(
            onDismissRequest = { myViewModel.showMarker(false) },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Column(
                Modifier
                    .background(Color.Gray)
                    .padding(24.dp)
                    .fillMaxWidth(0.9f))
            {
                val marker:DataMarker by myViewModel.marker.observeAsState(initial = DataMarker("","", LatLng(0.0,0.0)) )

                TextField(
                    value = marker.title,
                    onValueChange = { myViewModel.setTitleByMarker(it) },
                    label = { Text(text = "Enter your name") }
                )
                TextField(
                    value = marker.subTitle,
                    onValueChange = { myViewModel.setSubTitleByMarker(it) },
                    label = { Text(text = "Enter your name") }
                )


            }
        }
    }


}


