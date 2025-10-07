package com.example.appmov.composable

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appmov.screens.InicioScreen
import com.example.appmov.screens.HomeScreen
import com.example.appmov.screens.LoginScreen
import com.example.appmov.screens.RegistroScreen
import com.example.appmov.navigation.Routes
import com.example.appmov.screens.DetalleGastosScreen
import com.example.appmov.viewmodel.GastoViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.appmov.screens.FragmentView
import com.example.appmov.screens.FuenteConfigScreen
import com.example.appmov.screens.NoticiaScreen

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNav(fontSizeState: MutableState<Float>) {
    val gastoViewModel: GastoViewModel = viewModel()
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.INICIO
    ) {
        composable(Routes.INICIO) {
            InicioScreen(
                onComenzarClick = { navController.navigate(Routes.REGISTRO) },
                onLoginClick = { navController.navigate(Routes.LOGIN) },
                onFuenteConfigClick = {navController.navigate(Routes.CONFG_FUENTE)}

            )
        }
        composable(Routes.LOGIN) {
            LoginScreen(
                onHomeClick = { navController.navigate(Routes.HOME) }
            )
        }
        composable(Routes.REGISTRO) {
            RegistroScreen(
                onRegistroClick = { navController.navigate(Routes.LOGIN) }
            )
        }
        composable(Routes.HOME) {
            HomeScreen(navController = navController, viewModel = gastoViewModel)
        }
        composable(
            "detalle/{categoria}",
            arguments = listOf(navArgument("categoria") { type = NavType.StringType })
        ) { backStackEntry ->
            val cat = backStackEntry.arguments?.getString("categoria") ?: ""
            DetalleGastosScreen(
                categoria = cat,
                viewModel = gastoViewModel,
                navController = navController
            )
        }
        composable(Routes.CONFG_FUENTE) {
            FuenteConfigScreen(
                onFontSizeSelected = { newSize -> fontSizeState.value = newSize },
                onBack = { navController.popBackStack() }
            )
        }
        composable (Routes.NOTICIAS){
            NoticiaScreen()
        }
    }
}