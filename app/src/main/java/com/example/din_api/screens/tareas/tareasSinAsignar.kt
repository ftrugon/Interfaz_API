package com.example.din_api.screens.tareas

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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
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
import com.tareapi.model.Tarea
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodasTareasSinAsignar(navController: NavController){
//
//    var tareas = listOf(
//        Tarea("1", "Tarea 1", "Descripción breve de la tarea", false, Date(), Date(), ""),
//        Tarea("2", "Revisar reportes", "Analizar los datos del último informe", true, Date(), Date(), ""),
//        Tarea("3", "Comprar insumos", "Comprar papel y tinta para la oficina", false, Date(), Date(), ""),
//        Tarea("4", "Llamar al proveedor", "Negociar nuevos precios", false, Date(), Date(), ""),
//        Tarea("5", "Actualizar documentos", "Revisar y actualizar las políticas internas", true, Date(), Date(), "")
//    )

    var tareas by remember { mutableStateOf(listOf<Tarea>()) }
    var searchText by remember { mutableStateOf("") }


    val tareasFiltradas = tareas.filter { tarea ->
        tarea.titulo.contains(searchText, ignoreCase = true) ||
                tarea.titulo.contains(searchText, ignoreCase = true)
    }

    LaunchedEffect(Unit) {

        tareas = APIData.obtenerTareasDeUsuario().await()
    }


    Scaffold(
        topBar = {
            Column {
                TopAppBar(title = { Text("Tareas sin asignar") })
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text("Buscar tarea...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(AppScreen.AddTareaScreen.route)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text("+", style = MaterialTheme.typography.headlineMedium)
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
