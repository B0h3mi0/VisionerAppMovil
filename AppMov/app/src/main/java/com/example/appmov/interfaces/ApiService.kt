package com.example.appmov.interfaces
import com.example.appmov.model.Joke
import retrofit2.http.GET

interface ApiService {
    @GET("jokes/random")
    suspend fun getRandomJoke(): Joke
}