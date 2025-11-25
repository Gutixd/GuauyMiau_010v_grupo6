package com.example.guaumiau.data.remote.dog

data class DogApiResponse(
    val message: String, // URL de la imagen del perro
    val status: String // "success" o "error"
)
