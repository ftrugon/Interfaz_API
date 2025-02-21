package com.tareapi.dto

import com.tareapi.model.Direccion


class RegistrarUsuarioDTO (
    val username: String,
    val email: String,
    val password: String,
    val passwordRepeat: String,
    val direccion: Direccion,
    val rol: String?
)