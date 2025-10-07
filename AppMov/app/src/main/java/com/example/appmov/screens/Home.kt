package com.example.appmov.screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appmov.R
import com.example.appmov.ui.theme.Amarillomostaza
import com.example.appmov.ui.theme.Amarillopalido
import com.example.appmov.ui.theme.Azuloscuro
import com.example.appmov.ui.theme.Negrocool
import com.example.appmov.ui.theme.Verdeprincipal
import com.example.appmov.viewmodel.GastoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun HomeScreen(navController: NavController, viewModel: GastoViewModel) {
    val saldo by viewModel.saldoDisponible.collectAsState()
    val categorias by viewModel.categoriasConTotales.collectAsState()

    fun formatoSimple(valor: Long): String {
        return "$" + "%,d".format(valor).replace(",", ".")
    }
    //Preferencias COmpartidas
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val isLogged = prefs.getBoolean("isLogged", false)
    val correo = prefs.getString("correo", null)
    val nombre = prefs.getString("nombre", null)

    Scaffold(

        /************************************TOPBAR*********************************/
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "VisionerApp",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* TODO menú lateral */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menú"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: ajustes */ }) {
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
        /************************************TOPBAR*********************************/

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Aquí iría la navegación a pantalla para registrar gasto
                },
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 🔹 Tarjeta de sesión (solo si hay login)
                if (isLogged) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Verdeprincipal, // Azul clarito
                            contentColor = Azuloscuro
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("Sesión activa", style = MaterialTheme.typography.titleMedium)
                            Text("Nombre: ${nombre ?: "Desconocido"}")
                            Text("Correo: ${correo ?: "Desconocido"}")
                        }
                    }
                }

                // 🔹 Saldo disponible
                Box(
                    modifier = Modifier
                        .fillMaxWidth()              // ocupa todo el ancho
                        .padding(16.dp)              // separación externa
                        .background(
                            color = Negrocool,   // color de fondo celeste claro
                            shape = RoundedCornerShape(12.dp) // esquinas redondeadas
                        )
                        .padding(24.dp),             // separación interna
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Saldo disponible: ${formatoSimple(saldo)}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Amarillopalido, // verde oscuro
                        textAlign = TextAlign.Center
                    )
                }

                // 🔹 Lista de categorías
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categorias) { categoria ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    // Aquí navegas al detalle de la categoría
                                    navController.navigate("detalle/${categoria.nombre}")
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = Azuloscuro,      // Fondo de la card
                                contentColor = Amarillomostaza        // Color del texto dentro
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = categoria.nombre,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = "${formatoSimple(categoria.total)}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                    item {
                        ItemNoticia(navController = navController)
                    }


                }
            }
        }
    }
}




@RequiresApi(Build.VERSION_CODES.O)
class FakeGastoViewModel : GastoViewModel() {
    init {
        // Sobreescribimos datos de ejemplo
        viewModelScope.launch {
            addGasto("Comida", "Almuerzo", 5000L)
            addGasto("Transporte", "Metro", 1200L)
            addGasto("Ocio", "Cine", 8000L)
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val fakeNavController = rememberNavController()
    val fakeViewModel = FakeGastoViewModel()

    HomeScreen(navController = fakeNavController, viewModel = fakeViewModel)

}



