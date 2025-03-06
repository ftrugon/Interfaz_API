package com.example.din_api.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import com.example.din_api.R
import com.example.din_api.navigation.AppScreen

@Composable
fun Welcome(modifier: Modifier,navController: NavController){


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable { navController.navigate(AppScreen.LoginScreen.route) }
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(R.drawable.welcomeimage),
            contentDescription = "foto de app",
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .size(300.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )

        Text(
            modifier = Modifier.padding(10.dp),
            text = "Welcome back!",
            fontWeight = FontWeight(1000),
            fontSize = 5.em,
            color = Color.Black)
    }

}