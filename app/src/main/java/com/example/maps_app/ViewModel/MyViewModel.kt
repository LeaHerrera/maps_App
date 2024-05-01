package com.example.maps_app.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.maps_app.model.DataMarker
import com.google.android.gms.maps.model.LatLng

class MyViewModel : ViewModel(){

    private val markers = mutableListOf<DataMarker>()
    private var _marker = MutableLiveData( DataMarker("","", LatLng(0.0,0.0)))
    val marker = _marker

    private val _showMarker = MutableLiveData(false)
    val showMarker = _showMarker

    private val _showLoading = MutableLiveData(false)
    val showLoading = _showLoading

    //DATOS DE MARCADOR NUEVO
    fun setTitleByMarker(title:String){
        this._marker.value = DataMarker( title, _marker.value!!.subTitle, _marker.value!!.latLng )
    }
    fun setSubTitleByMarker(subTitle:String){
        this._marker.value =  DataMarker( _marker.value!!.title, subTitle, _marker.value!!.latLng )
    }
    fun setCordenadasByMarker(latLng: LatLng){
        this._marker.value =  DataMarker( _marker.value!!.title, _marker.value!!.subTitle, latLng )
    }

    //VISUALIZAR / GUARDAR  AÑADIR NEW MARKER
    fun showMarker(show: Boolean){
        this._showMarker!!.value = show
        if (!show){
            this._showLoading.value = true
        }
    }
    fun loadingStop(){
        this._showLoading.value = false
    }

    fun setMarker(){
        this.markers.add(_marker.value!!)
    }
    //LISA MARCADORES
    fun getMarkers():List<DataMarker>{
        return ArrayList(markers)
    }

}
