package com.example.maps_app.ui.screens

import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.maps_app.ViewModel.MyViewModel

@RequiresApi(34)
@Composable
fun ScreenListMarker(navigation: NavHostController, myViewModel: MyViewModel) {

    GeneralyScaffold(navigation = navigation, myViewModel = myViewModel )
}
@Composable
fun ListMarker(myViewModel: MyViewModel){

}