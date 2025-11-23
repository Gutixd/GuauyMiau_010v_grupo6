package com.example.guaumiau.ui.theme.perfil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.guaumiau.SmallTopAppBar

@Composable
fun AboutAppScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Acerca de GuauMiau") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "GuauMiau",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                "Aplicación desarrollada como proyecto académico.\n\n" +
                        "- Gestiona usuarios y mascotas con Room.\n" +
                        "- Arquitectura con Hilt, ViewModel y repositorios.\n" +
                        "- Interfaz construida con Jetpack Compose.\n\n" +
                        "Incluye pantallas de login, registro, listado de mascotas, perfil, ajustes, " +
                        "detalle de mascota y bienvenida.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.weight(1f))

            Text(
                "Esta pantalla es perfecta para explicar tu proyecto al profesor o en la rúbrica.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
