package com.example.appmov.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appmov.navigation.Routes
import com.example.appmov.ui.theme.Amarillomostaza
import com.example.appmov.ui.theme.Amarillopalido
import com.example.appmov.ui.theme.Azuloscuro
import com.example.appmov.ui.theme.Negrocool
import okhttp3.Route

@Composable
fun ItemNoticia(navController: NavController){

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(Amarillopalido)
            .padding(12.dp)
    ){
        Text("Noticias Financieras", fontSize = 18.sp, color = Negrocool)
        Spacer(modifier = Modifier.height(6.dp))
        FragmentView(navController = navController)
        Text(
            text = "Ejemplo de ViewGroup creado en un archivo externo al Homescreen, CONSUMO DE API (Mejora Pendiente)",
            color = Negrocool,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 20.dp)
        )

    }
}
@Composable
fun FragmentView (navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Azuloscuro)
            .padding(12.dp)
    ) {
        Text("Accede aqui a nuestro portal de noticias economicas y enterate de los mayores impactos en el mercado", fontSize = 16.sp, color = Amarillomostaza)
        Button(
            onClick = {navController.navigate(Routes.NOTICIAS)} ,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Portal FINANCIERO")
        }
    }
}