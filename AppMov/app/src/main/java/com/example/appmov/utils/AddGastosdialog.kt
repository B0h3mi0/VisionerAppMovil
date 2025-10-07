package com.example.appmov.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appmov.ui.theme.Amarillomostaza
import com.example.appmov.ui.theme.Azuloscuro
import com.example.appmov.ui.theme.Negrocool

@Composable
fun AddGastoDialog(
    categoria: String,
    onDismiss: () -> Unit,
    onSave: (descripcion: String, monto: Long) -> Unit
) {
    var descripcion by remember { mutableStateOf("") }
    var montoStr by remember { mutableStateOf("") } // usuario escribe "35000"

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar gasto") },
        text = {
            Column {
                Text("Categoría: $categoria")
                OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })
                OutlinedTextField(value = montoStr, onValueChange = { montoStr = it.filter { ch -> ch.isDigit() } }, label = { Text("Monto (ej: 35000)") })
                Text("Ingresa monto en pesos, sin puntos ni comas.", style = MaterialTheme.typography.headlineSmall)
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val montoVal = montoStr.toLongOrNull()
                if (descripcion.isNotBlank() && montoVal != null && montoVal > 0L) {
                    onSave(descripcion.trim(), montoVal)
                } else {
                    // podrías mostrar Toast o error simple
                }
            }) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AddGastoDialogPreview() {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        AddGastoDialog(
            categoria = "Comida",
            onDismiss = { showDialog = false },
            onSave = { descripcion, monto ->
                println("Gasto guardado: $descripcion - $monto")
                showDialog = false
            }
        )
    }
}


