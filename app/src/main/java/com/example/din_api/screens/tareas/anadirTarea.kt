package com.example.din_api.screens.tareas

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.din_api.APIData
import com.example.din_api.navigation.AppScreen
import com.example.din_proyectoapp.screens.login.ErrorMessage
import com.tareapi.dto.InsertarTareaDTO
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnadirTarea(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var errorMensage by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }


    // vista para añadir una tarea
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Añadir Tarea") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->

        if (error){
            ErrorMessage(errorMensage)
        }

        if (isLoading){
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            // boton para usar la api
            Button(
                onClick = {
                    if (titulo.isBlank()){
                        errorMensage = "El titulo no puede estar vacio"
                    }else if (descripcion.isBlank()){
                        errorMensage = "La descripcion no puede estar vacio"
                    }else{
                        coroutineScope.launch {
                            isLoading = true
                            val (msg,succes) = APIData.crearTarea(InsertarTareaDTO(titulo,descripcion)).await()
                            isLoading = false

                            if (succes){
                                navController.navigate(AppScreen.TareasSinAsignar.route)
                            }else{
                                errorMensage = msg
                                error = true
                            }

                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = titulo.isNotBlank() && descripcion.isNotBlank()
            ) {
                Text("Guardar Tarea")
            }
        }
    }
}
