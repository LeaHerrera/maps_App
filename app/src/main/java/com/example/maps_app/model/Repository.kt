package com.example.maps_app.model

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class Repository {

    private val database = FirebaseFirestore.getInstance()
    private val MARKER_PATH = "DataMarker"
    private val USER_PATH = "users"

    ////////////////// INTERACCIONES CON LA COLLECCION USER //////////////////
    fun addUser(user: User){
        database.collection(USER_PATH).add(
            hashMapOf(
                "userName" to user.userName,
                "age" to user.age,
                "profilePicture" to user.profilePicture
            )
        )
    }

    fun editUser(editedUser: User){
        database.collection(USER_PATH).document(editedUser.userId!!).set(
            hashMapOf(
                "userName" to editedUser.userName,
                "age" to editedUser.age,
                "profilePicture" to editedUser.profilePicture
            )
        )
    }

    fun deleteUser(userId: String){
        database.collection(USER_PATH).document(userId).delete()
    }

        //Interacciones con un documento de la colleccion users
    fun getUsers(): CollectionReference {
        return database.collection(USER_PATH)
    }

    fun getUser(userId: String): DocumentReference {
        return  database.collection(USER_PATH).document(userId)
    }

    ////////////////// INTERACCIONES CON LA COLLECCION DATAMARKER //////////////////
    fun addMarker(marker: DataMarker){
        database.collection(MARKER_PATH).add(
            hashMapOf(
                "title" to marker.title,
                "subTitle" to marker.subTitle,
                "latitud" to marker.latitud,
                "longitud" to marker.longitud,
                "img" to marker.img
            )
        )
    }

    fun editMarker(editedMarker: DataMarker, markerId: String){
        database.collection(MARKER_PATH).document(markerId).set(
            hashMapOf(
                "title" to editedMarker.title,
                "subTitle" to editedMarker.subTitle,
                "latitud" to editedMarker.latitud,
                "longitud" to editedMarker.longitud,
                "img" to editedMarker.img
            )
        )
    }

    fun deleteMarker(userId: String){
        database.collection(MARKER_PATH).document(userId).delete()
    }

    //Interacciones con un documento de la colleccion users
    fun getMarkers(): CollectionReference {
        return database.collection(MARKER_PATH)
    }

    fun getMarker(markerId: String ): DocumentReference {
        return  database.collection(MARKER_PATH).document(markerId)
    }
}