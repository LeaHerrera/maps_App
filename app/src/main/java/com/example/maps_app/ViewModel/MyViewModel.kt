package com.example.maps_app.ViewModel


import android.health.connect.datatypes.ExerciseRoute.Location
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.maps_app.model.DataMarker
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.firebase.storage.FirebaseStorage
import com.google.maps.android.compose.CameraPositionState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MyViewModel : ViewModel(){

    private val markers = mutableListOf<DataMarker>()
    private var _marker = MutableLiveData( DataMarker("","", LatLng(0.0,0.0)))
    val marker = _marker

    private val _showMarker = MutableLiveData(false)
    val showMarker = _showMarker

    private val _showLoading = MutableLiveData(false)
    val showLoading = _showLoading

    //Camera
    val cameraPermissionGranted = MutableLiveData(false)
    val shouldShowPermissionRationale = MutableLiveData(false)
    val showPermissionDenied = MutableLiveData(false)
    var returnAgain = false
    // Marker Image
    var image = MutableLiveData<String?>()

    //Upload Image
    fun uploadImage(imageUri: Uri) {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storage = FirebaseStorage.getInstance().getReference("images/$fileName")
        storage.putFile(imageUri)
            .addOnSuccessListener {
                Log.i("IMAGE UPLOAD", "Image uploaded successfully")
                storage.downloadUrl.addOnSuccessListener {
                    Log.i("IMAGE", it.toString())
                    image.value = it.toString()
                }
            }
            .addOnFailureListener {
                Log.i("IMAGE UPLOAD", "Image upload failed")
            }
    }


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
