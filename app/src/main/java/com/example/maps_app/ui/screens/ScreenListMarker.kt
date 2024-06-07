package com.example.maps_app.ui.screens

import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.maps_app.ViewModel.MyViewModel
import com.example.maps_app.model.DataMarker
import com.example.maps_app.navigation.Routes

@RequiresApi(34)
@Composable
fun ScreenListMarker(navigation: NavHostController, myViewModel: MyViewModel) {

    GeneralyScaffold(navigation = navigation, myViewModel = myViewModel )
}
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ListMarker(myViewModel: MyViewModel, navigation: NavHostController,){
    val markers :  List< DataMarker> by myViewModel.markers.observeAsState(initial = mutableListOf())

    if (markers.isEmpty()) {
        Cargando()
    } else {
        LazyColumn(){
            items(markers){ marker ->
                Card (
                    border = BorderStroke(2.dp, Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            myViewModel.setActualMarker(marker)
                            navigation.navigate(Routes.PantallaMarcadores.route)
                        }
                        .padding(20.dp)
                ) {
                    InfoMarker(marker = marker)
                }
            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun InfoMarker(marker: DataMarker){
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){

        Column (
            verticalArrangement =  Arrangement.Center,
            horizontalAlignment =  Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.55f)
        ){
            Text(text = marker.title, fontWeight = FontWeight.Black, fontSize = 20.sp)
            Text(text = marker.subTitle, color = Color.DarkGray)
        }


        GlideImage(
            model = marker.img,
            contentDescription = "Image from Storage",
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Crop
        )

        
    }

}
