package com.furkankocak.omdbmoviesearchapp.network

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    var service: ApiService
    private lateinit var retrofit: Retrofit
    private lateinit var okHttpClient: OkHttpClient
    private val baseUrl = "https://www.omdbapi.com"

    init {
        service = createRetrofitService()
    }

    private fun createRetrofitService(): ApiService {

        val interceptor = Interceptor { chain: Interceptor.Chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Expect", "application/json")
            val request = requestBuilder.build()
            chain.proceed(request)
        }

        val gson = GsonBuilder()
            .setLenient()
            .create()

        okHttpClient = OkHttpClient()
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)

        okHttpClient = builder.build()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}