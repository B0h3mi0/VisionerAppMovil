package com.example.appmov.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appmov.R
import com.example.appmov.data.RegistroDbHelper
import com.example.appmov.utils.RegistroUtils

@Composable
fun LoginScreen(
    onHomeClick: () -> Unit
) { // *** SCREEN secundaria, FORMULARIO INGRESO DE USUARIO ***
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) { //////////////Inicio del BOX//////////////////

        // Imagen de fondo ------------------------------
        Image(
            painter = painterResource(id = R.drawable.fondo_dif), // coloca tu imagen en res/drawable
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier // * Modificacion de la columna *
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {  //////////////////// Inicio de COLUMNA ///////////////////////
            // Encabezado
            Text("Iniciar Sesion",
                fontSize = 35.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
            )
            Spacer(modifier = Modifier.height(50.dp))
            // Usuario -------------------------------
            TextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Ingrese correo") },
                modifier = Modifier.fillMaxWidth()
            )

            // Contraseña ------------------------------
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier

                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )

            // Botón INICIAR SESION -- Login ---------------------------------
            Button(
                onClick = {
                    RegistroDbHelper.autenticar(context, correo, password) { res ->
                        if (res.ok) {
                            onHomeClick() // navega a la Home o siguiente pantalla
                        } else {
                            Toast.makeText(context, res.mensaje, Toast.LENGTH_LONG).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Iniciar Sesión",
                    style = MaterialTheme.typography.bodyLarge)
            }

            // OLVIDASTE TU CONTRASEÑA --  Recuperar clave--------------------------------------
            Text(
                text = "¿Olvidaste tu contraseña?",
                color = Color.Blue,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clickable {
                        Toast.makeText(context, "Recuperar clave (pendiente)", Toast.LENGTH_SHORT)
                            .show()
                    }
            )
        } //////////////////// Fin de COLUMNA ///////////////////////
    }
}
