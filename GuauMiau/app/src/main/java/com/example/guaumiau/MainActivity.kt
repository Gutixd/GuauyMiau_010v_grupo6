package com.example.guaumiau

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuauMiauApp()
        }
    }
}

@Composable
fun GuauMiauApp() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            GreetingScreen()
        }
    }
}

@Composable
fun GreetingScreen() {
    Text(
        text = "¡Hola desde Guau&Miau 🐶🐱!",
        style = MaterialTheme.typography.headlineMedium
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GuauMiauApp()
}
