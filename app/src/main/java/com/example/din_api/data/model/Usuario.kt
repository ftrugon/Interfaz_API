package com.tareapi.model


data class Usuario(
    val _id : String?,
    val username: String,
    var password: String,
    val email: String,
    val direccion: Direccion,
    val roles: String? = "USER"
) {

}
