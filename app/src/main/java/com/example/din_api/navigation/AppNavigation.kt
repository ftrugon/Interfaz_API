package com.example.din_api.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.din_api.screens.register.registro
import com.example.din_api.screens.tareas.AnadirTarea
import com.example.din_api.screens.tareas.TareaScreen
import com.example.din_api.screens.tareas.TareasPersonales
import com.example.din_api.screens.tareas.TodasLasTareas
import com.example.din_api.screens.tareas.TodasTareasSinAsignar
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
            registro(modifier,navController)
        }

        composable(AppScreen.TareasAsignadasScreen.route){
            TareasPersonales(navController)
        }

        composable(AppScreen.TodasTareasScreen.route) {
            TodasLasTareas(navController)
        }

        composable(AppScreen.TareasSinAsignar.route) {
            TodasTareasSinAsignar(navController)
        }

        composable(AppScreen.AddTareaScreen.route) {
            AnadirTarea(navController)
        }

        composable(AppScreen.TareaScreen.route + "/{tarea}",
            arguments = listOf(navArgument(name = "tarea"){type = NavType.StringType})
            ) {
            val tarea = it.arguments?.getString("tarea")
            TareaScreen(tarea!!,navController)
        }

    }



}