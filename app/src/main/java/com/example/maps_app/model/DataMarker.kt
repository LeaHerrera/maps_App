package com.example.maps_app.model

import android.icu.text.CaseMap.Title
import com.google.android.gms.maps.model.LatLng

data class DataMarker (
    var title:String,
    var subTitle:String,
    var latLng:LatLng
)