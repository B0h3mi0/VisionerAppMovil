package com.example.appmov.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appmov.ui.theme.Amarillomostaza
import com.example.appmov.ui.theme.Azuloscuro
import com.example.appmov.ui.theme.Negrocool
import com.example.appmov.utils.AddGastoDialog
import com.example.appmov.utils.formatCurrencyCLP
import com.example.appmov.utils.formatDate
import com.example.appmov.viewmodel.GastoViewModel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

import kotlinx.coroutines.launch

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview

import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.example.appmov.ui.theme.Amarillopalido

@RequiresApi(Build.VERSION_CODES.O)
class FakeGastoViewModelDetalle : GastoViewModel() {
    init {
        // Agregamos datos de ejemplo
        viewModelScope.launch {
            addGasto("Comida", "Almuerzo", 5000)
            addGasto("Comida", "Cena", 12000)
            addGasto("Transporte", "Metro", 1500)
        }
    }

    // Sobre escribir gastosPorCategoriaFlow para filtrar correctamente
    override fun gastosPorCategoriaFlow(categoria: String) =
        super.gastosPorCategoriaFlow(categoria)
}
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetalleGastosScreen(
    categoria: String,
    viewModel: GastoViewModel,
    navController: NavController
) {
    val gastos = viewModel.gastosPorCategoriaFlow(categoria).collectAsState().value
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        /************************************ TOPBAR *********************************/
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = categoria,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Ajustes */ }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Ajustes"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Azuloscuro,
                    titleContentColor = Amarillopalido,
                    navigationIconContentColor = Amarillomostaza,
                    actionIconContentColor = Amarillomostaza
                )
            )
        },
        /************************************ TOPBAR *********************************/

        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 8.dp
                ),
                shape = MaterialTheme.shapes.large
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar Gasto")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // 🔹 Lista de gastos de la categoría
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(gastos) { g ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* Aquí podrías abrir detalle del gasto */ },
                        colors = CardDefaults.cardColors(
                            containerColor = Negrocool,
                            contentColor = Amarillomostaza
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = g.descripcion,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = formatDate(g.fecha),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Text(
                                text = formatCurrencyCLP(g.monto),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }
        }

        // 🔹 Diálogo para agregar gasto
        if (showDialog) {
            AddGastoDialog(
                categoria = categoria,
                onDismiss = { showDialog = false },
                onSave = { descripcion, monto ->
                    viewModel.addGasto(
                        categoria = categoria,
                        descripcion = descripcion,
                        monto = monto
                    )
                    showDialog = false
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DetalleGastosScreenPreview() {
    val fakeNavController = rememberNavController()
    val fakeViewModel = remember { FakeGastoViewModelDetalle() }

    // Elegimos la categoría "Comida" para el preview
    DetalleGastosScreen(
        categoria = "Comida",
        viewModel = fakeViewModel,
        navController = fakeNavController
    )
}
