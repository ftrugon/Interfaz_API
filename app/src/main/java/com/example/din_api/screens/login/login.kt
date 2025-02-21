package com.example.din_proyectoapp.screens.login

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
){
//fun Login(){

    val username by viewModel.user
    val password by viewModel.pass
    val canLogIn by viewModel.canLogIn

    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



        if (showError){
            ShowError("Any of the fields are incorrect or the user does not exist in the database")
        }


        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFCDCDCD)),
        ) {

            UsernameField("Username",username,{
                viewModel.changeUser(it)
                viewModel.checkLogin()
            }, Constantes.textFieldColorValues())

            PasswordField(password,{
                viewModel.changePass(it)
                viewModel.checkLogin()
            },Constantes.textFieldColorValues())

        }

        LoginButton("Log In!",canLogIn){
            val token = APIData.getToken(username,password)
            print("hola")
            if (token != null){
                navController.navigate(AppScreen.UserScreen.route)
            }else{
                showError = true
            }

        }


        TextButton(onClick =
        {
            navController.navigate(AppScreen.RegisterScreen.route)
        }) {
            Text(
                text = "¿No estás registrado?",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }

}

@Composable
fun ShowError(text:String){
    Column(
        modifier = Modifier
            .padding(50.dp)
            //.background(Color.)
            .border(BorderStroke(2.dp,Color.Red))
            .clip(RoundedCornerShape(16.dp))
    ) {
        Text(text,
            color = Color.Red,
            modifier = Modifier.padding(20.dp))
    }
}