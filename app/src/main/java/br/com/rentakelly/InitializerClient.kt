package br.com.rentakelly

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InitializerClient {
    fun init(): RepositoryService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RepositoryService::class.java)
    }
}