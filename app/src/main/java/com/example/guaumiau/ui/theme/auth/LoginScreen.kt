package com.example.guaumiau.ui.theme.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

// pantalla de inicio de sesion
@Composable
fun LoginScreen(
    onGoRegister: () -> Unit, // Navega a registro
    onLoggedIn: (userId: Long) -> Unit,// Callback tras login exitoso
    vm: LoginViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState() // Estado reactivo desde el ViewModel

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Iniciar sesión", style = MaterialTheme.typography.headlineMedium)
// esto es del correo
        OutlinedTextField(
            value = state.email,
            onValueChange = { vm.onChange(email = it) },
            label = { Text("Correo DUOC") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        // esto es de la contraseña
        OutlinedTextField(
            value = state.password,
            onValueChange = { vm.onChange(password = it) },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        // solo un mensaje de error
        if (state.error != null) {
            Text(state.error!!, color = MaterialTheme.colorScheme.error)
        }

        // Boton de login
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

        // para ir a registro
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
