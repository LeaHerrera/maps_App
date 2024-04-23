package com.example.maps_app.model

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class Repository {

    private val database = FirebaseFirestore.getInstance()

    //INTERACCIONES CON LA COLLECCION USER
    fun addUser(user: User){
        database.collection("users").add(
            hashMapOf(
                "userName" to user.userName,
                "age" to user.age,
                "profilePicture" to user.profilePicture
            )
        )
    }

    fun editUser(editedUser: User){
        database.collection("users").document(editedUser.userId!!).set(
            hashMapOf(
                "userName" to editedUser.userName,
                "age" to editedUser.age,
                "profilePicture" to editedUser.profilePicture
            )
        )
    }

    fun deleteUser(userId: String){
        database.collection("users").document(userId).delete()
    }

        //Interacciones con un documento de la colleccion users
    fun getUsers(): CollectionReference {
        return database.collection("users")
    }

    fun getUser(): DocumentReference {
        return  database.collection("user")
    }
}