package com.example.maps_app.ui.screens

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.maps_app.ViewModel.MyViewModel
import com.example.maps_app.navigation.BottomNavigation

@RequiresApi(34)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralyScaffold(navigation: NavHostController, myViewModel: MyViewModel) {

    val navBackStackEntry by navigation.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavigationItem = listOf(
        BottomNavigation.Home,
        BottomNavigation.Favorite
    )

    val titulo = "Google Maps"
    Scaffold(
        topBar = { TopBarMap(titulo, myViewModel) },
        bottomBar = { MyBottomBarNavigation(navigation, bottomNavigationItem) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
        ) {
            if (currentRoute == bottomNavigationItem[0].route){
                CompositionFromMaps(navigation, myViewModel = myViewModel)
            } else if (currentRoute == bottomNavigationItem[1].route){
                myViewModel.getFirestoreMarkers()
                ListMarker(myViewModel = myViewModel, navigation)
            }
        }
    }
}