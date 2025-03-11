package com.example.din_api.screens.tareas

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.tareapi.model.Tarea
import kotlinx.serialization.json.Json
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.din_api.APIData
import com.example.din_api.navigation.AppScreen
import com.example.din_proyectoapp.screens.login.ErrorMessage
import com.tareapi.dto.InsertarTareaDTO
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaScreen(tareaString: String, navController: NavController) {
    val tarea = Json.decodeFromString<Tarea>(tareaString)
    var isAdmin = APIData.userData!!.roles == "ADMIN"

    // Vista dentro de una tarea
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Detalles de la Tarea", fontWeight = FontWeight.Bold) })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = tarea.titulo,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = tarea.texto,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(16.dp))

            ButtonSection(navController, tarea, isAdmin)
        }
    }
}

// seccion de los botones, se controla la aparicion de los botones en cada tarea dependiendo si eres admin, si tienes la tarea asignada , etc
@Composable
fun ButtonSection(navController: NavController, tarea: Tarea, isAdmin: Boolean) {

    val coroutineScope = rememberCoroutineScope()
    val isError by remember { mutableStateOf(false) }
    var errorMesaage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var mostrarDialogo by remember { mutableStateOf(false) }
    var mostrarDialogoAsignarAUsuario by remember { mutableStateOf(false) }


    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        if (isLoading){
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        if (isError){
            ErrorMessage(errorMesaage)
        }

        if (tarea.usuario == ""){
            CustomButton("Asignarme esta tarea") {
                coroutineScope.launch {
                    isLoading = true
                    APIData.asignarseTarea(tarea._id!!).await()
                    isLoading = false
                    navController.popBackStack()
                }
            }
        }

        if (isAdmin || APIData.userData!!.username == tarea.usuario){
            CustomButton("Modificar tarea") {
                mostrarDialogo = true
            }

            if (mostrarDialogo) {
                EditarTareaDialog(
                    tarea = tarea,
                    onDismiss = { mostrarDialogo = false },
                    onConfirm = { nuevoTitulo, nuevaDescripcion ->
                        // Aquí puedes hacer la lógica de actualización
                        mostrarDialogo = false
                        coroutineScope.launch {
                            isLoading = true
                            val newTarea = APIData.modTarea(tarea._id!!, InsertarTareaDTO(nuevoTitulo,nuevaDescripcion)).await()
                            isLoading = false

                            val tareJson = Json.encodeToString(newTarea)
                            navController.navigate("${AppScreen.TareaScreen.route}/$tareJson")
                        }
                    }
                )
            }

            CustomButton("Eliminar tarea", color = Color.Red) {
                coroutineScope.launch {
                    isLoading = true
                    APIData.eliminarTarea(tarea._id!!).await()
                    isLoading = false
                    navController.navigate(AppScreen.TareasAsignadasScreen.route)
                }
            }
        }

        if (APIData.userData!!.username == tarea.usuario){

            if (!tarea.estado){
                CustomButton("Completar Tarea") {
                    coroutineScope.launch {
                        isLoading = true
                        APIData.completarTarea(tarea._id!!).await()
                        isLoading = false
                        navController.navigate(AppScreen.TareasAsignadasScreen.route)
                    }
                }
            }else {
                CustomButton("Desmarcar como completa la tarea") {
                    coroutineScope.launch {
                        isLoading = true
                        APIData.desmarcarTarea(tarea._id!!).await()
                        isLoading = false
                        navController.popBackStack()
                    }
                }
            }
        }


        if (isAdmin && tarea.usuario == "") {
            CustomButton("Asignar a otro usuario") {
                mostrarDialogoAsignarAUsuario = true
            }

            if (mostrarDialogoAsignarAUsuario) {
                AsignarTareaDialog(
                    tarea = tarea,
                    onDismiss = { mostrarDialogoAsignarAUsuario = false },
                    onConfirm = { usuario ->
                        // Lógica para asignar la tarea al usuario ingresado
                        coroutineScope.launch {
                            isLoading = true
                            APIData.asignarTareaAUsuario(usuario,tarea._id!!).await()
                            isLoading = false
                            navController.popBackStack()
                        }

                    }
                )
            }
        }



    }
}

@Composable
fun CustomButton(text: String, color: Color = MaterialTheme.colorScheme.primary, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(50.dp)
    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

// dialogo para editar una tarea
@Composable
fun EditarTareaDialog(
    tarea: Tarea,
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var titulo by remember { mutableStateOf(tarea.titulo) }
    var descripcion by remember { mutableStateOf(tarea.texto) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Tarea") },
        text = {
            Column {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") }
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") }
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(titulo, descripcion) }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}


// dialogo para asignar una tarea
@Composable
fun AsignarTareaDialog(
    tarea: Tarea,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var usuarioAsignado by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Asignar Tarea") },
        text = {
            Column {
                OutlinedTextField(
                    value = usuarioAsignado,
                    onValueChange = { usuarioAsignado = it },
                    label = { Text("Usuario ID o Nombre") }
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(usuarioAsignado) }) {
                Text("Asignar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}