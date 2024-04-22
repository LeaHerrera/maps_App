package com.example.maps_app.ui.screens

import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.maps_app.ViewModel.MyViewModel

@RequiresApi(34)
@Composable
fun ScreenListMarker(navigation: NavHostController, myViewModel: MyViewModel) {

    GeneralyScaffold(navigation = navigation, myViewModel = myViewModel )
}
@Composable
fun ListMarker(myViewModel: MyViewModel){

    LazyColumn(){
        items(myViewModel.getMarkers()){
            Card (
                border = BorderStroke(2.dp, Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { TODO() }
            ) {
                Column (
                    verticalArrangement =  Arrangement.Center,
                    horizontalAlignment =  Alignment.CenterHorizontally
                ){
                    Text(text = it.title, fontWeight = FontWeight.Black, fontSize = 20.sp)
                    Text(text = it.subTitle, color = Color.DarkGray)
                }
            }
        }
    }

}