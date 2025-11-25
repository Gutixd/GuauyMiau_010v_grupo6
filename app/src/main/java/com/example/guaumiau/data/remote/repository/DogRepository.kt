package com.example.guaumiau.data.repository

import com.example.guaumiau.data.remote.dog.DogApiService
import javax.inject.Inject

class DogRepository @Inject constructor(
    private val api: DogApiService // injectamos la interfaz del servicio
) {

    suspend fun getRandomDogImageUrl(): Result<String> { // cambiamos el tipo de retorno a Result
        return try {
            val response = api.getRandomDog()
            if (response.status == "success") {
                Result.success(response.message)
            } else {
                Result.failure(Exception("API Error: ${response.status}"))
            }
        } catch (e: Exception) { // cambiamos la excepción a Exception nada mas
            Result.failure(e)
        }
    }
}
