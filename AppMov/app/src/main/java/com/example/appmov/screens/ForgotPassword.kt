package com.example.appmov.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appmov.R

@Composable
fun ForgotPasswordScreen (
    onBack: () -> Unit,
    onSendMail: () ->Unit,
)
{
    var email by remember { mutableStateOf("") }



    Scaffold(

        /************************************TOPBAR*********************************/
        topBar = {
            SimpleTopbar(
                onBack = onBack,
                title = "¿Olvidaste tu contraseña?",
                showBack = true
            )
        }
    ) { padding ->
        // Imagen de fondo ------------------------------
        Image(
            painter = painterResource(id = R.drawable.fondo_dif), // coloca tu imagen en res/drawable
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
           TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )


            Button(onClick = {
                onSendMail()

            }, modifier = Modifier.fillMaxWidth()) {
                Text("Enviar instrucciones", fontSize = 13.sp)
            }
        }
    }
}