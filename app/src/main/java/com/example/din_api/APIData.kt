package com.example.din_api

import android.util.Log
import com.example.din_api.data.ApiService
import com.example.din_api.data.RetrofitServiceFactory
import com.example.din_api.data.dto.UsuarioLoginDTO
import com.tareapi.dto.RegistrarUsuarioDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

object APIData {
    var Actualtoken:String = ""

    val apiService = RetrofitServiceFactory.retrofitService

    fun getToken(username:String,password:String):String?{


        var tokenToReturn:String? = ""
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val token = apiService.login(UsuarioLoginDTO(username,"",password))
                Log.i("token", "Token recibido: ${token.token}")
                tokenToReturn = token.token
                Actualtoken = token.token
            } catch (e: Exception) {
                Log.e("token", "Error en la solicitud: ${e.message}")
                tokenToReturn = null
            }
        }

        while (tokenToReturn == ""){
        }

        return tokenToReturn
    }


    fun registrarUsuario(registrarUsuarioDTO: RegistrarUsuarioDTO):Boolean{

        var seHaRegistrado:Boolean? = null

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = apiService.register(registrarUsuarioDTO)
                Log.e("USUARIO","$user")
                seHaRegistrado = true
            }catch (e:Exception){
                Log.e("Error","$e")
                seHaRegistrado = false
            }
        }

        while (seHaRegistrado == null){

        }



        getToken(registrarUsuarioDTO.username,registrarUsuarioDTO.password)

        return seHaRegistrado!!
    }

}