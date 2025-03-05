package com.example.din_api.screens.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.din_api.APIData
import com.example.din_api.navigation.AppScreen
import com.tareapi.dto.RegistrarUsuarioDTO
import com.tareapi.model.Direccion
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun registro(modifier:Modifier, navController: NavController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf(Direccion("", "", "", "", "")) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    fun onRegister() {
        coroutineScope.launch {
            if (password == passwordRepeat) {
                isLoading = true
                val usuarioDTO = RegistrarUsuarioDTO(username, email, password, passwordRepeat, direccion, "USER")
                try {
                    val (message, seHaRegistrado) = APIData.registrarUsuario(usuarioDTO).await()
                    if (seHaRegistrado) {
                        navController.navigate(AppScreen.LoginScreen.route)
                        errorMessage = null
                    } else {
                        errorMessage = message
                    }
                } catch (e: Exception) {
                    errorMessage = "Error al registrar: ${e.message}"
                } finally {
                    isLoading = false
                }
            } else {
                errorMessage = "Las contraseñas no coinciden."
            }
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "Crear cuenta",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Mensaje de error
                    errorMessage?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Campos de entrada
                    RegistroTextField("Nombre de usuario", username) { username = it }
                    RegistroTextField("Correo electrónico", email) { email = it }
                    RegistroTextField("Contraseña", password, isPassword = true) { password = it }
                    RegistroTextField("Repite la contraseña", passwordRepeat, isPassword = true) { passwordRepeat = it }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Dirección",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray
                    )

                    RegistroTextField("Municipio", direccion.municipio) { direccion = direccion.copy(municipio = it) }
                    RegistroTextField("Provincia", direccion.provincia) { direccion = direccion.copy(provincia = it) }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón de registro
                    Button(
                        onClick = { onRegister() },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !isLoading
                    ) {
                        Text(
                            text = if (isLoading) "Registrando..." else "Registrarse",
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(onClick = { navController.navigate(AppScreen.LoginScreen.route) }) {
                        Text("¿Ya tienes cuenta? Inicia sesión")
                    }
                }
            }
        }
    }
}

@Composable
fun RegistroTextField(label: String, value: String, isPassword: Boolean = false, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
}
