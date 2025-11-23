package com.example.guaumiau.data.remote.dog

import retrofit2.http.GET

interface DogApiService {

    @GET("breeds/image/random")
    suspend fun getRandomDog(): DogApiResponse
}
