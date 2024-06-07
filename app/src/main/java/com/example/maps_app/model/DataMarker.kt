package com.example.maps_app.model

import android.icu.text.CaseMap.Title
import com.google.android.gms.maps.model.LatLng

data class DataMarker (
    var markerId: String? = null,
    var title:String,
    var subTitle:String,
    var latitud: Double,
    var longitud: Double,
    var img: String
) {
    constructor() : this(
        null,
        "",
        "",
        0.0,0.0,
        ""
    )
}