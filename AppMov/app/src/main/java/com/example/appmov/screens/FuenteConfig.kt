package com.example.appmov.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.appmov.R

@Composable
fun FuenteConfigScreen(
    onBack: () -> Unit,
    onFontSizeSelected: (Float) -> Unit
) {
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
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Ajustar tamaño de fuente", style = MaterialTheme.typography.headlineSmall)

            Button(onClick = { onFontSizeSelected(14f) }) {
                Text(
                    text = "Pequeño",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Button(onClick = { onFontSizeSelected(20f) }) {
                Text(
                    text = "Medio",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Button(onClick = { onFontSizeSelected(35f) }) {
                Text(
                    text = "Grande",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(Modifier.height(32.dp))

            Button(onClick = onBack) {
                Text("Volver")
            }
        }
    }
}
