package com.example.din_api.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf

class LoginViewModel() {

    private var _canLogIn = mutableStateOf(false)
    var canLogIn = _canLogIn

    private var _user = mutableStateOf("")
    val user = _user

    private val _pass = mutableStateOf("")
    val pass = _pass



    fun changeUser(newValue: String){
        _user.value = newValue
    }

    fun changePass(newValue: String){
        _pass.value = newValue
    }

    fun resetUserAndPass(){
        _pass.value = ""
        _user.value = ""
        _canLogIn.value = false
    }

    fun checkLogin(){
        _canLogIn.value = validateMail() && validatePass()
    }

    private fun validateMail():Boolean{
        return _user.value.isNotEmpty()
    }

    private fun validatePass():Boolean{
        return _pass.value.length >= 6
    }


}