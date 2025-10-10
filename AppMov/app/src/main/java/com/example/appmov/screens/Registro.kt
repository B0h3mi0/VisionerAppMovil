package com.example.appmov.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appmov.R
import com.example.appmov.data.RegistroDbHelper
import com.example.appmov.utils.RegistroUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    onRegistroClick: () -> Unit = {},
    onBack: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }


    // ComboBox País
    val paises = listOf("Chile", "Argentina", "Perú", "México")
    var paisExpanded by remember { mutableStateOf(false) }
    var paisSelected by remember { mutableStateOf(paises.first()) }

    val context = LocalContext.current

    Scaffold(

        /************************************TOPBAR*********************************/
        topBar = {
            SimpleTopbar(
                onBack = onBack,
                title = "Visioner",
                showBack = true
            )
        }
    ) { padding ->
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                // Encabezado
                Text(
                    "Formulario de Registro",
                    fontSize = 35.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                )

                Spacer(modifier = Modifier.height(50.dp))

                // Nombre
                TextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre de usuario") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Correo
                TextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Contraseña con toggle
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (showPassword) "Ocultar contraseña" else "Mostrar contraseña"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // ComboBox País
                ExposedDropdownMenuBox(
                    expanded = paisExpanded,
                    onExpandedChange = { paisExpanded = !paisExpanded }
                ) {
                    TextField(
                        value = paisSelected,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("País") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = paisExpanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = paisExpanded,
                        onDismissRequest = { paisExpanded = false }
                    ) {
                        paises.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    paisSelected = opcion
                                    paisExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón de registro
                Button(
                    onClick = {
                        RegistroDbHelper.guardarRegistro(
                            context,
                            nombre,
                            correo,
                            password,
                            paisSelected,
                        ) { res ->
                            if (res.ok) {
                                Toast.makeText(
                                    context,
                                    "Usuario registrado con éxito",
                                    Toast.LENGTH_LONG
                                ).show()
                                onRegistroClick()
                            } else {
                                Toast.makeText(
                                    context,
                                    res.errores.joinToString("\n"),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Registrarse",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

            }
        }
    }
}

/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
//fun PreviewRegistroScreen() {
    MaterialTheme {
        RegistroScreen()
    }
}
*/