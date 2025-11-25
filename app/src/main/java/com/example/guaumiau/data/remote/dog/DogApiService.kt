package com.example.guaumiau.data.remote.dog

import retrofit2.http.GET

interface DogApiService { // interfaz de la api de perro

    @GET("breeds/image/random") // es la ruta de la api
    suspend fun getRandomDog(): DogApiResponse // y esta es la respuesta de la api


}
