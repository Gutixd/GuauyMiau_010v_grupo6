package com.example.guaumiau.ui.theme.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    onGoRegister: () -> Unit,
    onLoggedIn: (userId: Long) -> Unit,
    vm: LoginViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Iniciar sesión", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = state.email,
            onValueChange = { vm.onChange(email = it) },
            label = { Text("Email DUOC") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.password,
            onValueChange = { vm.onChange(password = it) },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        if (state.error != null) {
            Text(state.error!!, color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = {
                // intenta login 
               
                vm.login()
                // navega cuando no haya  algun error
            },
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (state.isLoading) "Entrando..." else "Entrar")
        }

        TextButton(onClick = onGoRegister) {
            Text("Crear cuenta")
        }
    }

    // solo sirve para observar e agregar canmbios 
    LaunchedEffect(state.isLoading, state.error) {
        if (!state.isLoading && state.error == null && state.email.isNotBlank()) {
            onLoggedIn(1L) // en tu flujo real, reemplaza por el id de algun usuario que ya esta logueado
        }
    }
}
