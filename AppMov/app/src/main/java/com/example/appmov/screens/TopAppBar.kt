package com.example.appmov.screens

import android.R.color.white
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.appmov.ui.theme.Amarillomostaza
import com.example.appmov.ui.theme.Amarillopalido
import com.example.appmov.ui.theme.Azuloscuro
import com.example.appmov.ui.theme.Negrocool

/*  PASO 1: Modificamos el SimpleTopBar para que contenga un meno despegable*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopbar(
    title: String,
    showBack: Boolean,
    onBack: (() -> Unit)? = null,
    onNavigateToApi: (() -> Unit)? = null,
    onNavigateToHome: (() -> Unit)? = null,
    onNavigateToLogin: (() -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(title)},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Azuloscuro,
            titleContentColor = Amarillomostaza,
            navigationIconContentColor = Amarillomostaza,
            actionIconContentColor = Amarillomostaza
        ),
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = { onBack?.invoke() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                }
            } else {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menú")
                }
                // Aquí desplegamos el menú hamburguesa
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Home") },
                        onClick = {
                            expanded = false
                            onNavigateToHome?.invoke()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Api") },
                        onClick = {
                            expanded = false
                            onNavigateToApi?.invoke()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Cerrar Sesión") },
                        onClick = {
                            expanded = false
                            onNavigateToLogin?.invoke()
                        }
                    )
                }
            }
        }
    )
}