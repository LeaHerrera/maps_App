package com.example.maps_app.model

class User (){
    var userId: String? = null
    var userName: String = ""
    var age: Int = 0
    var profilePicture: String? = null

    constructor(userId: String?, userName: String, age: Int, profilePicture: String?):this(){
        this.userId = userId
        this.userName = userName
        this.age = age
        this.profilePicture = profilePicture
    }
}