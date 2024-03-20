package com.aradhya.MediSpeak.text

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenFdaApiClient {
    private const val BASE_URL = "https://api.fda.gov/"

    fun create(): OpenFdaApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(OpenFdaApiService::class.java)
    }
}
