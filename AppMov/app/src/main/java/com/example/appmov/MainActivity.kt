package com.example.appmov

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.appmov.composable.AppNav
import com.example.appmov.ui.theme.AppMovTheme


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val fontSizeState = remember { mutableStateOf(16f) } // valor por defecto

            AppMovTheme(fontSize = fontSizeState.value) {
                AppNav(fontSizeState = fontSizeState)
            }
        }
    }
}



