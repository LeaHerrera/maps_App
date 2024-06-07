package com.example.maps_app.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.maps_app.MainActivity
import com.example.maps_app.ViewModel.MyViewModel
import com.example.maps_app.ViewModel.ViewModelFireBase
import com.example.maps_app.model.DataMarker
import com.example.maps_app.navigation.Routes
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.delay


@RequiresApi(34)
@OptIn( ExperimentalPermissionsApi::class)
@Composable
fun ScreenMaps(
    navigation: NavHostController,
    FireBase: ViewModelFireBase,
    myViewModel: MyViewModel
) {

    val permissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )
    LaunchedEffect(Unit){
        permissionState.launchPermissionRequest()
    }

    GeneralyScaffold(navigation = navigation, myViewModel = myViewModel)
}

@RequiresApi(34)
@SuppressLint("MissingPermission")
@Composable
fun CompositionFromMaps(navigation: NavHostController, myViewModel: MyViewModel ){

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
        Maps(myViewModel = myViewModel, cameraPositionState = cameraPositionState)
        CrearMarker(navigation, myViewModel)
    }

}

@Composable
fun Maps(myViewModel: MyViewModel, cameraPositionState: CameraPositionState){
    val show: Boolean by myViewModel.showLoading.observeAsState(initial = false)
    val markers :  List<DataMarker> by myViewModel.markers.observeAsState(initial = mutableListOf())

    if (show){
        LaunchedEffect(key1 = Unit) {
            delay(1000L)
            myViewModel.loadingStop()
        }
        Cargando()
    } else {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState =  cameraPositionState,
            onMapLongClick = {
                myViewModel.showMarker(true)
                myViewModel.setCordenadasByMarker(it)
            }
        ){
            markers.forEach{ marker  ->
                Marker(
                    state = MarkerState(LatLng(marker.latitud, marker.longitud)),
                    title = marker.title,
                    snippet = marker.subTitle
                )
            }

        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearMarker(navigation: NavHostController, myViewModel: MyViewModel){
    val show: Boolean by myViewModel.showMarker.observeAsState(initial = false)
    val context = LocalContext.current

    val shouldShowPermissionRationale by myViewModel.shouldShowPermissionRationale.observeAsState(false)

    val laucher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {isGradens ->
            if (isGradens){
                myViewModel.cameraPermissionGranted(true)
                navigation.navigate(Routes.PantallaCamara.route)
            } else {
                myViewModel.shouldShowPermissionRationale.value = shouldShowRequestPermissionRationale(
                    context as Activity,
                    Manifest.permission.CAMERA
                )
                if (!shouldShowPermissionRationale){
                    Log.i("CameraScreen", "No podemos volver a pedir permisos")
                    myViewModel.setShowPermissionDenied(true)
                }
            }
        }
    )

    if (show){
       MyDialog(myViewModel = myViewModel, laucher = laucher, navigation = navigation)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun MyDialog(myViewModel: MyViewModel, laucher: ManagedActivityResultLauncher<String, Boolean>, navigation: NavHostController){
    val context = LocalContext.current
    val isCameraPermissionGranted by myViewModel.cameraPermissionGranted.observeAsState(false)
    val showPermissionDenied by myViewModel.showPermissionDenied.observeAsState(false)
    val imageUrl by myViewModel.image.observeAsState(null)

    Dialog(
        onDismissRequest = { myViewModel.showMarker(false) },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Column(
            Modifier
                .background(Color.Gray)
                .padding(24.dp)
                .fillMaxWidth(0.95f))
        {
            val marker:DataMarker by myViewModel.marker.observeAsState(initial = DataMarker() )

            TextField(
                value = marker.title,
                onValueChange = { myViewModel.setTitleByMarker(it) },
                label = { Text(text = "Introduce un Titulo") }
            )
            TextField(
                value = marker.subTitle,
                onValueChange = { myViewModel.setSubTitleByMarker(it) },
                label = { Text(text = "Introduce un subtitulo") }
            )
            Column (
                verticalArrangement = Arrangement.Center ,
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Spacer(modifier = Modifier.padding(10.dp))
                Row {
                    Button(
                        onClick = {
                            if ( !imageUrl.isNullOrEmpty() ) {
                                myViewModel.setImageByMarker(imageUrl!!)
                                myViewModel.setMarker()
                                myViewModel.showMarker(false)
                            } else {
                                myToast(context = context , "¡Debes Introducir una imagen!")
                            }

                        },
                        modifier = Modifier.fillMaxWidth(0.55f)
                    ) {
                        Text(text = "Confirmar")
                    }
                    Button(
                        onClick = {
                            myViewModel.showMarker(false)
                            myViewModel.loadingStop()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp)
                    ) {
                        Text(text = "Cancelar")
                    }
                }

                if (!imageUrl.isNullOrEmpty()){
                    GlideImage(
                        model = imageUrl,
                        contentDescription = "Image from Storage",
                        modifier = Modifier.size(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Button(
                    onClick = {
                        if(!isCameraPermissionGranted) {
                            laucher.launch(Manifest.permission.CAMERA)
                        } else {
                            navigation.navigate(Routes.PantallaCamara.route)
                        }
                        if (showPermissionDenied) myViewModel.showMarker(false)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Filled.CameraAlt, "Location Icon" )
                }

            }

        }
    }
}


fun myToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
















