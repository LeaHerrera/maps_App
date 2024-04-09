package com.example.maps_app.model

import android.icu.text.CaseMap.Title
import com.google.android.gms.maps.model.LatLng

class DataMarker {

    var title:String
        private set
    var subTitle:String
        private set
    var latLng:LatLng
    constructor(title: String, subTitle: String, latLng: LatLng, ){
        this.title = title
        this.subTitle = subTitle
        this.latLng = latLng
    }
}