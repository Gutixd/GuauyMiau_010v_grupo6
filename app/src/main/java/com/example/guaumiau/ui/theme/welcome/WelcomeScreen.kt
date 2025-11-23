package com.example.guaumiau.ui.theme.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WelcomeScreen(
    onGoLogin: () -> Unit,
    onGoRegister: () -> Unit
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    "GuauMiau",
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    "La app para estudiantes que aman a sus mascotas y quieren tener todo ordenado.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = onGoLogin,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ya tengo cuenta")
                }
                OutlinedButton(
                    onClick = onGoRegister,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Quiero registrarme")
                }
                Text(
                    "App creada para el ramo de Desarrollo de Aplicaciones, usando Jetpack Compose, Room y Hilt.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
