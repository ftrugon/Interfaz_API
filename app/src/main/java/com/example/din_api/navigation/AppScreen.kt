package com.example.din_api.navigation

sealed class AppScreen(val route:String) {

    object WelcomeScreen:AppScreen("WelocomeScreen")
    object UserScreen:AppScreen("UserScreen")
    object LoginScreen:AppScreen("LoginScreen")
    object RegisterScreen:AppScreen("RegisterScreen")
    object TareasAsignadasScreen:AppScreen("TareasAsignadas")
    object TodasTareasScreen:AppScreen("TodasTareas")
    object TareasSinAsignar:AppScreen("TareasSinAsignar")
    object AddTareaScreen:AppScreen("AddTareaScreen")
    object TareaScreen:AppScreen("TareaScreen")

}