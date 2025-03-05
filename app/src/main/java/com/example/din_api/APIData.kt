package com.example.din_api

import android.util.Log
import com.example.din_api.data.ApiService
import com.example.din_api.data.RetrofitServiceFactory
import com.example.din_api.data.dto.UsuarioLoginDTO
import com.tareapi.dto.InsertarTareaDTO
import com.tareapi.dto.RegistrarUsuarioDTO
import com.tareapi.dto.TareaDTO
import com.tareapi.model.Tarea
import com.tareapi.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

object APIData {
    var Actualtoken:String = ""

    var userData: Usuario? = null

    val apiService = RetrofitServiceFactory.retrofitService

    fun getToken(username:String,password:String):Deferred<Pair<String,Boolean>>{

        return CoroutineScope(Dispatchers.IO).async {
            try {
                val token = apiService.login(UsuarioLoginDTO(username,"",password))
                Log.i("token", "Token recibido: ${token.token}")
                Actualtoken = token.token
                return@async Pair(token.token,true)

            } catch (e: Exception) {
                Log.e("token", "Error en la solicitud: ${e.message}")
                return@async Pair("${e.message}",false)
            }
        }

    }

    fun registrarUsuario(registrarUsuarioDTO: RegistrarUsuarioDTO):Deferred<Pair<String,Boolean>>{

        return CoroutineScope(Dispatchers.IO).async {
            try {
                val user = apiService.register(registrarUsuarioDTO)
                Log.e("USUARIO","$user")
                return@async Pair("",true)
            }catch (e:Exception){
                Log.e("token", "Error en la solicitud: ${e.message}")
                return@async Pair("${e.message}",false)
            }
        }
    }


    fun userInfo():Deferred<Usuario>{
        return CoroutineScope(Dispatchers.IO).async {
            val user = apiService.getInfo("Bearer $Actualtoken")
            userData = user
            return@async user
        }
    }

    fun crearTarea(insertarTareaDTO: InsertarTareaDTO):Deferred<Pair<String,Boolean>>{
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val tarea = apiService.crearTarea("Bearer $Actualtoken",insertarTareaDTO)
                Log.e("TAREA","$tarea")
                return@async Pair("",true)
            }catch (e:Exception){
                Log.e("token", "Error en la solicitud: ${e.message}")
                return@async Pair("${e.message}",false)
            }
        }
    }

    fun obtenerTareasDeUsuario():Deferred<List<Tarea>>{
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val tareas = apiService.getTareasPersonales("Bearer $Actualtoken")
                return@async tareas
            }catch (e:Exception){
                Log.e("token", "Error en la solicitud: ${e.message}")
                return@async listOf()
            }
        }
    }

    fun obtenerTodasLasTareas():Deferred<List<Tarea>>{
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val tareas = apiService.getTodasTareas("Bearer $Actualtoken")
                return@async tareas
            }catch (e:Exception){
                Log.e("token", "Error en la solicitud: ${e.message}")
                return@async listOf()
            }
        }
    }


    fun obtenerTareasSinAsignar():Deferred<List<Tarea>>{
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val tareas = apiService.getTareasSinAsignar("Bearer $Actualtoken")
                return@async tareas
            }catch (e:Exception){
                Log.e("token", "Error en la solicitud: ${e.message}")
                return@async listOf()
            }
        }
    }


    fun asignarseTarea(tareaId:String):Deferred<Pair<String,Boolean>>{
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val tarea = apiService.darAltaTarea("Bearer $Actualtoken",tareaId)
                Log.e("TAREA","$tarea")
                return@async Pair("",true)
            }catch (e:Exception){
                Log.e("token", "Error en la solicitud: ${e.message}")
                return@async Pair("${e.message}",false)
            }
        }
    }

    fun asignarTareaAUsuario(username: String,tareaId:String):Deferred<Pair<String,Boolean>>{
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val tarea = apiService.darAltaTareaAdmin("Bearer $Actualtoken",username,tareaId)
                Log.e("TAREA","$tarea")
                return@async Pair("",true)
            }catch (e:Exception){
                Log.e("token", "Error en la solicitud: ${e.message}")
                return@async Pair("${e.message}",false)
            }
        }
    }

    fun modTarea(tareaId:String,insertarTareaDTO: InsertarTareaDTO):Deferred<Tarea>{
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val tarea = apiService.modTarea("Bearer $Actualtoken",tareaId,insertarTareaDTO)
                Log.e("TAREA","$tarea")
                return@async tarea
            }catch (e:Exception){
                Log.e("token", "Error en la solicitud: ${e.message}")
                return@async Tarea(null,"Error inesperado","",false,"","","")
            }
        }
    }

    fun completarTarea(tareaId:String):Deferred<Pair<String,Boolean>>{
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val tarea = apiService.completarTarea("Bearer $Actualtoken",tareaId)
                Log.e("TAREA","$tarea")
                return@async Pair("",true)
            }catch (e:Exception){
                Log.e("token", "Error en la solicitud: ${e.message}")
                return@async Pair("${e.message}",false)
            }
        }
    }

    fun desmarcarTarea(tareaId:String):Deferred<Pair<String,Boolean>>{
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val tarea = apiService.desmarcarTarea("Bearer $Actualtoken",tareaId)
                Log.e("TAREA","$tarea")
                return@async Pair("",true)
            }catch (e:Exception){
                Log.e("token", "Error en la solicitud: ${e.message}")
                return@async Pair("${e.message}",false)
            }
        }
    }

    fun eliminarTarea(tareaId:String):Deferred<Pair<String,Boolean>>{
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val tarea = apiService.deleteTarea("Bearer $Actualtoken",tareaId)
                Log.e("TAREA","$tarea")
                return@async Pair("",true)
            }catch (e:Exception){
                Log.e("token", "Error en la solicitud: ${e.message}")
                return@async Pair("${e.message}",false)
            }
        }
    }
}