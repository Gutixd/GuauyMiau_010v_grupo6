package com.example.guaumiau.ui.theme.pets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.guaumiau.ui.theme.dog.DogViewModel

@Composable
fun DogApiSection(
    modifier: Modifier = Modifier,
    viewModel: DogViewModel
) {
    val state by viewModel.uiState.collectAsState()

    // Cargar un perrito al entrar
    LaunchedEffect(Unit) {
        viewModel.loadRandomDog()
    }

    Column(modifier = modifier) {
        Text(text = "Perrito aleatorio (API externa)")

        Spacer(modifier = Modifier.height(8.dp))

        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }
            state.error != null -> {
                Text(text = "Error: ${state.error}")
                Spacer(modifier = Modifier.height(4.dp))
                Button(onClick = { viewModel.loadRandomDog() }) {
                    Text("Reintentar")
                }
            }
            state.imageUrl != null -> {
                AsyncImage(
                    model = state.imageUrl,
                    contentDescription = "Perrito",
                    modifier = Modifier
                        .size(200.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.loadRandomDog() }) {
                    Text("Otro perrito")
                }
            }

        }
    }
}
