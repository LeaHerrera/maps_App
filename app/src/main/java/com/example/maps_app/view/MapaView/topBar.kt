package com.example.maps_app.view.MapaView

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.maps_app.ViewModel.MyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarMap(titulo: String, api: MyViewModel){
    TopAppBar(
        title = { Text(text = titulo, fontWeight = FontWeight.Black, fontSize = 30.sp) },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color.LightGray,
            titleContentColor = Color.White
        ),
        actions = {
            IconButton(
                onClick = {

                }
            ){
                Icon(imageVector = Icons.Default.AddCircle , contentDescription = "volver" )
            }
        }
    )
}