package com.example.din_api

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.din_api.data.RetrofitServiceFactory
import com.example.din_api.data.dto.UsuarioLoginDTO
import com.example.din_api.navigation.AppNavigation
import com.example.din_api.ui.theme.DIN_APITheme
import com.example.din_api.viewmodel.LoginViewModel
import com.tareapi.dto.RegistrarUsuarioDTO
import com.tareapi.model.Direccion
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


//        APIData.registrarUsuario(RegistrarUsuarioDTO(
//            "paco",
//            "Paconil@gmail.com",
//            "123456",
//            "123456",
//            Direccion("tal","tal","chiclana de la frontera","cádiz","11139"),
//            "USER"
//        ))




        val loginViewModel = LoginViewModel()
        setContent {
            DIN_APITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding),loginViewModel)
                }
            }
        }
    }
}
