package com.example.guaumiau.ui.theme.pets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
//import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.guaumiau.SmallTopAppBar
import com.example.guaumiau.data.local.entity.Pet
import com.example.guaumiau.domain.model.PetType
import com.example.guaumiau.ui.theme.dog.DogViewModel
import com.example.guaumiau.ui.theme.pets.DogApiSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetsScreen(
    userId: Long,
    onLogout: () -> Unit,
    onGoProfile: (Long) -> Unit,
    onGoSettings: () -> Unit,
    onGoAbout: () -> Unit,
    vm: PetsViewModel = hiltViewModel()
) {
    val pets by vm.pets.collectAsState()
    val dogVm: DogViewModel = hiltViewModel()

    LaunchedEffect(userId) {
        if (userId > 0) vm.observePets(userId)
    }

    var newName by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf<PetType?>(null) }
    var petToEdit by remember { mutableStateOf<Pet?>(null) }
    var editName by remember { mutableStateOf("") }
    var editType by remember { mutableStateOf<PetType?>(null) }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Mis mascotas") },
                actions = {
                    TextButton(onClick = { onGoProfile(userId) }) {
                        Text("Perfil")
                    }
                    TextButton(onClick = onGoSettings) {
                        Text("Ajustes")
                    }
                    TextButton(onClick = onLogout) {
                        Text("Salir")
                    }
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

            ElevatedCard(Modifier.fillMaxWidth()) {
                Column(
                    Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Más opciones", style = MaterialTheme.typography.titleMedium)
                    Text("Desde aquí puedes ir a la pantalla de información de la app.")
                    Button(
                        onClick = onGoAbout,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Acerca de la app")
                    }
                }
            }

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
            ) {
                Text("Agregar")
            }
            DogApiSection(viewModel = dogVm)

            Divider()

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(pets) { pet: Pet ->
                    ElevatedCard(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(12.dp)) {
                            Text(pet.name, style = MaterialTheme.typography.titleMedium)
                            Text("Tipo: ${pet.type}")

                            Row {
                                TextButton(onClick = { vm.deletePet(pet) }) {
                                    Text("Eliminar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun SmallTopAppBar(title: @Composable () -> Unit, actions: @Composable () -> Unit) {}
