package com.example.maps_app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import com.example.maps_app.ViewModel.MyViewModel
import com.example.maps_app.model.DataMarker

@Composable
fun ScreenMarcador(navigation: NavHostController, myViewModel: MyViewModel) {
    val marker : DataMarker by myViewModel.actualMarker.observeAsState(initial = DataMarker())
    
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        InfoMarker(marker = marker)
        Row {
            Button(
                onClick = {
                    myViewModel.deleteActualMarker()
                    navigation.navigateUp()
                }
            ) {
                Icon( Icons.Filled.DeleteForever, contentDescription = "eliminar")
            }
            Button(onClick = { navigation.navigateUp() }) {
                Icon( Icons.Filled.ArrowBackIosNew, contentDescription = "salir")
            }
        }
    }
    
}