package com.example.maps_app.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.maps_app.model.Repository
import com.example.maps_app.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange

class ViewModelFireBase: ViewModel() {

    private val repository = Repository()

    private var _userList = MutableLiveData( emptyList<User>() )
    val userList = _userList

    private var _actualUser = MutableLiveData( User() )
    val actualUser = _actualUser

    private var _goToNext = MutableLiveData( false )
    val goToNext = _goToNext

    private val auth = FirebaseAuth.getInstance()

    private var _userLogin = MutableLiveData( User() )
    val userLogin = _userLogin

    fun register( username: String, password: String ){
        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    _goToNext.value = true
                } else {
                    _goToNext.value = false
                    Log.e("Error", "Error creating user: ${it.result}")
                }
            }
    }

    fun login( username: String?, password: String? ){
        auth.signInWithEmailAndPassword(username!!, password!!)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    getUser( it.result.user?.uid!! )
                    if (_actualUser.value != null){
                        _userLogin.value = _actualUser.value
                        _goToNext.value = true
                    }
                } else {
                    _goToNext.value = false
                    Log.e("Error", "Error signing in: ${it.result}")
                }
            }
    }

    fun getUsers() {
        repository.getUsers().addSnapshotListener{ value, error ->
            if (error != null){
                Log.e("Firestore error", error.message.toString())
            }
            val tempList = mutableListOf<User>()

            for (dc: DocumentChange in value?.documentChanges!!){
                if (dc.type == DocumentChange.Type.ADDED){
                    val newUser = dc.document.toObject(User::class.java)
                    newUser.userId = dc.document.id
                    tempList.add(newUser)
                }
            }
            _userList.value = tempList
        }
    }

    fun getUser(userId: String) {
        repository.getUser(userId).addSnapshotListener{ value, error ->
            if (error != null){
                Log.e("UserRepository","Listen failed", error)
                return@addSnapshotListener
            }

            if ( value != null && value.exists() ){
                val user = value.toObject(User::class.java)
                if (user != null){
                    user.userId = userId
                }
                _actualUser.value = user
            } else {
                Log.d("UserRepository","Current data: null")
            }
        }
    }

}

