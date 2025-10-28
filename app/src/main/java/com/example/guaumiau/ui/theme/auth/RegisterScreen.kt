package com.example.guaumiau.ui.theme.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.guaumiau.domain.model.PetType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    onRegistered: () -> Unit,
    vm: RegisterViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Crear cuenta") })
        }
    ) { padding ->
        LazyColumn(
            Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                OutlinedTextField(
                    value = state.fullName,
                    onValueChange = { vm.onChange(fullName = it) },
                    label = { Text("Nombre completo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                OutlinedTextField(
                    value = state.email,
                    onValueChange = { vm.onChange(email = it) },
                    label = { Text("Email DUOC") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                OutlinedTextField(
                    value = state.phone,
                    onValueChange = { vm.onChange(phone = it) },
                    label = { Text("Teléfono (opcional)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                OutlinedTextField(
                    value = state.password,
                    onValueChange = { vm.onChange(password = it) },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                OutlinedTextField(
                    value = state.confirm,
                    onValueChange = { vm.onChange(confirm = it) },
                    label = { Text("Confirmar contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Sección de mascotas (mínima)
            itemsIndexed(state.pets) { index, row ->
                Column(Modifier.fillMaxWidth()) {
                    Text("Mascota ${index + 1}", style = MaterialTheme.typography.titleMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Selector simple por ahora (2 botones)
                        AssistChip(
                            onClick = { vm.setPetType(index, PetType.PERRO) },
                            label = { Text("Perro") },
                        )
                        AssistChip(
                            onClick = { vm.setPetType(index, PetType.GATO) },
                            label = { Text("Gato") },
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = row.name,
                        onValueChange = { vm.setPetName(index, it) },
                        label = { Text("Nombre de la mascota") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextButton(onClick = { vm.removePetRow(index) }) {
                        Text("Quitar mascota")
                    }
                    Divider()
                }
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = { vm.addPetRow() }) { Text("Agregar otra mascota") }
                    Spacer(Modifier.weight(1f))
                    Button(
                        onClick = { vm.submit(currentUserId = null) },
                        enabled = !state.isSubmitting
                    ) { Text(if (state.isSubmitting) "Guardando..." else "Registrarme") }
                }
            }

            if (state.error != null) {
                item { Text(state.error!!, color = MaterialTheme.colorScheme.error) }
            }
            if (state.success) {
                item {
                    Text("¡Registro exitoso!", color = MaterialTheme.colorScheme.primary)
                    LaunchedEffect(Unit) { onRegistered() }
                }
            }

            item {
                TextButton(onClick = onBack) { Text("Volver") }
            }
        }
    }
}
