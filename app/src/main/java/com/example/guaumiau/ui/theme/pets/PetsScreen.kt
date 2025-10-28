package com.example.guaumiau.ui.theme.pets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.guaumiau.data.local.entity.Pet
import com.example.guaumiau.domain.model.PetType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetsScreen(
    userId: Long,
    onLogout: () -> Unit,
    vm: PetsViewModel = hiltViewModel()
) {
    val pets by vm.pets.collectAsState()

    LaunchedEffect(userId) {
        if (userId > 0) vm.observePets(userId)
    }

    var newName by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf<PetType?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Mascotas") },
                actions = {
                    TextButton(onClick = onLogout) { Text("Salir") }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Formulario rápido para agregar
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(onClick = { selectedType = PetType.PERRO }, label = { Text("Perro") })
                AssistChip(onClick = { selectedType = PetType.GATO }, label = { Text("Gato") })
                AssistChip(onClick = { selectedType = PetType.OTRO }, label = { Text("Otro") })
            }

            OutlinedTextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("Nombre de la mascota") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    vm.addPet(selectedType, newName)
                    newName = ""
                    selectedType = null
                },
                enabled = !newName.isBlank() && selectedType != null,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Agregar") }

            Divider()

            // Lista
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(pets) { pet: Pet ->
                    ElevatedCard(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(12.dp)) {
                            Text(pet.name, style = MaterialTheme.typography.titleMedium)
                            Text("Tipo: ${pet.type}")
                            Row {
                                TextButton(onClick = { vm.deletePet(pet) }) { Text("Eliminar") }
                            }
                        }
                    }
                }
            }
        }
    }
}
