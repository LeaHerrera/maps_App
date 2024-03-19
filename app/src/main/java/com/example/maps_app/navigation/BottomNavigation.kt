package com.example.maps_app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

open class BottomNavigation {
    val route:String
    val icon: ImageVector
    val label:String

    constructor(route:String, icon: ImageVector, label:String){
        this.route = route
        this.icon = icon
        this.label = label
    }

    object Home:
        BottomNavigation( route = Routes.PantallaMapa.route, icon = Icons.Filled.LocationOn , label = "Location")
    object Favorite: BottomNavigation( route = Routes.PantallaListaMarcadores.route , icon = Icons.Filled.Favorite , label = "Favoritos" )

}