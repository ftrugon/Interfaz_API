package com.example.din_api.screens.Register

import com.example.din_proyectoapp.screens.login.LoginButton
import com.example.din_proyectoapp.screens.login.PasswordField
import com.example.din_proyectoapp.screens.login.UsernameField
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.din_api.APIData
import com.example.din_api.data.ApiService
import com.example.din_api.data.dto.UsuarioLoginDTO
import com.example.din_api.data.model.LoginResponse
import com.example.din_api.navigation.AppScreen
import com.example.din_api.ui.theme.Constantes
import com.example.din_api.viewmodel.LoginViewModel
import com.tareapi.dto.RegistrarUsuarioDTO
import com.tareapi.model.Direccion
import kotlinx.coroutines.launch

@Composable
fun RegistroScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf(Direccion("", "", "", "", "")) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Función para manejar el registro
    fun onRegister() {
        coroutineScope.launch {
            if (password == passwordRepeat) {
                isLoading = true
                val usuarioDTO = RegistrarUsuarioDTO(
                    username,
                    email,
                    password,
                    passwordRepeat,
                    direccion,
                    "USER"
                )
                try {
                    val seHaRegistrado = APIData.registrarUsuario(usuarioDTO)
                    if (seHaRegistrado) {

                        navController.navigate(AppScreen.UserScreen.route)
                        errorMessage = null
                        // Aquí podrías navegar a otra pantalla o mostrar un mensaje de éxito
                    } else {
                        // Error en el registro
                        errorMessage = "No se pudo registrar el usuario. Intenta nuevamente."
                    }
                    isLoading = false
                } catch (e: Exception) {
                    errorMessage = "Error al registrar el usuario: ${e.message}"
                    isLoading = false
                }
            } else {
                // Las contraseñas no coinciden
                errorMessage = "Las contraseñas no coinciden."
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Registro", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(bottom = 16.dp))

        // Campos de entrada
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = passwordRepeat,
            onValueChange = { passwordRepeat = it },
            label = { Text("Repite la contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Dirección
        Text("Dirección", style = MaterialTheme.typography.labelSmall)

        OutlinedTextField(
            value = direccion.calle,
            onValueChange = { direccion = direccion.copy(calle = it) },
            label = { Text("Calle") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = direccion.num,
            onValueChange = { direccion = direccion.copy(num = it) },
            label = { Text("Número") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = direccion.municipio,
            onValueChange = { direccion = direccion.copy(municipio = it) },
            label = { Text("Municipio") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = direccion.provincia,
            onValueChange = { direccion = direccion.copy(provincia = it) },
            label = { Text("Provincia") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = direccion.cp,
            onValueChange = { direccion = direccion.copy(cp = it) },
            label = { Text("Código Postal") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Mensaje de error
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Botón de registro
        Button(
            onClick = { onRegister() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Registrando..." else "Registrarse")
        }
    }
}
