package com.example.din_api.screens.userScreen

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
import androidx.navigation.NavController
import com.example.din_api.APIData
import com.example.din_api.navigation.AppScreen
import com.tareapi.model.Direccion
import com.tareapi.model.Usuario
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(modifier: Modifier = Modifier, navController: NavController) {
    var isAdmin by remember { mutableStateOf(false) }
    var userInfo by remember {
        mutableStateOf(
            Usuario("", "", "", "", Direccion("", "", "", "", ""), "")
        )
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                userInfo = APIData.userInfo().await()
                isAdmin = userInfo.roles == "ADMIN"
            } catch (e: Exception) {
                Log.e("UserScreen", "Error al obtener usuario: ${e.message}")
            }
        }
    }

    Scaffold(
        modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = userInfo.username, fontWeight = FontWeight.Bold) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Rol: ${userInfo.roles}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(24.dp))

            ButtonSection(navController, isAdmin)
        }
    }
}

@Composable
fun ButtonSection(navController: NavController, isAdmin: Boolean) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomButton("Ver tus tareas") {
            navController.navigate(AppScreen.TareasAsignadasScreen.route)
        }

        CustomButton("Ver tareas sin asignar") {
            navController.navigate(AppScreen.TareasSinAsignar.route)
        }

        if (isAdmin) {
            CustomButton("Ver todas las tareas") {
                navController.navigate(AppScreen.TodasTareasScreen.route)
            }
        }
    }
}

@Composable
fun CustomButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(50.dp)
    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}
