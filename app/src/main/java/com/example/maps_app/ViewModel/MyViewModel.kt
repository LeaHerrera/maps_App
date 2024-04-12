package com.example.maps_app.ViewModel


import android.health.connect.datatypes.ExerciseRoute.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.maps_app.model.DataMarker
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.CameraPositionState

class MyViewModel : ViewModel(){

    private val markers = mutableListOf<DataMarker>()
    private var _marker = MutableLiveData( DataMarker("","", LatLng(0.0,0.0)))
    val marker = _marker

    private val _showMarker = MutableLiveData(false)
    val showMarker = _showMarker

    //DATOS DE MARCADOR NUEVO
    fun setTitleByMarker(title:String){
        this._marker.value = DataMarker( title, _marker.value!!.subTitle, _marker.value!!.latLng )
    }
    fun setSubTitleByMarker(subTitle:String){
        this._marker.value!!.subTitle = subTitle
    }
    fun setCordenadasByMarker(latLng: LatLng){
        this._marker.value!!.latLng = latLng
    }

    fun showMarker(show: Boolean){
        this._showMarker!!.value = show
    }

    fun setMarker(){
        this.markers.add(_marker.value!!)
    }

    fun getMarkers():List<DataMarker>{
        return ArrayList(markers)
    }
}
