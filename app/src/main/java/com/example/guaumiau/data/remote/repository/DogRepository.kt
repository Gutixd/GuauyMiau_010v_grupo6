package com.example.guaumiau.data.repository

import com.example.guaumiau.data.remote.dog.DogApiService
import javax.inject.Inject

class DogRepository @Inject constructor(
    private val api: DogApiService
) {

    suspend fun getRandomDogImageUrl(): Result<String> {
        return try {
            val response = api.getRandomDog()
            if (response.status == "success") {
                Result.success(response.message)
            } else {
                Result.failure(Exception("API Error: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
