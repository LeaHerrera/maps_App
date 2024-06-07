package com.example.maps_app.navigation


sealed class Routes(val route: String) {
    object PantallaSplash: Routes("screen_launch")
    object PantallaLogin: Routes("screen_login")
    object PantallaMenu: Routes("screen_menu")
    object PantallaMapa: Routes("screen_maps")
    object PantallaMarcadores: Routes("screen_Marker")
    object PantallaListaMarcadores: Routes("screen_markerList")
    object PantallaCamara: Routes("screen_camara")
    object PanttallaGalery: Routes("screen_galery")
}