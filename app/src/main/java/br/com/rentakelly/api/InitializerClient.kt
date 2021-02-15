package br.com.rentakelly.api

import androidx.annotation.VisibleForTesting
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InitializerClient {
    var httpClient = OkHttpClient.Builder().build()

    @VisibleForTesting
    var baseUrl = "https://api.github.com/"
    fun init(): RepositoryService {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RepositoryService::class.java)
    }
}
