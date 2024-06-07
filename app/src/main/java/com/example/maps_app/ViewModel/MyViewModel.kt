package com.example.maps_app.ViewModel

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.maps_app.model.DataMarker
import com.example.maps_app.model.Repository
import com.example.maps_app.model.User
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MyViewModel : ViewModel(){

    private var _markers = MutableLiveData ( mutableListOf< DataMarker >() )
    val markers = _markers
    private var _marker = MutableLiveData( DataMarker(null,"","", 0.0,0.0,"") )
    val marker = _marker

    private val _showMarker = MutableLiveData(false)
    val showMarker = _showMarker

    private val _showLoading = MutableLiveData(false)
    val showLoading = _showLoading

    //Camera
    private var _cameraPermissionGranted = MutableLiveData(false)
    val cameraPermissionGranted = _cameraPermissionGranted

    fun cameraPermissionGranted(permition: Boolean){
        _cameraPermissionGranted.value = permition
    }

    var shouldShowPermissionRationale = MutableLiveData(false)



    private var _showPermissionDenied = MutableLiveData(false)
    val showPermissionDenied = _showPermissionDenied

    fun setShowPermissionDenied(permition: Boolean){
        _showPermissionDenied.value = permition
    }

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
        this._marker.value = DataMarker(null, title, _marker.value!!.subTitle, _marker.value!!.latitud, _marker.value!!.longitud, _marker.value!!.img )

    }
    fun setSubTitleByMarker(subTitle:String){
        this._marker.value =  DataMarker(null, _marker.value!!.title, subTitle, _marker.value!!.latitud, _marker.value!!.longitud, _marker.value!!.img )
    }
    fun setCordenadasByMarker(latLng: LatLng){
        this._marker.value =  DataMarker(null, _marker.value!!.title, _marker.value!!.subTitle, latLng.latitude, latLng.longitude, _marker.value!!.img )
    }

    fun setImageByMarker(img: String){
        this._marker.value =  DataMarker(null, _marker.value!!.title, _marker.value!!.subTitle, _marker.value!!.latitud, _marker.value!!.longitud, img )
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


    ////// FIRESTORE ////////
    private val repository = Repository()

    private var _actualMarker = MutableLiveData( DataMarker() )
    val actualMarker = _actualMarker

    fun setMarker(){
        this.repository.addMarker(_marker.value!!)
    }

    fun setActualMarker(marker: DataMarker){
        _actualMarker.value = marker
    }

    fun deleteActualMarker(){
        _actualMarker.value?.markerId?.let { repository.deleteMarker(it) }
    }

    fun getFirestoreMarkers() {
        repository.getMarkers().addSnapshotListener{ value, error ->
            if (error != null){
                Log.e("Firestore error", error.message.toString())
            }
            val tempList = mutableListOf< DataMarker >()

            for (dc: DocumentChange in value?.documentChanges!!){
                if (dc.type == DocumentChange.Type.ADDED){
                    val newMarker = dc.document.toObject(DataMarker::class.java)
                    newMarker.markerId = dc.document.id
                    tempList.add(  newMarker )
                }
            }
            _markers.value = tempList
        }
    }

    fun getFirestoreMarker(Id: String) {
        repository.getMarker(Id).addSnapshotListener{ value, error ->
            if (error != null){
                Log.e("UserRepository","Listen failed", error)
                return@addSnapshotListener
            }

            if ( value != null && value.exists() ){
                val marker = value.toObject(DataMarker::class.java)
                if (marker != null){
                    marker.markerId = Id
                }
                _actualMarker.value = marker
            } else {
                Log.d("UserRepository","Current data: null")
            }
        }
    }

}
