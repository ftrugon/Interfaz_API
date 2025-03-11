package com.example.din_proyectoapp.screens.login

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.input.VisualTransformation
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log


@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun Login(
    modifier: Modifier,
    navController: NavController,
    viewModel: LoginViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val username by viewModel.user
    val password by viewModel.pass
    val canLogIn by viewModel.canLogIn

    var isCharging by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isCharging) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            if (showError) {
                ErrorMessage(errorMessage)
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFCDCDCD))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    UsernameField("Username",username,{
                        viewModel.changeUser(it)
                        viewModel.checkLogin()
                    }, Constantes.textFieldColorValues())
                    PasswordField(password,{
                        viewModel.changePass(it)
                        viewModel.checkLogin()
                    },Constantes.textFieldColorValues())
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = {
                    coroutineScope.launch {
                        isCharging = true
                        val (token, success) = APIData.getToken(username, password).await()
                        isCharging = false
                        if (success) {
                            navController.navigate(AppScreen.UserScreen.route)
                        } else {
                            if (token.contains("401")){
                                errorMessage = "Login incorrecto"
                            }else{
                                errorMessage = "Error en la conexion"
                            }

                            showError = true
                        }
                    }
                },
                enabled = canLogIn,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = if (isCharging) "Cargando..." else "Iniciar sesión")
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = { navController.navigate(AppScreen.RegisterScreen.route) }) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }
    }
}

@Composable
fun ErrorMessage(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(8.dp)
    )
}

