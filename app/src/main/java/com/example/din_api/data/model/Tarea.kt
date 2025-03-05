package com.tareapi.model

import android.os.Parcelable
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Tarea (
    val _id : String?,
    var titulo: String,
    var texto: String,
    var estado: Boolean,
    var fecha_inicio: String,
    var fecha_final: String?,
    var usuario: String?
)