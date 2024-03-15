package com.example.maps_app.model.Navigation


sealed class Routes(val route: String) {
    object PantallaSplash: Routes("screen_launch")
    object PantallaLogin: Routes("screen_login")
    object PantallaMenu: Routes("screen_menu")
    object PantallaMapa: Routes("screen_maps")
    object PantallaAddMarcadores: Routes("screen_addMarker")
    object PantallaListaMarcadores: Routes("screen_markerList")
}