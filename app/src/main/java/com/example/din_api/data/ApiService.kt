package com.example.din_api.data

import com.example.din_api.data.dto.UsuarioLoginDTO
import com.example.din_api.data.model.LoginResponse
import com.tareapi.dto.RegistrarUsuarioDTO
import com.tareapi.dto.UsuarioDTO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {


    @POST("/users/login")
    suspend fun login(@Body usuarioLoginDTO: UsuarioLoginDTO): LoginResponse

    @POST("/users/register")
    suspend fun register(@Body registrarUsuarioDTO: RegistrarUsuarioDTO): UsuarioDTO
}