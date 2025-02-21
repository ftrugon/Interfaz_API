package com.example.din_api.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.din_api.data.ApiService
import com.example.din_api.screens.Register.RegistroScreen
import com.example.din_api.screens.userScreen.UserScreen
import com.example.din_api.screens.welcome.Welcome
import com.example.din_api.viewmodel.LoginViewModel
import com.example.din_proyectoapp.screens.login.Login

@Composable
fun AppNavigation(modifier: Modifier,loginViewModel: LoginViewModel){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreen.WelcomeScreen.route
    ) {
        composable(AppScreen.WelcomeScreen.route) {
            Welcome(modifier,navController)
        }

        composable(AppScreen.UserScreen.route){
            UserScreen(modifier,navController)
        }

        composable(AppScreen.LoginScreen.route){
            Login(modifier,navController,loginViewModel)
        }

        composable(AppScreen.RegisterScreen.route) {
            RegistroScreen(navController)
        }

    }



}