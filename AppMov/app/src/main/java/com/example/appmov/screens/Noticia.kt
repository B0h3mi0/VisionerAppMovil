package com.example.appmov.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appmov.utils.RetrofitClient
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoticiaScreen() {
    val scope = rememberCoroutineScope()
    var joke by remember { mutableStateOf("Cargando...") }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val api = RetrofitClient.create()
                val response = api.getRandomJoke()
                joke = response.value
            } catch (e: Exception) {
                joke = "Error: ${e.message}"
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Chuck Norris Jokes") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(joke, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                scope.launch {
                    try {
                        val api = RetrofitClient.create()
                        val response = api.getRandomJoke()
                        joke = response.value
                    } catch (e: Exception) {
                        joke = "Error: ${e.message}"
                    }
                }
            }) {
                Text("Otro chiste 🤣")
            }
        }
    }
}

