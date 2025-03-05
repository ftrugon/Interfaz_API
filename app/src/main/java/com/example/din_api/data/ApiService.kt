package com.example.din_api.data

import com.example.din_api.data.dto.UsuarioLoginDTO
import com.example.din_api.data.model.LoginResponse
import com.tareapi.dto.InsertarTareaDTO
import com.tareapi.dto.RegistrarUsuarioDTO
import com.tareapi.dto.TareaDTO
import com.tareapi.dto.UsuarioDTO
import com.tareapi.model.Tarea
import com.tareapi.model.Usuario
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {


    @POST("/users/login")
    suspend fun login(@Body usuarioLoginDTO: UsuarioLoginDTO): LoginResponse

    @POST("/users/register")
    suspend fun register(@Body registrarUsuarioDTO: RegistrarUsuarioDTO): UsuarioDTO

    @GET("/users/getInfo")
    suspend fun getInfo(@Header("Authorization") authToken: String): Usuario


    @POST("/tareas/crearTarea")
    suspend fun crearTarea(
        @Header("Authorization") authToken: String,
        @Body tareaDTO: InsertarTareaDTO): TareaDTO

    @GET("/tareas/obtenerTareas")
    suspend fun getTareasPersonales(@Header("Authorization") authToken: String): List<Tarea>

    @GET("/tareas/obtenerTodasTareas")
    suspend fun getTodasTareas(@Header("Authorization") authToken: String): List<Tarea>

    @GET("/tareas/obtenerTareasSinAsignar")
    suspend fun getTareasSinAsignar(@Header("Authorization") authToken: String): List<Tarea>

    @PUT("/tareas/asignarTarea/{tareaId}")
    suspend fun darAltaTarea(
        @Header("Authorization") authToken: String,
        @Path("tareaId") tareaId :String
    )

    @PUT("/tareas/asignarTareaAUsuario/{username}/{tareaId}")
    suspend fun darAltaTareaAdmin(
        @Header("Authorization") authToken: String,
        @Path("username") username :String,
        @Path("tareaId") tareaId :String
    ): Tarea

    @PUT("/tareas/modTarea/{tareaId}")
    suspend fun modTarea(
        @Header("Authorization") authToken: String,
        @Path("tareaId") tareaId :String,
        @Body tareaDTO: InsertarTareaDTO
    ): Tarea

    @PUT("/tareas/completarTarea/{tareaId}")
    suspend fun completarTarea(
        @Header("Authorization") authToken: String,
        @Path("tareaId") tareaId :String
    ): Tarea

    @PUT("/tareas/desmarcarTarea/{tareaId}")
    suspend fun desmarcarTarea(
        @Header("Authorization") authToken: String,
        @Path("tareaId") tareaId :String
    ): Tarea

    @DELETE("/tareas/eliminarTarea/{tareaId}")
    suspend fun deleteTarea(
        @Header("Authorization") authToken: String,
        @Path("tareaId") tareaId :String
    ): Tarea

}