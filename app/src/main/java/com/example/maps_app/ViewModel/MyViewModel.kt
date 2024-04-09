package com.example.maps_app.ViewModel


import android.health.connect.datatypes.ExerciseRoute.Location
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.CameraPositionState

class MyViewModel : ViewModel(){

    private val markers = mutableListOf<Marker>()
    var showMarker = false
        private set
    var latLngActual = LatLng(0.0,0.0)
        private set

    fun setLatLngByActualMarker(latLng: LatLng){
        this.latLngActual = latLng
    }
    fun showMarker(show: Boolean){
        this.showMarker = show
    }

    fun setMarker(marker: Marker){
        this.markers.add(marker)
    }

    fun getMarkers():List<Marker>{
        return ArrayList(markers)
    }
}
