package com.example.maps_app.view.LoginView

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.maps_app.R
import com.example.maps_app.ViewModel.MyViewModel
import com.example.maps_app.model.Navigation.Routes

@Composable
fun ScreenLogin(navigation: NavHostController, myViewModel: MyViewModel) {

    val tamañoLetra = 40

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painterResource(id = R.drawable.icon_usuario) ,
            contentDescription = "" ,
            modifier = Modifier
                .fillMaxSize(0.6f)
        )

        Button(
            onClick = { navigation.navigate(Routes.PantallaMapa.route) },
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(Color.DarkGray),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .border(5.dp, Color.Black)
        ) {
            Text(
                text = "LOGIN",
                fontWeight = FontWeight.Black,
                fontSize = tamañoLetra.sp
            )
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Button(
            onClick = { navigation.navigate(Routes.PantallaMapa.route) },
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(Color.DarkGray),
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .fillMaxWidth(0.6f)
                .border(5.dp, Color.Black)
        ) {
            Text(
                text = "Register",
                fontWeight = FontWeight.Black,
                fontSize = (tamañoLetra * 2/5).sp
            )
        }
    }

}