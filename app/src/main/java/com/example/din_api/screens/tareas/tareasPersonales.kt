package com.example.din_api.screens.tareas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.din_api.APIData
import com.example.din_api.navigation.AppScreen
import com.google.gson.JsonParser
import com.tareapi.model.Tarea
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareasPersonales(navController: NavController){

    // variables para almacenar tareas y texto de busqueda
    var tareas by remember { mutableStateOf(listOf<Tarea>()) }
    var searchText by remember { mutableStateOf("") }


    val tareasFiltradas = tareas.filter { tarea ->
        tarea.titulo.contains(searchText, ignoreCase = true)
    }

    // Traer las tareas de la api
    LaunchedEffect(Unit) {
        tareas = APIData.obtenerTareasDeUsuario().await()
    }

    // Scaffold y lo que se ve en la pantalla
    Scaffold(
        topBar = {
            Column {
                TopAppBar(title = { Text("Tareas Asignadas") })
                // el filtro
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text("Buscar tarea...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tareasFiltradas) { tarea ->
                    TareaItem(tarea, navController = navController)
                }
            }
        }
    }
}

@Composable
fun TareaItem(tarea: Tarea,navController: NavController) {

    val tareJson = Json.encodeToString(tarea)

    //val tareJson = "hola"

    Card(
        modifier = Modifier.fillMaxWidth().clickable{ navController.navigate("${AppScreen.TareaScreen.route}/$tareJson") },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = tarea.titulo.ifEmpty { "Sin título" },
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = tarea.texto.ifEmpty { "Sin descripción" },
                fontSize = 14.sp,
                color = Color.Gray
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (tarea.estado) "✅ Completada" else "⏳ Pendiente",
                    fontWeight = FontWeight.Medium,
                    color = if (tarea.estado) Color(0xFF4CAF50) else Color(0xFFE57373)
                )
                Text(
                    text = "ID: ${tarea._id}",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.End
                )

            }
            Text(
                text = "Usuario: ${tarea.usuario}",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.End
            )
        }
    }
}