package com.aradhya.MediSpeak.text
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenFdaApiService {
    @GET("drug/event.json")
    fun getDrugEvent(
        @Query("api_key") apiKey: String,
        @Query("search") searchText: String
    ): Call<OpenFdaApiResponse>
}
